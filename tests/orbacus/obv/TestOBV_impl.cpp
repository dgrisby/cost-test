// **********************************************************************
//
// Copyright (c) 1999
// Object Oriented Concepts, Inc.
// Billerica, MA, USA
//
// All Rights Reserved
//
// **********************************************************************

#include <includes.h>

#include <TestOBV_skel.h>

#include <TestOBV_impl.h>
#include <TestTrunc_impl.h>
#include <TestValueInterface_impl.h>

using namespace CORBA;

//
// IDL:TestOBV:1.0
//
TestOBV_impl::TestOBV_impl(ORB_ptr orb,
                           TestValue_init* valueFactory,
                           TestValueSub_init* valueSubFactory,
#ifndef HAVE_NO_CUSTOM_VALUETYPE
                           TestCustom_init* customFactory,
#endif
                           TestNode_init* nodeFactory,
                           TestAbstract_ptr absInterface,
                           TestAbstract_ptr absValue,
                           TestValueInterface_init* valueInterfaceFactory)
    : orb_(ORB::_duplicate(orb))
{
    value_ = valueFactory -> create(500);
    valueSub_ = valueSubFactory -> create_sub(501, "ValueSub");
#ifndef HAVE_NO_CUSTOM_VALUETYPE
    custom_ = customFactory -> create(-99, -123456, "CustomVal", 100.997);
#endif

    TestNode_var left, right, ltmp, rtmp;
    ltmp = nodeFactory -> create(2);
    rtmp = nodeFactory -> create(1);
    left = nodeFactory -> create_lr(3, ltmp, rtmp);
    ltmp = nodeFactory -> create(5);
    right = nodeFactory -> create_lr(6, ltmp, 0);
    node_ = nodeFactory -> create_lr(10, left, right);

    absInterface_ = TestAbstract::_duplicate(absInterface);
    absValue_ = TestAbstract::_duplicate(absValue);

    valueInterface_ = valueInterfaceFactory -> create(99);
}

TestOBV_impl::~TestOBV_impl()
{
}

ValueBase*
TestOBV_impl::get_null_valuebase()
{
    return (ValueBase*)0;
}

void
TestOBV_impl::set_null_valuebase(ValueBase* v)
{
    TEST(v == 0);
}

TestValueSub*
TestOBV_impl::get_null_valuesub()
{
    return (TestValueSub*)0;
}

void
TestOBV_impl::set_null_valuesub(TestValueSub* v)
{
    TEST(v == 0);
}

TestAbsValue1*
TestOBV_impl::get_abs_value1()
{
    add_ref(value_);
    return value_;
}

void
TestOBV_impl::set_abs_value1(TestAbsValue1* v)
{
    TEST(v != 0);
    TestValue* value = TestValue::_downcast(v);
    TEST(value != 0);
    value -> ping1();
    TEST(value -> count() == value_ -> count());
}

TestAbsValue2*
TestOBV_impl::get_abs_value2()
{
    add_ref(valueSub_);
    return valueSub_;
}

void
TestOBV_impl::set_abs_value2(TestAbsValue2* v)
{
    TEST(v != 0);
    TestValueSub* value = TestValueSub::_downcast(v);
    TEST(value != 0);
    value -> ping1();
    value -> ping2();
    TEST(value -> count() == valueSub_ -> count());
    TEST(strcmp(value -> name(), valueSub_ -> name()) == 0);
}

TestValue*
TestOBV_impl::get_value()
{
    add_ref(value_);
    return value_;
}

void
TestOBV_impl::set_value(TestValue* v)
{
    TEST(v != 0);
    v -> ping1();
    TEST(v -> count() == value_ -> count());
}

TestValueSub*
TestOBV_impl::get_valuesub()
{
    add_ref(valueSub_);
    return valueSub_;
}

void
TestOBV_impl::set_valuesub(TestValueSub* v)
{
    TEST(v != 0);
    v -> ping1();
    v -> ping2();
    TEST(v -> count() == valueSub_ -> count());
    TEST(strcmp(v -> name(), valueSub_ -> name()) == 0);
}

TestValue*
TestOBV_impl::get_valuesub_as_value()
{
    add_ref(valueSub_);
    return valueSub_;
}

void
TestOBV_impl::set_valuesub_as_value(TestValue* v)
{
    TEST(v != 0);
    TestValueSub* value = TestValueSub::_downcast(v);
    TEST(value != 0);
    value -> ping1();
    value -> ping2();
    TEST(value -> count() == valueSub_ -> count());
    TEST(strcmp(value -> name(), valueSub_ -> name()) == 0);
}

void
TestOBV_impl::get_two_values(TestValue_out v1, TestValue_out v2)
{
    add_ref(value_);
    add_ref(value_);
    v1 = value_.in();
    v2 = value_.in();
}

void
TestOBV_impl::set_two_values(TestValue* v1, TestValue* v2)
{
    TEST(v1 != 0);
    TEST(v2 != 0);
    TEST(v1 == v2);
    v1 -> ping1();
    v2 -> ping1();
    TEST(v1 -> count() == value_ -> count());
}

void
TestOBV_impl::get_two_valuesubs_as_values(TestValue_out v1, TestValue_out v2)
{
    //
    // Lookup the factory and create two distinct TestValueSub instances
    //

    ValueFactoryBase_var factory;
    factory = orb_ -> lookup_value_factory("IDL:TestValueSub:1.0");
    TEST(factory.in() != 0);
    TestValueSub_init* subFactory = TestValueSub_init::_downcast(factory);
    TEST(subFactory != 0);

    v1 = subFactory -> create_sub(999, "ValueSub");
    v2 = subFactory -> create_sub(999, "ValueSub");
}

void
TestOBV_impl::set_two_valuesubs_as_values(TestValue* v1, TestValue* v2)
{
    TEST(v1 != 0);
    TEST(v2 != 0);
    v1 -> ping1();
    v2 -> ping1();
    TEST(v1 -> count() == v2 -> count());

    TestValueSub* s1 = TestValueSub::_downcast(v1);
    TEST(s1 != 0);
    s1 -> ping2();
    TestValueSub* s2 = TestValueSub::_downcast(v2);
    TEST(s2 != 0);
    s2 -> ping2();
    TEST(strcmp(s1 -> name(), s2 -> name()) == 0);
}

#ifndef HAVE_NO_CUSTOM_VALUETYPE
TestCustom*
TestOBV_impl::get_custom()
{
    add_ref(custom_);
    return custom_;
}

void
TestOBV_impl::set_custom(TestCustom* v)
{
    TEST(v != 0);
    v -> ping1();
    TEST(v -> shortVal() == custom_ -> shortVal());
    TEST(v -> longVal() == custom_ -> longVal());
    TEST(v -> doubleVal() == custom_ -> doubleVal());
    TEST(strcmp(v -> stringVal(), custom_ -> stringVal()) == 0);
}

TestAbsValue1*
TestOBV_impl::get_abs_custom()
{
    add_ref(custom_);
    return custom_;
}

void
TestOBV_impl::set_abs_custom(TestAbsValue1* v)
{
    TEST(v != 0);
    v -> ping1();
    TestCustom* c = TestCustom::_downcast(v);
    TEST(c != 0);
    TEST(c -> shortVal() == custom_ -> shortVal());
    TEST(c -> longVal() == custom_ -> longVal());
    TEST(c -> doubleVal() == custom_ -> doubleVal());
    TEST(strcmp(c -> stringVal(), custom_ -> stringVal()) == 0);
}
#endif

void
TestOBV_impl::get_node(TestNode_out v, ULong& count)
{
    add_ref(node_);
    v = node_.in();
    count = node_ -> compute_count();
}

void
TestOBV_impl::set_node(TestNode* v)
{
    TEST(v != 0);
    TEST(v -> compute_count() == node_ -> compute_count());
}

TestStringBox*
TestOBV_impl::get_string_box(const char* value)
{
    return new TestStringBox(value);
}

void
TestOBV_impl::set_string_box(TestStringBox* b, const char* value)
{
    TEST(b != 0);
    TEST(strcmp(b -> _value(), value) == 0);
}

TestULongBox*
TestOBV_impl::get_ulong_box(ULong value)
{
    return new TestULongBox(value);
}

void
TestOBV_impl::set_ulong_box(TestULongBox* b,
                            ULong value)
{
    TEST(b != 0);
    TEST(b -> _value() == value);
}

TestFixStructBox*
TestOBV_impl::get_fix_struct_box(const TestFixStruct& value)
{
    return new TestFixStructBox(value);
}

void
TestOBV_impl::set_fix_struct_box(TestFixStructBox* b,
                                 const TestFixStruct& value)
{
    TEST(b != 0);
    TEST(b -> x() == value.x);
    TEST(b -> y() == value.y);
    TEST(b -> radius() == value.radius);
}

TestVarStructBox*
TestOBV_impl::get_var_struct_box(const TestVarStruct& value)
{
    return new TestVarStructBox(value);
}

void
TestOBV_impl::set_var_struct_box(TestVarStructBox* b,
                                 const TestVarStruct& value)
{
    TEST(b != 0);
    TEST(strcmp(b -> name(), value.name) == 0);
    TEST(strcmp(b -> email(), value.email) == 0);
}

TestFixUnionBox*
TestOBV_impl::get_fix_union_box(const TestFixUnion& value)
{
    return new TestFixUnionBox(value);
}

void
TestOBV_impl::set_fix_union_box(TestFixUnionBox* b, const TestFixUnion& value)
{
    TEST(b != 0);

    switch(value._d())
    {
    case true:
        TEST(b -> o() == value.o());
        break;

    case false:
        TEST(b -> d() == value.d());
        break;
    }
}

TestVarUnionBox*
TestOBV_impl::get_var_union_box(const TestVarUnion& value)
{
    return new TestVarUnionBox(value);
}

void
TestOBV_impl::set_var_union_box(TestVarUnionBox* b, const TestVarUnion& value)
{
    TEST(b != 0);

    switch(value._d())
    {
    case 0:
        TEST(strcmp(b -> s(), value.s()) == 0);
        break;

    case 9:
    {
        const TestFixStruct& fs1 = b -> fs();
        const TestFixStruct& fs2 = value.fs();
        TEST(fs1.x == fs2.x);
        TEST(fs1.y == fs2.y);
        TEST(fs1.radius == fs2.radius);
        break;
    }
    }
}

TestAnonSeqBox*
TestOBV_impl::get_anon_seq_box(ULong length)
{
    TestAnonSeqBox* result = new TestAnonSeqBox;
    result -> length(length);
    for(ULong i = 0 ; i < length ; i++)
        (*result)[i] = (Short)i;
    return result;
}

void
TestOBV_impl::set_anon_seq_box(TestAnonSeqBox* b, ULong length)
{
    TEST(b != 0);
    TEST(b -> length() == length);
    for(ULong i = 0 ; i < length ; i++)
        TEST((*b)[i] == (Short)i);
}

TestStringSeqBox*
TestOBV_impl::get_string_seq_box(const TestStringSeq& value)
{
    return new TestStringSeqBox(value);
}

void
TestOBV_impl::set_string_seq_box(TestStringSeqBox* b,
                                 const TestStringSeq& value)
{
    TEST(b != 0);
    TEST(b -> length() == value.length());
    for(ULong i = 0 ; i < b -> length() ; i++)
        TEST(strcmp((*b)[i], value[i]) == 0);
}

TestAbstract_ptr
TestOBV_impl::get_ai_interface()
{
    return TestAbstract::_duplicate(absInterface_);
}

void 
TestOBV_impl::set_ai_interface(TestAbstract_ptr a)
{
    if(!is_nil(a))
    {
        a -> abstract_op();
#if 0
	// DG: No requirement for base Object _narrow of abstract interface
        Object_var obj = Object::_narrow(a);
        TEST(!is_nil(obj));
#endif
        TestAbstractSub_var sub = TestAbstractSub::_narrow(a);
        TEST(!is_nil(sub));
        sub -> sub_op();
    }
}

Any*
TestOBV_impl::get_ai_interface_any()
{
    Any* result = new Any;
    *result <<= absInterface_;

    //
    // Test local any extraction
    //
    AbstractBase_var base;
    TEST(*result >>= Any::to_abstract_base(base.out()));
    TEST(!is_nil(base));
    TestAbstract_var ab = TestAbstract::_narrow(base);
    TEST(!is_nil(ab));
    ab -> abstract_op();
    TestAbstractSub_var sub = TestAbstractSub::_narrow(base);
    TEST(!is_nil(sub));
    sub -> sub_op();

    return result;
}

void 
TestOBV_impl::set_ai_interface_any(const Any& a)
{
    //
    // Test remote any extraction
    //
    Object_var obj;
    Any::to_object to_obj(obj.out());
    TEST(a >>= to_obj);
    TEST(!is_nil(obj));
    TestAbstractSub_var sub = TestAbstractSub::_narrow(obj);
    TEST(!is_nil(sub));
    sub -> abstract_op();
    sub -> sub_op();
}

TestAbstract_ptr
TestOBV_impl::get_ai_value()
{
    return TestAbstract::_duplicate(absValue_);
}

void
TestOBV_impl::set_ai_value(TestAbstract_ptr a)
{
    if(!is_nil(a))
    {
        a -> abstract_op();
#if 0
	// DG: No requirement for ValueBase _downcast of abstract interface
        ValueBase* vb = ValueBase::_downcast(a);
        TEST(vb != 0);
#endif
        TestValueAI* v = TestValueAI::_downcast(a);
        TEST(v != 0);
        v -> value_op();
        TEST(v -> count() == 12345);
        TestAbstractSub_var sub = TestAbstractSub::_narrow(v);
        TEST(is_nil(sub));
    }
}

Any*
TestOBV_impl::get_ai_value_any()
{
    Any* result = new Any;
    *result <<= absValue_;

    //
    // Test local any extraction
    //
    AbstractBase_var base;
    TEST(*result >>= Any::to_abstract_base(base.out()));
    TEST(!is_nil(base));
    TestAbstract_var ab = TestAbstract::_narrow(base);
    TEST(!is_nil(ab));
    ab -> abstract_op();
    TestValueAI* val = TestValueAI::_downcast(base);
    TEST(val != 0);
    val -> value_op();

    return result;
}

void 
TestOBV_impl::set_ai_value_any(const Any& a)
{
    //
    // Test remote any extraction
    //
    AbstractBase_var base;
    TEST(a >>= Any::to_abstract_base(base.out()));
    TEST(!is_nil(base));
    TestAbstract_var ab = TestAbstract::_narrow(base);
    TEST(!is_nil(ab));
    ab -> abstract_op();
    TestValueAI* val = TestValueAI::_downcast(base);
    TEST(val != 0);
    val -> value_op();
}

TestTruncBase*
TestOBV_impl::get_trunc1()
{
    //
    // This test addresses several issues:
    //
    // 1) truncation
    // 2) skipping chunks and nested values during truncation
    // 3) nested value with repository ID information
    //

    TestTrunc1_var t1 = new TestTrunc1_impl;
    t1 -> cost((Float)1.993);
    t1 -> boolVal(true);
    ValueBase_var vb = value_ -> _copy_value();
    TestValue* v = TestValue::_downcast(vb);
    v -> count(999);
    t1 -> v(v);
    t1 -> shortVal(12667);
    return t1._retn();
}

TestTruncBase*
TestOBV_impl::get_trunc2()
{
    //
    // This test addresses several issues:
    //
    // 1) truncation - skipping chunks, nested values
    // 2) nested values with repository ID information
    // 3) cotermination of outer and nested values
    // 4) nested truncatable values
    // 5) indirection of repository ID information
    // 6) value indirection into truncated portion of nested value
    // 7) value indirection
    //

    TestTrunc2_var t2 = new TestTrunc2_impl;
    t2 -> cost((Float)5.993);

    TestTrunc1_var t1 = new TestTrunc1_impl;
    t1 -> cost((Float)1.993);
    t1 -> boolVal(true);
    ValueBase_var vb = value_ -> _copy_value();
    TestValue* v = TestValue::_downcast(vb);
    v -> count(999);
    t1 -> v(v);
    t1 -> shortVal(12667);
    t2 -> t(t1); // issues 2, 4

    vb = value_ -> _copy_value();
    TestValue* v2 = TestValue::_downcast(vb);
    v2 -> count(9999);
    t2 -> a(v2); // issues 2, 5

    t2 -> v(v); // issue 6

    t2 -> b(t1); // issue 7

    return t2._retn();
}

Any*
TestOBV_impl::get_value_any()
{
    TestValue_var v = get_value();
    Any* result = new Any;
    *result <<= v;
    return result;
}

Any*
TestOBV_impl::get_valuesub_any()
{
    TestValueSub_var v = get_valuesub();
    Any* result = new Any;
    *result <<= v;
    return result;
}

Any*
TestOBV_impl::get_valuesub_as_value_any()
{
    //
    // Widen TestValueSub to TestValue - the any will contain
    // the TypeCode for TestValue
    //
    TestValue_var v = get_valuesub();
    Any* result = new Any;
    *result <<= v;
    return result;
}

#ifndef HAVE_NO_CUSTOM_VALUETYPE
Any*
TestOBV_impl::get_custom_any()
{
    TestCustom_var v = get_custom();
    Any* result = new Any;
    *result <<= v;
    return result;
}
#endif

Any*
TestOBV_impl::get_trunc1_any()
{
    TestTruncBase_var v = get_trunc1();
    TestTrunc1* t1 = TestTrunc1::_downcast(v);
    TEST(t1 != 0);
    Any* result = new Any;
    *result <<= t1;
    return result;
}

Any*
TestOBV_impl::get_trunc1_as_base_any()
{
    //
    // Widen TestTrunc1 to TestTruncBase - the any will contain
    // the TypeCode for TestTruncBase
    //
    TestTruncBase_var v = get_trunc1();
    Any* result = new Any;
    *result <<= v;
    return result;
}

Any*
TestOBV_impl::get_trunc2_any()
{
    TestTruncBase_var v = get_trunc2();
    TestTrunc2* t2 = TestTrunc2::_downcast(v);
    Any* result = new Any;
    *result <<= t2;
    return result;
}

Any*
TestOBV_impl::get_trunc2_as_base_any()
{
    //
    // Widen TestTrunc2 to TestTruncBase - the any will contain
    // the TypeCode for TestTruncBase
    //
    TestTruncBase_var v = get_trunc2();
    Any* result = new Any;
    *result <<= v;
    return result;
}

void
TestOBV_impl::remarshal_any(const Any&)
{
    // nothing to do
}

void
TestOBV_impl::get_two_value_anys(Any_out a1, Any_out a2)
{
    TestValue_var v = get_value();
    Any_var av1 = new Any();
    av1 <<= v;
    Any_var av2 = new Any();
    av2 <<= v;
    a1 = av1._retn();
    a2 = av2._retn();
}

void
TestOBV_impl::set_two_value_anys(const Any& a1, const Any& a2)
{
    TestValue* v1;
    TestValue* v2;
    TEST(a1 >>= v1);
    TEST(v1 != 0);
    TEST(a2 >>= v2);
    TEST(v2 != 0);
    TEST(v1 == v2);
}

TestValueInterface*
TestOBV_impl::get_value_as_value()
{
    add_ref(valueInterface_);
    return valueInterface_;
}

TestInterface_ptr
TestOBV_impl::get_value_as_interface()
{
    TestValueInterface_impl* impl =
        dynamic_cast<TestValueInterface_impl*>(valueInterface_.in());
    TEST(impl != 0);
    return impl -> _this();
}

void
TestOBV_impl::deactivate()
{
    orb_ -> shutdown(false);
}

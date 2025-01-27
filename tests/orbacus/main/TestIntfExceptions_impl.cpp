// **********************************************************************
//
// Copyright (c) 2001
// IONA Technologies, Inc.
// Waltham, MA, USA
//
// All Rights Reserved
//
// **********************************************************************

#include <includes.h>

#include <TestIntfExceptions_impl.h>

#include <limits.h>

using namespace CORBA;

TestIntfExceptions_impl::TestIntfExceptions_impl()
{
}

void
TestIntfExceptions_impl::op_UNKNOWN_Ex()
{
    throw UNKNOWN(1, COMPLETED_YES);
}

void
TestIntfExceptions_impl::op_BAD_PARAM_Ex()
{
    throw BAD_PARAM(2, COMPLETED_NO);
}

void
TestIntfExceptions_impl::op_NO_MEMORY_Ex()
{
    throw NO_MEMORY(3, COMPLETED_MAYBE);
}

void
TestIntfExceptions_impl::op_IMP_LIMIT_Ex()
{
    throw IMP_LIMIT(4, COMPLETED_YES);
}

void
TestIntfExceptions_impl::op_COMM_FAILURE_Ex()
{
    throw COMM_FAILURE(5, COMPLETED_NO);
}

void
TestIntfExceptions_impl::op_INV_OBJREF_Ex()
{
    throw INV_OBJREF(6, COMPLETED_MAYBE);
}

void
TestIntfExceptions_impl::op_NO_PERMISSION_Ex()
{
    throw NO_PERMISSION(7, COMPLETED_YES);
}

void
TestIntfExceptions_impl::op_INTERNAL_Ex()
{
    throw INTERNAL(8, COMPLETED_NO);
}

void
TestIntfExceptions_impl::op_MARSHAL_Ex()
{
    throw MARSHAL(9, COMPLETED_MAYBE);
}

void
TestIntfExceptions_impl::op_INITIALIZE_Ex()
{
    throw INITIALIZE(10, COMPLETED_YES);
}

void
TestIntfExceptions_impl::op_NO_IMPLEMENT_Ex()
{
    throw NO_IMPLEMENT(11, COMPLETED_NO);
}

void
TestIntfExceptions_impl::op_BAD_TYPECODE_Ex()
{
    throw BAD_TYPECODE(12, COMPLETED_MAYBE);
}

void
TestIntfExceptions_impl::op_BAD_OPERATION_Ex()
{
    throw BAD_OPERATION(13, COMPLETED_YES);
}

void
TestIntfExceptions_impl::op_NO_RESOURCES_Ex()
{
    throw NO_RESOURCES(14, COMPLETED_NO);
}

void
TestIntfExceptions_impl::op_NO_RESPONSE_Ex()
{
    throw NO_RESPONSE(15, COMPLETED_MAYBE);
}

void
TestIntfExceptions_impl::op_BAD_INV_ORDER_Ex()
{
    throw BAD_INV_ORDER(17, COMPLETED_NO);
}

void
TestIntfExceptions_impl::op_TRANSIENT_Ex()
{
    throw TRANSIENT(18, COMPLETED_MAYBE);
}

void
TestIntfExceptions_impl::op_OBJ_ADAPTER_Ex()
{
    throw OBJ_ADAPTER(24, COMPLETED_MAYBE);
}

void
TestIntfExceptions_impl::op_DATA_CONVERSION_Ex()
{
    throw DATA_CONVERSION(25, COMPLETED_YES);
}

void
TestIntfExceptions_impl::op_OBJECT_NOT_EXIST_Ex()
{
    throw OBJECT_NOT_EXIST(26, COMPLETED_NO);
}

void
TestIntfExceptions_impl::op_INV_POLICY_Ex()
{
    throw INV_POLICY(30, COMPLETED_NO);
}

// **********************************************************************
//
// Copyright (c) 1999
// Object Oriented Concepts, Inc.
// Billerica, MA, USA
//
// All Rights Reserved
//
// **********************************************************************

#ifndef TEST_CUSTOM_IMPL_H
#define TEST_CUSTOM_IMPL_H

//
// IDL:TestCustom:1.0
//
class TestCustom_impl : virtual public OBV_TestCustom,
                        virtual public CORBA::DefaultValueRefCountBase
{
    TestCustom_impl(const TestCustom_impl&);
    void operator=(const TestCustom_impl&);

public:

    TestCustom_impl();
    ~TestCustom_impl();

    virtual CORBA::ValueBase* _copy_value();

    virtual void marshal(CORBA::DataOutputStream* os);
    virtual void unmarshal(CORBA::DataInputStream* is);

    //
    // IDL:TestAbsValue1/ping1:1.0
    //
    virtual void ping1();
};

#endif

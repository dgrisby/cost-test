// **********************************************************************
//
// Copyright (c) 2001
// IONA Technologies, Inc.
// Waltham, MA, USA
//
// All Rights Reserved
//
// **********************************************************************

#ifndef TEST_INTF_FIXED_IMPL_H
#define TEST_INTF_FIXED_IMPL_H

#include <TestIntfFixed_skel.h>

class TestIntfFixed_impl
    : virtual public POA_ORBTest_Fixed::Intf
{
    ORBTest_Fixed::TestFixed m_aFixed;

public:
    TestIntfFixed_impl();

    virtual ORBTest_Fixed::TestFixed
    attrFixed();

    virtual void
    attrFixed(
	const ORBTest_Fixed::TestFixed&
    );

    virtual ORBTest_Fixed::TestFixed
    opFixed(
	const ORBTest_Fixed::TestFixed&,
	ORBTest_Fixed::TestFixed&,
	ORBTest_Fixed::TestFixed_out
    );

    virtual ORBTest_Fixed::TestFixed
    opFixedEx(
	const ORBTest_Fixed::TestFixed&,
	ORBTest_Fixed::TestFixed&,
	ORBTest_Fixed::TestFixed_out
    );
};

#endif

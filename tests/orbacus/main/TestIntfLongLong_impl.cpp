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

#include <TestIntfLongLong_impl.h>

#include <limits.h>

using namespace CORBA;

TestIntfLongLong_impl::TestIntfLongLong_impl()
{
}

LongLong
TestIntfLongLong_impl::attrLongLong()
{
    return m_aLongLong;
}

void
TestIntfLongLong_impl::attrLongLong(
    LongLong val
)
{
    m_aLongLong = val;
}

LongLong
TestIntfLongLong_impl::opLongLong(
    LongLong a0,
    LongLong& a1,
    LongLong& a2
)
{
    m_aLongLong = a0 + a1;
    a1 = a2 = m_aLongLong;
    return m_aLongLong;
}

LongLong
TestIntfLongLong_impl::opLongLongEx(
    LongLong a0,
    LongLong& a1,
    LongLong&
)
{
    m_aLongLong = a0 + a1;
    throw ORBTest_LongLong::ExLongLong(m_aLongLong);
    return 0; // Some compilers need this
}

ULongLong
TestIntfLongLong_impl::attrULongLong()
{
    return m_aULongLong;
}

void
TestIntfLongLong_impl::attrULongLong(
    ULongLong val
)
{
    m_aULongLong = val;
}

ULongLong
TestIntfLongLong_impl::opULongLong(
    ULongLong a0,
    ULongLong& a1,
    ULongLong& a2
)
{
    m_aULongLong = a0 + a1;
    a1 = a2 = m_aULongLong;
    return m_aULongLong;
}

ULongLong
TestIntfLongLong_impl::opULongLongEx(
    ULongLong a0,
    ULongLong& a1,
    ULongLong&
)
{
    m_aULongLong = a0 + a1;
    throw ORBTest_LongLong::ExULongLong(m_aULongLong);
    return 0; // Some compilers need this
}


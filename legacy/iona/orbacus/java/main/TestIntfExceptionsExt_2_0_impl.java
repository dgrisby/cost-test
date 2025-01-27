// **********************************************************************
//
// Copyright (c) 2001
// IONA Technologies, Inc.
// Waltham, MA, USA
//
// All Rights Reserved
//
// **********************************************************************

package ORBTest;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;

final class TestIntfExceptionsExt_2_0_impl
    extends ORBTest_ExceptionsExt_2_0.IntfPOA
{
    private POA m_poa;

    public
    TestIntfExceptionsExt_2_0_impl(
	POA poa
    )
    {
	m_poa = poa;
    }

    public synchronized void
    op_PERSIST_STORE_Ex()
    {
	throw new PERSIST_STORE(16, CompletionStatus.COMPLETED_YES);
    }

    public synchronized void
    op_FREE_MEM_Ex()
    {
	throw new FREE_MEM(19, CompletionStatus.COMPLETED_YES);
    }

    public synchronized void
    op_INV_IDENT_Ex()
    {
	throw new INV_IDENT(20, CompletionStatus.COMPLETED_NO);
    }

    public synchronized void
    op_INV_FLAG_Ex()
    {
	throw new INV_FLAG(21, CompletionStatus.COMPLETED_MAYBE);
    }

    public synchronized void
    op_INTF_REPOS_Ex()
    {
	throw new INTF_REPOS(22, CompletionStatus.COMPLETED_YES);
    }

    public synchronized void
    op_BAD_CONTEXT_Ex()
    {
	throw new BAD_CONTEXT(23, CompletionStatus.COMPLETED_NO);
    }

    public synchronized void
    op_TRANSACTION_REQUIRED_Ex()
    {
	throw new TRANSACTION_REQUIRED(27, CompletionStatus.COMPLETED_NO);
    }

    public synchronized void
    op_TRANSACTION_ROLLEDBACK_Ex()
    {
	throw new TRANSACTION_ROLLEDBACK(28, CompletionStatus.COMPLETED_NO);
    }

    public synchronized void
    op_INVALID_TRANSACTION_Ex()
    {
	throw new INVALID_TRANSACTION(29, CompletionStatus.COMPLETED_NO);
    }

    public org.omg.PortableServer.POA
    _default_POA()
    {
	return m_poa;
    }
}

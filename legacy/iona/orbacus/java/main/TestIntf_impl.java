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

final class TestIntf_impl extends ORBTest.IntfPOA
{
    private ORB m_orb;
    private POA m_poa;

    private TestIntfBasic_impl m_test_intf_basic_impl;
    private TestIntfBasicDSI_impl m_test_intf_basic_dsi_impl;
    private ORBTest_Basic.Intf m_test_intf_basic;
    private ORBTest_Basic.Intf m_test_intf_basic_dsi;

    private TestIntfContext_impl m_test_intf_context_impl;
    private TestIntfContextDSI_impl m_test_intf_context_dsi_impl;
    private ORBTest_Context.Intf m_test_intf_context;
    private ORBTest_Context.Intf m_test_intf_context_dsi;

    private TestIntfExceptions_impl m_test_intf_exceptions_impl;
    private TestIntfExceptionsDSI_impl m_test_intf_exceptions_dsi_impl;
    private ORBTest_Exceptions.Intf m_test_intf_exceptions;
    private ORBTest_Exceptions.Intf m_test_intf_exceptions_dsi;

    private TestIntfExceptionsExt_2_0_impl m_test_intf_exceptions_ext_2_0_impl;
    private TestIntfExceptionsExt_2_0DSI_impl
	m_test_intf_exceptions_ext_2_0_dsi_impl;
    private ORBTest_ExceptionsExt_2_0.Intf m_test_intf_exceptions_ext_2_0;
    private ORBTest_ExceptionsExt_2_0.Intf m_test_intf_exceptions_ext_2_0_dsi;

    private TestIntfWChar_impl m_test_intf_wchar_impl;
    private TestIntfWCharDSI_impl m_test_intf_wchar_dsi_impl;
    private ORBTest_WChar.Intf m_test_intf_wchar;
    private ORBTest_WChar.Intf m_test_intf_wchar_dsi;

    private TestIntfFixed_impl m_test_intf_fixed_impl;
    private TestIntfFixedDSI_impl m_test_intf_fixed_dsi_impl;
    private ORBTest_Fixed.Intf m_test_intf_fixed;
    private ORBTest_Fixed.Intf m_test_intf_fixed_dsi;

    private TestIntfLongLong_impl m_test_intf_long_long_impl;
    private TestIntfLongLongDSI_impl m_test_intf_long_long_dsi_impl;
    private ORBTest_LongLong.Intf m_test_intf_long_long;
    private ORBTest_LongLong.Intf m_test_intf_long_long_dsi;

    private TestIntfExceptionsExt_2_3_impl m_test_intf_exceptions_ext_2_3_impl;
    private TestIntfExceptionsExt_2_3DSI_impl
	m_test_intf_exceptions_ext_2_3_dsi_impl;
    private ORBTest_ExceptionsExt_2_3.Intf m_test_intf_exceptions_ext_2_3;
    private ORBTest_ExceptionsExt_2_3.Intf m_test_intf_exceptions_ext_2_3_dsi;

    private TestIntfStubTimeout_impl m_test_intf_stub_timeout_impl;
    private TestIntfStubTimeoutDSI_impl m_test_intf_stub_timeout_dsi_impl;
    private ORBTest_StubTimeout.Intf m_test_intf_stub_timeout;
    private ORBTest_StubTimeout.Intf m_test_intf_stub_timeout_dsi;

    interface TestCaseInitializer
    {
	public void
	init( org.omg.CORBA.ORB orb,
	    TestIntf_impl impl,
	    ORBTest.TestCase test_case
	);
    }

    private class TestIntfBasicInitializer implements TestCaseInitializer
    {
	ImplType m_impl_type;

	public
	TestIntfBasicInitializer(
	    ImplType impl_type
	)
	{
	    m_impl_type = impl_type;
	}

	public void
	init(
	    org.omg.CORBA.ORB orb,
	    TestIntf_impl impl,
	    ORBTest.TestCase test_case
	)
	{
	    // SSI implementation
	    //
	    if (impl.m_test_intf_basic_impl == null)
	    {
		impl.m_test_intf_basic_impl = (
		    new TestIntfBasic_impl(impl.m_poa)
		);
		impl.m_test_intf_basic = (
		    impl.m_test_intf_basic_impl._this(m_orb)
		);
	    }

	    // DSI implementation
	    //
	    if (
		m_impl_type.equals(ImplType.DSI)
		&& impl.m_test_intf_basic_dsi_impl == null
	    )
	    {
		impl.m_test_intf_basic_dsi_impl = (
		    new TestIntfBasicDSI_impl(
		    	    impl.m_orb,
			    impl.m_test_intf_basic
			)
		);

		try
		{
		    byte[] id = impl.m_poa.activate_object(
			impl.m_test_intf_basic_dsi_impl
		    );

		    org.omg.CORBA.Object obj = (
			impl.m_poa.create_reference_with_id(
			    id, "IDL:ORBTest_Basic/Intf:1.0"
			)
		    );

		    impl.m_test_intf_basic_dsi = (
			ORBTest_Basic.IntfHelper.narrow(obj)
		    );
		}
		catch (
		    org.omg.PortableServer.POAPackage.ServantAlreadyActive ex
		)
		{
		}
		catch (
		    org.omg.PortableServer.POAPackage.WrongPolicy ex
		)
		{
		}
	    }

	    test_case.impl_description = new String(m_impl_type.to_string());

	    if (m_impl_type.equals(ImplType.SSI))
	    {
		test_case.impl = impl.m_test_intf_basic;
	    }
	    else if (m_impl_type.equals(ImplType.DSI))
	    {
		test_case.impl = impl.m_test_intf_basic_dsi;
	    }
	}
    }

    private class TestIntfContextInitializer implements TestCaseInitializer
    {
	ImplType m_impl_type;

	public
	TestIntfContextInitializer(
	    ImplType impl_type
	)
	{
	    m_impl_type = impl_type;
	}

	public void
	init(
	    org.omg.CORBA.ORB orb,
	    TestIntf_impl impl,
	    ORBTest.TestCase test_case
	)
	{
	    // SSI implementation
	    //
	    if (impl.m_test_intf_context_impl == null)
	    {
		impl.m_test_intf_context_impl = (
		    new TestIntfContext_impl(impl.m_poa)
		);
		impl.m_test_intf_context = (
		    impl.m_test_intf_context_impl._this(m_orb)
		);
	    }

	    // DSI implementation
	    //
	    if (
		m_impl_type.equals(ImplType.DSI)
		&& impl.m_test_intf_context_dsi_impl == null
	    )
	    {
		impl.m_test_intf_context_dsi_impl = (
		    new TestIntfContextDSI_impl(
		    	    impl.m_orb,
			    impl.m_test_intf_context
			)
		);

		try
		{
		    byte[] id = impl.m_poa.activate_object(
			impl.m_test_intf_context_dsi_impl
		    );

		    org.omg.CORBA.Object obj = (
			impl.m_poa.create_reference_with_id(
			    id, "IDL:ORBTest_Context/Intf:1.0"
			)
		    );

		    impl.m_test_intf_context_dsi = (
			ORBTest_Context.IntfHelper.narrow(obj)
		    );
		}
		catch (
		    org.omg.PortableServer.POAPackage.ServantAlreadyActive ex
		)
		{
		}
		catch (
		    org.omg.PortableServer.POAPackage.WrongPolicy ex
		)
		{
		}
	    }

	    test_case.impl_description = new String(m_impl_type.to_string());

	    if (m_impl_type.equals(ImplType.SSI))
	    {
		test_case.impl = impl.m_test_intf_context;
	    }
	    else if (m_impl_type.equals(ImplType.DSI))
	    {
		test_case.impl = impl.m_test_intf_context_dsi;
	    }
	}
    }

    private class TestIntfExceptionsInitializer implements TestCaseInitializer
    {
	ImplType m_impl_type;

	public
	TestIntfExceptionsInitializer(
	    ImplType impl_type
	)
	{
	    m_impl_type = impl_type;
	}

	public void
	init(
	    org.omg.CORBA.ORB orb,
	    TestIntf_impl impl,
	    ORBTest.TestCase test_case
	)
	{
	    // SSI implementation
	    //
	    if (impl.m_test_intf_exceptions_impl == null)
	    {
		impl.m_test_intf_exceptions_impl = (
		    new TestIntfExceptions_impl(impl.m_poa)
		);
		impl.m_test_intf_exceptions = (
		    impl.m_test_intf_exceptions_impl._this(m_orb)
		);
	    }

	    // DSI implementation
	    //
	    if (
		m_impl_type.equals(ImplType.DSI)
		&& impl.m_test_intf_exceptions_dsi_impl == null
	    )
	    {
		impl.m_test_intf_exceptions_dsi_impl = (
		    new TestIntfExceptionsDSI_impl(impl.m_orb)
		);

		try
		{
		    byte[] id = impl.m_poa.activate_object(
			impl.m_test_intf_exceptions_dsi_impl
		    );

		    org.omg.CORBA.Object obj = (
			impl.m_poa.create_reference_with_id(
			    id, "IDL:ORBTest_Exceptions/Intf:1.0"
			)
		    );

		    impl.m_test_intf_exceptions_dsi = (
			ORBTest_Exceptions.IntfHelper.narrow(obj)
		    );
		}
		catch (
		    org.omg.PortableServer.POAPackage.ServantAlreadyActive ex
		)
		{
		}
		catch (
		    org.omg.PortableServer.POAPackage.WrongPolicy ex
		)
		{
		}
	    }

	    test_case.impl_description = new String(m_impl_type.to_string());

	    if (m_impl_type.equals(ImplType.SSI))
	    {
		test_case.impl = impl.m_test_intf_exceptions;
	    }
	    else if (m_impl_type.equals(ImplType.DSI))
	    {
		test_case.impl = impl.m_test_intf_exceptions_dsi;
	    }
	}
    }

    private class TestIntfExceptionsExt_2_0Initializer
	implements TestCaseInitializer
    {
	ImplType m_impl_type;

	public
	TestIntfExceptionsExt_2_0Initializer(
	    ImplType impl_type
	)
	{
	    m_impl_type = impl_type;
	}

	public void
	init(
	    org.omg.CORBA.ORB orb,
	    TestIntf_impl impl,
	    ORBTest.TestCase test_case
	)
	{
	    // SSI implementation
	    //
	    if (impl.m_test_intf_exceptions_ext_2_0_impl == null)
	    {
		impl.m_test_intf_exceptions_ext_2_0_impl = (
		    new TestIntfExceptionsExt_2_0_impl(impl.m_poa)
		);
		impl.m_test_intf_exceptions_ext_2_0 = (
		    impl.m_test_intf_exceptions_ext_2_0_impl._this(m_orb)
		);
	    }

	    // DSI implementation
	    //
	    if (
		m_impl_type.equals(ImplType.DSI)
		&& impl.m_test_intf_exceptions_ext_2_0_dsi_impl == null
	    )
	    {
		impl.m_test_intf_exceptions_ext_2_0_dsi_impl = (
		    new TestIntfExceptionsExt_2_0DSI_impl(
			    impl.m_orb
			)
		);

		try
		{
		    byte[] id = impl.m_poa.activate_object(
			impl.m_test_intf_exceptions_ext_2_0_dsi_impl
		    );

		    org.omg.CORBA.Object obj = (
			impl.m_poa.create_reference_with_id(
			    id, "IDL:ORBTest_ExceptionsExt_2_0/Intf:1.0"
			)
		    );

		    impl.m_test_intf_exceptions_ext_2_0_dsi = (
			ORBTest_ExceptionsExt_2_0.IntfHelper.narrow(obj)
		    );
		}
		catch (
		    org.omg.PortableServer.POAPackage.ServantAlreadyActive ex
		)
		{
		}
		catch (
		    org.omg.PortableServer.POAPackage.WrongPolicy ex
		)
		{
		}
	    }

	    test_case.impl_description = new String(m_impl_type.to_string());

	    if (m_impl_type.equals(ImplType.SSI))
	    {
		test_case.impl = impl.m_test_intf_exceptions_ext_2_0;
	    }
	    else if (m_impl_type.equals(ImplType.DSI))
	    {
		test_case.impl = impl.m_test_intf_exceptions_ext_2_0_dsi;
	    }
	}
    }

    private class TestIntfWCharInitializer implements TestCaseInitializer
    {
	ImplType m_impl_type;

	public
	TestIntfWCharInitializer(
	    ImplType impl_type
	)
	{
	    m_impl_type = impl_type;
	}

	public void
	init(
	    org.omg.CORBA.ORB orb,
	    TestIntf_impl impl,
	    ORBTest.TestCase test_case
	)
	{
	    // SSI implementation
	    //
	    if (impl.m_test_intf_wchar_impl == null)
	    {
		impl.m_test_intf_wchar_impl = (
		    new TestIntfWChar_impl(impl.m_poa)
		);
		impl.m_test_intf_wchar = (
		    impl.m_test_intf_wchar_impl._this(m_orb)
		);
	    }

	    // DSI implementation
	    //
	    if (
		m_impl_type.equals(ImplType.DSI)
		&& impl.m_test_intf_wchar_dsi_impl == null
	    )
	    {
		impl.m_test_intf_wchar_dsi_impl = (
		    new TestIntfWCharDSI_impl(
		    	    impl.m_orb,
			    impl.m_test_intf_wchar
			)
		);

		try
		{
		    byte[] id = impl.m_poa.activate_object(
			impl.m_test_intf_wchar_dsi_impl
		    );

		    org.omg.CORBA.Object obj = (
			impl.m_poa.create_reference_with_id(
			    id, "IDL:ORBTest_WChar/Intf:1.0"
			)
		    );

		    impl.m_test_intf_wchar_dsi = (
			ORBTest_WChar.IntfHelper.narrow(obj)
		    );
		}
		catch (
		    org.omg.PortableServer.POAPackage.ServantAlreadyActive ex
		)
		{
		}
		catch (
		    org.omg.PortableServer.POAPackage.WrongPolicy ex
		)
		{
		}
	    }

	    test_case.impl_description = new String(m_impl_type.to_string());

	    if (m_impl_type.equals(ImplType.SSI))
	    {
		test_case.impl = impl.m_test_intf_wchar;
	    }
	    else if (m_impl_type.equals(ImplType.DSI))
	    {
		test_case.impl = impl.m_test_intf_wchar_dsi;
	    }
	}
    }

    private class TestIntfFixedInitializer implements TestCaseInitializer
    {
	ImplType m_impl_type;

	public
	TestIntfFixedInitializer(
	    ImplType impl_type
	)
	{
	    m_impl_type = impl_type;
	}

	public void
	init(
	    org.omg.CORBA.ORB orb,
	    TestIntf_impl impl,
	    ORBTest.TestCase test_case
	)
	{
	    // SSI implementation
	    //
	    if (impl.m_test_intf_fixed_impl == null)
	    {
		impl.m_test_intf_fixed_impl = (
		    new TestIntfFixed_impl(impl.m_poa)
		);
		impl.m_test_intf_fixed = (
		    impl.m_test_intf_fixed_impl._this(m_orb)
		);
	    }

	    // DSI implementation
	    //
	    if (
		m_impl_type.equals(ImplType.DSI)
		&& impl.m_test_intf_fixed_dsi_impl == null
	    )
	    {
		impl.m_test_intf_fixed_dsi_impl = (
		    new TestIntfFixedDSI_impl(
		    	    impl.m_orb,
			    impl.m_test_intf_fixed
			)
		);

		try
		{
		    byte[] id = impl.m_poa.activate_object(
			impl.m_test_intf_fixed_dsi_impl
		    );

		    org.omg.CORBA.Object obj = (
			impl.m_poa.create_reference_with_id(
			    id, "IDL:ORBTest_Fixed/Intf:1.0"
			)
		    );

		    impl.m_test_intf_fixed_dsi = (
			ORBTest_Fixed.IntfHelper.narrow(obj)
		    );
		}
		catch (
		    org.omg.PortableServer.POAPackage.ServantAlreadyActive ex
		)
		{
		}
		catch (
		    org.omg.PortableServer.POAPackage.WrongPolicy ex
		)
		{
		}
	    }

	    test_case.impl_description = new String(m_impl_type.to_string());

	    if (m_impl_type.equals(ImplType.SSI))
	    {
		test_case.impl = impl.m_test_intf_fixed;
	    }
	    else if (m_impl_type.equals(ImplType.DSI))
	    {
		test_case.impl = impl.m_test_intf_fixed_dsi;
	    }
	}
    }

    private class TestIntfLongLongInitializer implements TestCaseInitializer
    {
	ImplType m_impl_type;

	public
	TestIntfLongLongInitializer(
	    ImplType impl_type
	)
	{
	    m_impl_type = impl_type;
	}

	public void
	init(
	    org.omg.CORBA.ORB orb,
	    TestIntf_impl impl,
	    ORBTest.TestCase test_case
	)
	{
	    // SSI implementation
	    //
	    if (impl.m_test_intf_long_long_impl == null)
	    {
		impl.m_test_intf_long_long_impl = (
		    new TestIntfLongLong_impl(impl.m_poa)
		);
		impl.m_test_intf_long_long = (
		    impl.m_test_intf_long_long_impl._this(m_orb)
		);
	    }

	    // DSI implementation
	    //
	    if (
		m_impl_type.equals(ImplType.DSI)
		&& impl.m_test_intf_long_long_dsi_impl == null
	    )
	    {
		impl.m_test_intf_long_long_dsi_impl = (
		    new TestIntfLongLongDSI_impl(
		    	    impl.m_orb,
			    impl.m_test_intf_long_long
			)
		);

		try
		{
		    byte[] id = impl.m_poa.activate_object(
			impl.m_test_intf_long_long_dsi_impl
		    );

		    org.omg.CORBA.Object obj = (
			impl.m_poa.create_reference_with_id(
			    id, "IDL:ORBTest_LongLong/Intf:1.0"
			)
		    );

		    impl.m_test_intf_long_long_dsi = (
			ORBTest_LongLong.IntfHelper.narrow(obj)
		    );
		}
		catch (
		    org.omg.PortableServer.POAPackage.ServantAlreadyActive ex
		)
		{
		}
		catch (
		    org.omg.PortableServer.POAPackage.WrongPolicy ex
		)
		{
		}
	    }

	    test_case.impl_description = new String(m_impl_type.to_string());

	    if (m_impl_type.equals(ImplType.SSI))
	    {
		test_case.impl = impl.m_test_intf_long_long;
	    }
	    else if (m_impl_type.equals(ImplType.DSI))
	    {
		test_case.impl = impl.m_test_intf_long_long_dsi;
	    }
	}
    }

    private class TestIntfExceptionsExt_2_3Initializer
	implements TestCaseInitializer
    {
	ImplType m_impl_type;

	public
	TestIntfExceptionsExt_2_3Initializer(
	    ImplType impl_type
	)
	{
	    m_impl_type = impl_type;
	}

	public void
	init(
	    org.omg.CORBA.ORB orb,
	    TestIntf_impl impl,
	    ORBTest.TestCase test_case
	)
	{
	    // SSI implementation
	    //
	    if (impl.m_test_intf_exceptions_ext_2_3_impl == null)
	    {
		impl.m_test_intf_exceptions_ext_2_3_impl = (
		    new TestIntfExceptionsExt_2_3_impl(impl.m_poa)
		);
		impl.m_test_intf_exceptions_ext_2_3 = (
		    impl.m_test_intf_exceptions_ext_2_3_impl._this(m_orb)
		);
	    }

	    // DSI implementation
	    //
	    if (
		m_impl_type.equals(ImplType.DSI)
		&& impl.m_test_intf_exceptions_ext_2_3_dsi_impl == null
	    )
	    {
		impl.m_test_intf_exceptions_ext_2_3_dsi_impl = (
		    new TestIntfExceptionsExt_2_3DSI_impl(
		    	    impl.m_orb
			)
		);

		try
		{
		    byte[] id = impl.m_poa.activate_object(
			impl.m_test_intf_exceptions_ext_2_3_dsi_impl
		    );

		    org.omg.CORBA.Object obj = (
			impl.m_poa.create_reference_with_id(
			    id, "IDL:ORBTest_ExceptionsExt_2_3/Intf:1.0"
			)
		    );

		    impl.m_test_intf_exceptions_ext_2_3_dsi = (
			ORBTest_ExceptionsExt_2_3.IntfHelper.narrow(obj)
		    );
		}
		catch (
		    org.omg.PortableServer.POAPackage.ServantAlreadyActive ex
		)
		{
		}
		catch (
		    org.omg.PortableServer.POAPackage.WrongPolicy ex
		)
		{
		}
	    }

	    test_case.impl_description = new String(m_impl_type.to_string());

	    if (m_impl_type.equals(ImplType.SSI))
	    {
		test_case.impl = impl.m_test_intf_exceptions_ext_2_3;
	    }
	    else if (m_impl_type.equals(ImplType.DSI))
	    {
		test_case.impl = impl.m_test_intf_exceptions_ext_2_3_dsi;
	    }
	}
    }

    private class TestIntfStubTimeoutInitializer implements TestCaseInitializer
    {
	ImplType m_impl_type;

	public
	TestIntfStubTimeoutInitializer(
	    ImplType impl_type
	)
	{
	    m_impl_type = impl_type;
	}

	public void
	init(
	    org.omg.CORBA.ORB orb,
	    TestIntf_impl impl,
	    ORBTest.TestCase test_case
	)
	{
	    // SSI implementation
	    //
	    if (impl.m_test_intf_stub_timeout_impl == null)
	    {
		impl.m_test_intf_stub_timeout_impl = (
		    new TestIntfStubTimeout_impl(impl.m_poa)
		);
		impl.m_test_intf_stub_timeout = (
		    impl.m_test_intf_stub_timeout_impl._this(m_orb)
		);
	    }

	    // DSI implementation
	    //
	    if (
		m_impl_type.equals(ImplType.DSI)
		&& impl.m_test_intf_stub_timeout_dsi_impl == null
	    )
	    {
		impl.m_test_intf_stub_timeout_dsi_impl = (
		    new TestIntfStubTimeoutDSI_impl(
		    	    impl.m_orb,
			    impl.m_test_intf_stub_timeout
			)
		);

		try
		{
		    byte[] id = impl.m_poa.activate_object(
			impl.m_test_intf_stub_timeout_dsi_impl
		    );

		    org.omg.CORBA.Object obj = (
			impl.m_poa.create_reference_with_id(
			    id, "IDL:ORBTest_StubTimeout/Intf:1.0"
			)
		    );

		    impl.m_test_intf_stub_timeout_dsi = (
			ORBTest_StubTimeout.IntfHelper.narrow(obj)
		    );
		}
		catch (
		    org.omg.PortableServer.POAPackage.ServantAlreadyActive ex
		)
		{
		}
		catch (
		    org.omg.PortableServer.POAPackage.WrongPolicy ex
		)
		{
		}
	    }

	    test_case.impl_description = new String(m_impl_type.to_string());

	    if (m_impl_type.equals(ImplType.SSI))
	    {
		test_case.impl = impl.m_test_intf_stub_timeout;
	    }
	    else if (m_impl_type.equals(ImplType.DSI))
	    {
		test_case.impl = impl.m_test_intf_stub_timeout_dsi;
	    }
	}
    }

    private TestCaseInitializer test_case_initializers[] =
    {
    	new TestIntfBasicInitializer(ImplType.SSI),
    	new TestIntfContextInitializer(ImplType.SSI),
    	new TestIntfExceptionsInitializer(ImplType.SSI),
    	new TestIntfExceptionsExt_2_0Initializer(ImplType.SSI),
    	new TestIntfWCharInitializer(ImplType.SSI),
    	new TestIntfFixedInitializer(ImplType.SSI),
    	new TestIntfLongLongInitializer(ImplType.SSI),
    	new TestIntfExceptionsExt_2_3Initializer(ImplType.SSI),
    	new TestIntfStubTimeoutInitializer(ImplType.SSI),

    	new TestIntfBasicInitializer(ImplType.DSI),
    	new TestIntfContextInitializer(ImplType.DSI),
    	new TestIntfExceptionsInitializer(ImplType.DSI),
    	new TestIntfExceptionsExt_2_0Initializer(ImplType.DSI),
    	new TestIntfWCharInitializer(ImplType.DSI),
    	new TestIntfFixedInitializer(ImplType.DSI),
    	new TestIntfLongLongInitializer(ImplType.DSI),
    	new TestIntfExceptionsExt_2_3Initializer(ImplType.DSI),
    	new TestIntfStubTimeoutInitializer(ImplType.DSI)
    };

    public
    TestIntf_impl(
	ORB orb,
	POA poa
    )
    {
        m_orb = orb;
	m_poa = poa;
    }

    public synchronized void
    deactivate()
    {
	m_orb.shutdown(false);
    }

    public synchronized boolean
    concurrent_request_execution()
    {
        POA poa = _default_POA();
        com.ooc.OBPortableServer.POA obpoa =
            com.ooc.OBPortableServer.POAHelper.narrow(poa);
        com.ooc.OB.DispatchStrategy strategy = obpoa.the_dispatch_strategy();

        return strategy.id() != com.ooc.OB.SAME_THREAD.value;
    }

    public synchronized TestCase[]
    get_test_case_list()
    {
	int num_test_cases = test_case_initializers.length;
	ORBTest.TestCase[] ret = new ORBTest.TestCase[num_test_cases];

	// Construct the list of supported test cases
	//
	for (int i = 0; i < num_test_cases; ++i)
	{
	    ret[i] = new ORBTest.TestCase();
	    test_case_initializers[i].init(m_orb, this, ret[i]);
	}

	return ret;
    }

    public org.omg.PortableServer.POA
    _default_POA()
    {
	return m_poa;
    }
}

ORBacus testsuite
=================

This is the ORBacus C++ testsuite, contributed to the OMG COST project
by Iona. The code here is all licensed under the terms of the GNU
Lesser Public License, available in COPYING.LIB.


1. Setting up

In order to configure the tests you have to create a file config/Make.rules
that reflects your system configuration. For ORBacus 4.1, Mico, omniORB and
Orbix2000 2.0 templates are provided that should help setting everything up.
These files are located in the config folder. Just copy one of them to
config/Make.rules if you have one of these ORBs and then edit this file to
reflect your setup.
Additionally for other ORBs than those listed above you should edit
include/includes.h to reflect the vendor-specific files that have to be
included.
Note that the tests require an ORB that implements a mapping based on
namespaces.

2. Optional tests

For ORBs that do not support the optional Fixed data type use
-DHAVE_NO_FIXED_TYPE when compiling the IDL and the generated code.
For ORBs that do not support the optional long double data type use
-DHAVE_NO_LONG_DOUBLE_TYPE when compiling the IDL and the generated code.
For ORBs that do not support valuetypes use -DHAVE_NO_VALUETYPE
when compiling the IDL and the generated code.
For ORBs that do not support CORBA 2.4 use -DHAVE_NO_CORBA_2_4
when compiling the IDL and the generated code.

3. Compiling the sources

For ORBs that support stream inserters for exceptions you should add
-DHAVE_EXCEPTION_INSERTERS when compiling the generated code.

Type 'make' in the top level tree.

4. Running the tests

Each folder provides a runtest shell script that starts the servers and
clients used for each test. You can run all the tests at once by starting
the runtest script in the toplevel folder.

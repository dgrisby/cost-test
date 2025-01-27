remote-executor.py
==================

Server process for executing a program on a remote machine via an IDL
interface.

WARNING!

The remote-executor can be asked to execute any command on your
machine. The security risk should be obvious. You should never run
this script on a machine that can be reached by untrusted clients.


On Windows you need a recent version of the Python Win32 Extensions
(i.e. win32all-132 works fine).

Usage: remote-executor.py -n <namingcontext> [-v] [<name>]

       -n | --executor-namecontext=
              the remote-executor will register its object reference as
              "<namingcontext>/<name>.executor"

       -v | --verbose
              be more verbose...

       <name>
              optional, local hostname is used if not specified



test-controller.py
==================

Test controller program. The defaults are set up to make it most
easily usable for the omniORB testsuite, but it isn't limited to
omniORB or the omniORB testsuite.

Usage: ./test-controller.py -d <local-testsrc-dir> -n <namingcontext>
       -c <host-conf-file>
       [-b <build-base>] [-B <builddir-base>] [-i <iterations>]
       [-t <build-test>] [-T <builddir-test>] [-p <priority-tests>]
       [-u] [-v] <host> <host>...


       -c | --host-configuration=
              testhost configuration file (see testconf.py)

       -d | --local-testsrc-dir=
              testsuite source directory on the local machine; this is
              used for accessing the test configuration files
              (e.g. "/mnt/scratch/chm/omni/src/testsuite")

       -i | --iterations=
              number of iterations the tests should be run; default is 1
              (use 0 for infinite iterations)

       -n | --executor-namecontext=
              namecontext of the remote executor objects

       -b | --build-base=
              command to build the base program; the string "$PLATFORM"
              is replaced with the platform name given in the test-host
              configuration
              (default: "make all")

       -B | --builddir-base=
              build directory for the base program (relative to the
              directory given in the test-host configuration file);
              the string "$PLATFORM" is replaced with the platform name
              given in the test-host configuration
              (default: "build/$PLATFORM")

       -p | --priority=
              comma-separated list of test subdirectories which should be
              built before all other tests
              (default: "common")

       -t | --build-test=
              command to build the test subdirectory; the string "$PLATFORM"
              is replaced with the platform name given in the test-host
              configuration and the string "$TEST" is replaced with the
              current test subdirectory
              (default: "omake all")

       -T | --builddir-test=
              build directory for the test programs (relative to the
              directory given in the test-host configuration file);
              the string "$PLATFORM" is replaced with the platform name
              given in the test-host configuration and the string "$TEST"
              is replaced with the current test subdirectory
              (default: "build/$PLATFORM/testsuite/$TEST")

       -v | --verbose
              be more verbose...

       <host> <host>...
              list of hosts where the tests should be run; the client is
              run on the first host specified, the servers are run on the
              following specified hosts. (If only one hostname is given,
              all programs are executed on this host.) A remote-executor
              is assumed to be already running on a specified host.

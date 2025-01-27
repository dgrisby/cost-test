package com.prismt.orb.dynany;

/**
 * DynAnyUnionIntTest.java
 *
 * DynAny tests for union types using an integer discriminator.
 *
 */

import junit.framework.*;
import junit.extensions.TestSetup;
import org.omg.CORBA.TCKind;

import com.prismt.common.ORBSetup;
import com.prismt.orb.Tests.UnionDefaultIntType;
import com.prismt.orb.Tests.UnionDefaultIntTypeHelper;
import com.prismt.orb.Tests.UnionNoDefaultIntType;
import com.prismt.orb.Tests.UnionNoDefaultIntTypeHelper;

public class DynAnyUnionIntTest extends TestCase
{
   private static org.omg.DynamicAny.DynAnyFactory factory = null;
   private static org.omg.CORBA.ORB orb = null;

   private static final String ID = "IDL:test:1.0";
   private static final String NAME = "MyUnion";

   public DynAnyUnionIntTest (String name)
   {
      super (name);
   }


   public static Test suite ()
   {
      TestSuite suite = new TestSuite
         ("DynUnion Tests using Integer Discriminator");
      Setup setup = new Setup (suite);
      ORBSetup osetup = new ORBSetup (setup);

      suite.addTest (new DynAnyUnionIntTest ("testFactoryCreateFromAny"));
      suite.addTest (new DynAnyUnionIntTest ("testFactoryCreateFromTypeCode"));
      suite.addTest (new DynAnyUnionIntTest ("testFactoryCreateFromIDLTypeCode"));
      suite.addTest (new DynAnyUnionIntTest ("testCompareDynAnyUnamedMember"));
      suite.addTest (new DynAnyUnionIntTest ("testCompareDynAnyNamedMember"));
      suite.addTest (new DynAnyUnionIntTest ("testIterateDynAnyNamedMember"));
      suite.addTest (new DynAnyUnionIntTest ("testIterateDynAnyUnamedMember"));
      suite.addTest (new DynAnyUnionIntTest ("testAccessUnionDisc"));
      suite.addTest (new DynAnyUnionIntTest ("testUnionDefaultCase"));
      suite.addTest (new DynAnyUnionIntTest ("testUnionNoDefaultCase"));
      suite.addTest (new DynAnyUnionIntTest ("testAccessNamedUnionMember"));
      suite.addTest (new DynAnyUnionIntTest ("testAccessUnamedUnionMember"));
      suite.addTest (new DynAnyUnionIntTest ("testDynAnyTypeCode"));
      suite.addTest (new DynAnyUnionIntTest ("testInitDynAnyFromDynAny"));
      suite.addTest (new DynAnyUnionIntTest ("testInitDynAnyFromAny"));
      suite.addTest (new DynAnyUnionIntTest ("testInitFromAnyTypeMismatchEx"));
      suite.addTest (new DynAnyUnionIntTest ("testGenerateAnyFromDynAny"));
      suite.addTest (new DynAnyUnionIntTest ("testDestroyDynAny"));
      suite.addTest (new DynAnyUnionIntTest ("testDestroyComponent"));
      suite.addTest (new DynAnyUnionIntTest ("testCopyDynAny"));
      
      return osetup;
   }


   /**
    * Tests creating a DynAny object from an Any object using the
    * DynAnyFactory object.
    */
   public void testFactoryCreateFromAny ()
   {
      UnionDefaultIntType type = null;
      org.omg.CORBA.Any any = null;

      type = new UnionDefaultIntType ();
      type.win (10);
      any = orb.create_any ();
      UnionDefaultIntTypeHelper.insert (any, type);
      
      createDynAnyFromAny (any);
   }


   /**
    * Tests creating a DynAny object from a TypeCode object using the
    * DynAnyFactory object.
    */
   public void testFactoryCreateFromTypeCode ()
   {
      org.omg.CORBA.TypeCode tc = null;

      tc = orb.create_union_tc
      (
         ID,
         NAME,
         orb.get_primitive_tc (TCKind.tk_short),
         getUnionMembers ()
      );
      createDynAnyFromTypeCode (tc);
   }


   /**
    * Tests creating a DynAny object from a TypeCode object generated from
    * IDL using the DynAnyFactory object.
    */
   public void testFactoryCreateFromIDLTypeCode ()
   {
      org.omg.CORBA.TypeCode tc = null;

      tc = UnionDefaultIntTypeHelper.type ();
      createDynAnyFromTypeCode (tc);
   }


   /**
    * Test comparing DynAny values.  The DynUnion does not have a named member.
    */
   public void testCompareDynAnyUnamedMember ()
   {
      String msg;
      UnionNoDefaultIntType type;
      org.omg.CORBA.Any any = null;
      org.omg.DynamicAny.DynUnion dynAny = null;
      org.omg.DynamicAny.DynUnion dynAny2 = null;

      type = new UnionNoDefaultIntType ();
      type.__default ();
      
      any = orb.create_any ();
      UnionNoDefaultIntTypeHelper.insert (any, type);
      dynAny = createDynAnyFromAny (any);
      dynAny2 = createDynAnyFromAny (any);
      
      msg = "Comparing two equal DynAny values using DynAny::equal failed";
      assertTrue (msg, dynAny.equal (dynAny2));
   }


   /**
    * Test comparing DynAny values.  The DynUnion has a named member.
    */
   public void testCompareDynAnyNamedMember ()
   {
      String msg;
      UnionDefaultIntType type;
      org.omg.CORBA.Any any = null;
      org.omg.DynamicAny.DynUnion dynAny = null;
      org.omg.DynamicAny.DynUnion dynAny2 = null;
      
      type = new UnionDefaultIntType ();
      type.win (10);
      any = orb.create_any ();
      UnionDefaultIntTypeHelper.insert (any, type);
      dynAny = createDynAnyFromAny (any);
      dynAny2 = createDynAnyFromAny (any);
      
      msg = "Comparing two equal DynAny values using DynAny::equal failed";
      assertTrue (msg, dynAny.equal (dynAny2));
   }

   
   /**
    * Test iterating through components of a DynAny.  The DynUnion has an
    * active member.
    */
   public void testIterateDynAnyNamedMember ()
   {
      String msg;
      int compCount = -1;
      boolean seek;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynUnion dynAny = null;
      org.omg.DynamicAny.DynAny compSeek = null;
      org.omg.DynamicAny.DynAny compRewind = null;
      
      tc = orb.create_union_tc
      (
         ID,
         NAME,
         orb.get_primitive_tc (TCKind.tk_short),
         getUnionMembers ()
      );
      dynAny = createDynAnyFromTypeCode (tc);

      // test the component count
      msg = "The number of components returned from the ";
      msg += "DynAny::component_count method is incorrect";
      try
      {
         compCount = dynAny.component_count ();
      }
      catch (Throwable ex)
      {
         // should not be needed, but it prevents compiler errors
         fail ("Unexpected error raised by DynAny::component_count operation");
      }
      assertEquals (msg, 2, compCount);

      // seek an invalid position
      msg = "The DynAny::seek operation indicates a valid current position ";
      msg += "when the current position should be invalid";
      seek = dynAny.seek (-1);
      assertTrue (msg, !seek);

      // seek the first position
      msg = "The DynAny::seek operation indicates an invalid current position ";
      msg += "when the current position should be valid";
      seek = dynAny.seek (0);
      assertTrue (msg, seek);

      // extract a value from the current position
      try
      {
         compSeek = dynAny.current_component ();
      }
      catch (Throwable ex)
      {
         msg = "Failed to get the current component using the ";
         msg += "DynAny::current_component operation after calling the ";
         msg += "DynAny::seek operation";
         fail (msg);
      }
      
      // seek the next position
      msg = "The DynAny::next operation indicates an invalid current position ";
      msg += "when the current position should be valid";
      seek = dynAny.next ();
      assertTrue (msg, seek);

      // return to the first position
      dynAny.rewind ();

      // extract a value from the current position
      try
      {
         compRewind = dynAny.current_component ();
      }
      catch (Throwable ex)
      {
         msg = "Failed to get the current component using the ";
         msg += "DynAny::current_component operation after calling the ";
         msg += "DynAny::rewind operation";
         fail (msg);
      }
      msg = "The component at DynAny::seek(0) is not equal to the ";
      msg += "component at DynAny::rewind";
      assertTrue (msg, compSeek.equal (compRewind));
   }


   /**
    * Test iterating through components of a DynAny.  The DynUnion does not
    * have an active member.
    */
   public void testIterateDynAnyUnamedMember ()
   {
      String msg;
      short discVal = -1;
      int compCount = -1;
      boolean seek;
      UnionNoDefaultIntType type;
      org.omg.CORBA.Any any = null;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynUnion dynAny = null;
      org.omg.DynamicAny.DynAny disc = null;
      
      type = new UnionNoDefaultIntType ();
      type.__default ();

      any = orb.create_any ();
      UnionNoDefaultIntTypeHelper.insert (any, type);
      dynAny = createDynAnyFromAny (any);

      // test that the discriminator is correctly set
      disc = dynAny.get_discriminator ();

      // specific to IDL
      msg = "The discriminator is incorrectly set for a DynAny created ";
      msg += "from a union with no active member";
      discVal = disc.to_any ().extract_short ();
      assertTrue (msg, discVal < (short) 0 || discVal > (short) 3);

      // test the component count
      try
      {
         compCount = dynAny.component_count ();
      }
      catch (Throwable ex)
      {
         // should not be needed, but it prevents compiler errors
         fail ("Unexpected error raised by DynAny::component_count operation");
      }

      msg = "The number of components returned from the ";
      msg += "DynAny::component_count operation is incorrect";
      assertEquals (msg, 1, compCount);
      
      // seek the first position
      msg = "The DynAny::seek operation indicates an invalid current position ";
      msg += "when the current position should be valid";
      seek = dynAny.seek (0);
      assertTrue (msg, seek);
      
      // seek the next position
      msg = "The DynAny::next operation indicates a valid current position ";
      msg += "when the current position should be invalid";
      seek = dynAny.next ();
      assertTrue (msg, !seek);
      
      // test getting the current component
      msg = "The object returned from the DynAny::current_component ";
      msg += "operation should be null because the current position is ";
      msg += "invalid (-1)";
      try
      {
         dynAny = (org.omg.DynamicAny.DynUnion) dynAny.current_component ();

         assertNull (msg, dynAny);
      }
      catch (Throwable ex)
      {
         fail (msg + ": " + ex);
      }
   }


   /**
    * Test accessing the discriminator of a DynUnion object.
    */
   public void testAccessUnionDisc ()
   {
      String msg;
      TCKind discKind = null;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynUnion dynAny = null;
      org.omg.DynamicAny.DynAny disc = null;
      org.omg.DynamicAny.DynAny invalidDisc = null;
      
      tc = UnionDefaultIntTypeHelper.type ();
      dynAny = createDynAnyFromTypeCode (tc);

      // test setting the discriminator
      tc = orb.get_primitive_tc (TCKind.tk_short);
      try
      {
         disc = factory.create_dyn_any_from_type_code (tc);
      }
      catch (Throwable ex)
      {
         fail ("Failed to create DynAny with correct TypeCode: " + ex);
      }

      try
      {
         disc.insert_short ((short) 1); // specific to IDL         
      }
      catch (Throwable ex)
      {
         fail ("Failed to set value of discriminator: " + ex);
      }
      
      try
      {
         dynAny.set_discriminator (disc);
      }
      catch (Throwable ex)
      {
         msg = "Failed to set the value of the discriminator using the ";
         msg += "DynUnion::set_discriminator operation";
         fail (msg + ": " + ex);
      }

      // test getting the discriminator
      msg = "Failed to get the correct value of the discriminator using the ";
      msg += "DynUnion::get_discriminator operation";
      disc = dynAny.get_discriminator ();

      // specific to IDL
      assertEquals (msg, (short) 1, disc.to_any ().extract_short ());
         
      // test getting the kind of the discriminator
      msg = "Failed to get the correct kind of the discriminator using ";
      msg += "DynUnion::discriminator_kind operation";
      discKind = dynAny.discriminator_kind ();

      // specific to IDL
      assertEquals (msg, TCKind._tk_short, discKind.value ());

      // test setting an invalid discriminator
      tc = orb.get_primitive_tc (TCKind.tk_boolean);
      try
      {
         invalidDisc = factory.create_dyn_any_from_type_code (tc);
      }
      catch (Throwable ex)
      {
         fail ("Failed to create DynAny with incorrect TypeCode: " + ex);
      }

      try
      {
         dynAny.set_discriminator (invalidDisc);

         msg = "Failed to throw a TypeMismatch exception when calling ";
         msg += "DynStruct::set_discriminator operation with an incorrect ";
         msg += "TypeCode";
         fail (msg);
      }
      catch (org.omg.DynamicAny.DynAnyPackage.TypeMismatch ex)
      {
         // success
      }
   }


   /**
    * Test setting the discriminator to the default member for a union with
    * an explicit default case.
    */
   public void testUnionDefaultCase ()
   {
      String msg;
      int compCount = -1;
      short discVal = 0;
      UnionDefaultIntType type;
      boolean hasNoActiveMember = true;
      org.omg.CORBA.Any any = null;
      org.omg.DynamicAny.DynAny disc = null;
      org.omg.DynamicAny.DynUnion dynAny = null;

      type = new UnionDefaultIntType ();
      type.win (10);
      any = orb.create_any ();
      UnionDefaultIntTypeHelper.insert (any, type);
      dynAny = createDynAnyFromAny (any);

      // test activating the default member
      msg = "Failed to set the discriminator to the default member using the ";
      msg += "DynUnion::set_to_default_member operation";
      try
      {
         dynAny.set_to_default_member ();
      }
      catch (Throwable ex)
      {
         fail (msg + ": " + ex);
      }
      disc = dynAny.get_discriminator ();

      // specific to IDL
      discVal = disc.to_any ().extract_short ();
      assertTrue (msg, discVal < 0 || discVal > 3);

      // test the component count
      try
      {
         compCount = dynAny.component_count ();
      }
      catch (Throwable ex)
      {
         // should not be needed, but it prevents compiler errors
         fail ("Unexpected error raised by DynAny::component_count operation");
      }
      msg = "Wrong number of components returned from DynAny::component_count ";
      msg += "operation after calling DynUnion::set_to_default_member ";
      msg += "operation";
      assertEquals (msg, 2, compCount);

      // test attempting to deactivate the default member
      try
      {
         dynAny.set_to_no_active_member ();

         msg = "Failed to raise a TypeMismatch exception when calling the ";
         msg += "DynUnion::set_to_no_active_member operation on a union with ";
         msg += "an explicit default case";
         fail (msg);
      }
      catch (org.omg.DynamicAny.DynAnyPackage.TypeMismatch ex)
      {
         // success
      }

      msg = "The DynUnion::has_no_active_member operation did not return ";
      msg += "FALSE when called on a union with an explicit default case";
      try
      {
         hasNoActiveMember = dynAny.has_no_active_member ();
      }
      catch (Throwable ex)
      {
         fail (msg + ": " + ex);
      }
      assertTrue (msg, !hasNoActiveMember);
   }


   /**
    * Test setting the discriminator to no active member for a union without
    * an explicit default case.
    */
   public void testUnionNoDefaultCase ()
   {
      String msg;
      short discVal = -1;
      int compCount = -1;
      UnionNoDefaultIntType type;
      boolean hasNoActiveMember = false;
      org.omg.CORBA.Any any = null;
      org.omg.DynamicAny.DynUnion dynAny = null;
      org.omg.DynamicAny.DynAny disc = null;

      type = new UnionNoDefaultIntType ();
      type.win (10);
      any = orb.create_any ();
      UnionNoDefaultIntTypeHelper.insert (any, type);
      dynAny = createDynAnyFromAny (any);

      // test deactivating the active member
      msg = "Failed to set the discriminator to no active member using the ";
      msg += "DynUnion::set_to_no_active_member operation";
      try
      {
         dynAny.set_to_no_active_member ();
      }
      catch (Throwable ex)
      {
         fail (msg + ": " + ex);
      }
      disc = dynAny.get_discriminator ();

      // specific to IDL
      discVal = disc.to_any ().extract_short ();
      assertTrue (msg, discVal < (short) 0 || discVal > (short) 3);

      // test the component count
      try
      {
         compCount = dynAny.component_count ();
      }
      catch (Throwable ex)
      {
         // should not be needed, but it prevents compiler errors
         fail ("Unexpected error raised by DynAny::component_count operation");
      }

      msg = "Wrong number of components returned from DynAny::component_count ";
      msg += "operation after calling DynUnion::set_to_no_active_member ";
      msg += "operation";
      assertEquals (msg, 1, compCount);

      // test attempting to activate the default member
      try
      {
         dynAny.set_to_default_member ();

         msg = "Failed to raise a TypeMismatch exception when calling the ";
         msg += "DynUnion::set_to_default_member operation on a union without ";
         msg += "an explicit default case";
         fail (msg);
      }
      catch (org.omg.DynamicAny.DynAnyPackage.TypeMismatch ex)
      {
         // success
      }

      msg = "The DynUnion::has_no_active_member operation did not return ";
      msg += "TRUE when called on a union without an explicit default case";
      try
      {
         hasNoActiveMember = dynAny.has_no_active_member ();
      }
      catch (Throwable ex)
      {
         fail (msg + ": " + ex);
      }
      assertTrue (msg, hasNoActiveMember);
   }


   /**
    * Test accessing the active member of a DynUnion object.
    */
   public void testAccessNamedUnionMember ()
   {
      String msg;
      int testVal = 10;
      UnionDefaultIntType type;
      TCKind memberKind = null;
      String memberName = null;
      int memberVal = -1;
      org.omg.CORBA.Any any = null;
      org.omg.DynamicAny.DynUnion dynAny = null;
      org.omg.DynamicAny.DynAny member = null; // specific to IDL

      type = new UnionDefaultIntType ();
      type.win (testVal);
      any = orb.create_any ();
      UnionDefaultIntTypeHelper.insert (any, type);
      dynAny = createDynAnyFromAny (any);

      // test getting the kind of the active member
      msg = "Failed to get the correct kind of the active member using ";
      msg += "DynUnion::member_kind operation";
      try
      {
         memberKind = dynAny.member_kind ();
      }
      catch (Throwable ex)
      {
         fail (msg + ": " + ex);
      }

      // specific to IDL
      assertEquals (msg, TCKind._tk_long, memberKind.value ());

      // test getting the name of the active member
      msg = "Failed to get the correct name of the active member using ";
      msg += "DynUnion::member_name operation";
      try
      {
         memberName = dynAny.member_name ();
      }
      catch (Throwable ex)
      {
         fail (msg + ": " + ex);
      }

      assertEquals (msg, "win", memberName); // specific to IDL

      // test getting the value of the active member
      try
      {
         member = dynAny.member ();
      }
      catch (Throwable ex)
      {
         msg = "Failed to get the correct active union member using ";
         msg += "DynUnion::member operation";
         fail (msg + ": " + ex);
      }

      msg = "Failed to get the correct value of active union member using ";
      msg += "DynUnion::member operation";
      memberVal = member.to_any ().extract_long ();

      // specific to IDL
      assertEquals (msg, testVal, memberVal);
   }


   /**
    * Test attempting to access the active member of a DynUnion object when
    * there is no active member.
    */
   public void testAccessUnamedUnionMember ()
   {
      String msg;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynUnion dynAny = null;
      
      tc = UnionNoDefaultIntTypeHelper.type ();
      dynAny = createDynAnyFromTypeCode (tc);

      try
      {
         dynAny.set_to_no_active_member ();
      }
      catch (Throwable ex)
      {
         fail ("Failed to set the union to have no active member: " + ex);
      }

      // test attempting to get the kind with no active member
      try
      {
         dynAny.member_kind ();

         msg = "Failed to raise an InvalidValue exception when calling the ";
         msg += "DynUnion::member_kind operation on a union with no active ";
         msg += "member";
         fail (msg);
      }
      catch (org.omg.DynamicAny.DynAnyPackage.InvalidValue ex)
      {
         // success
      }

      // test attempting to get the name with no active member
      try
      {
         dynAny.member_name ();

         msg = "Failed to raise an InvalidValue exception when calling the ";
         msg += "DynUnion::member_name operation on a union with no active ";
         msg += "member";
         fail (msg);
      }
      catch (org.omg.DynamicAny.DynAnyPackage.InvalidValue ex)
      {
         // success
      }

      // test attempting to get the member with no active member
      try
      {
         dynAny.member ();

         msg = "Failed to raise an InvalidValue exception when calling the ";
         msg += "DynUnion::member operation on a union with no active member";
         fail (msg);
      }
      catch (org.omg.DynamicAny.DynAnyPackage.InvalidValue ex)
      {
         // success
      }      
   }


   /**
    * Test obtaining the TypeCode associated with a DynAny object.
    */
   public void testDynAnyTypeCode ()
   {
      String msg;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynUnion dynAny = null;

      tc = orb.create_union_tc
      (
         ID,
         NAME,
         orb.get_primitive_tc (TCKind.tk_short),
         getUnionMembers ()
      );
      dynAny = createDynAnyFromTypeCode (tc);
         
      msg = "Incorrect TypeCode retrieved from DynAny::type operation";
      assertTrue (msg, dynAny.type ().equal (tc));
   }


   /**
    * Test initializing a DynAny object from another DynAny object.
    */
   public void testInitDynAnyFromDynAny ()
   {
      String msg;
      UnionDefaultIntType type;
      org.omg.CORBA.Any any = null;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynUnion dynAny = null;
      org.omg.DynamicAny.DynUnion dynAny2 = null;      
      
      tc = UnionDefaultIntTypeHelper.type ();
      dynAny = createDynAnyFromTypeCode (tc);      

      type = new UnionDefaultIntType ();
      type.win (10);
      any = orb.create_any ();
      UnionDefaultIntTypeHelper.insert (any, type);
      dynAny2 = createDynAnyFromAny (any);

      msg = "Failed to initialize a DynAny object from another DynAny ";
      msg += "object using the DynAny::assign operation";
      try
      {
         dynAny.assign (dynAny2);
      }
      catch (Throwable ex)
      {
         fail (msg + ": " + ex);
      }
      assertTrue (msg, dynAny.equal (dynAny2));
   }


   /**
    * Test initializing a DynAny object from an Any value.
    */
   public void testInitDynAnyFromAny ()
   {
      String msg;
      UnionDefaultIntType type;
      org.omg.CORBA.Any any = null;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynUnion dynAny = null;
      org.omg.DynamicAny.DynUnion dynAny2 = null;
      
      tc = UnionDefaultIntTypeHelper.type ();
      dynAny = createDynAnyFromTypeCode (tc);

      type = new UnionDefaultIntType ();
      type.win (10);
      any = orb.create_any ();
      UnionDefaultIntTypeHelper.insert (any, type);
      dynAny2 = createDynAnyFromAny (any);

      msg = "Failed to initialize a DynAny object from an Any object ";
      msg += "using the DynAny::from_any operation";
      try
      {
         dynAny.from_any (any);
      }
      catch (Throwable ex)
      {
         fail (msg + ": " + ex);
      }
      assertTrue (msg, dynAny.equal (dynAny2));
   }


   /**
    * Test that a TypeMismatch exception is raised if there is a type
    * mismatch between the DynAny and Any types in an assignment.
    */
   public void testInitFromAnyTypeMismatchEx ()
   {
      String msg;
      org.omg.CORBA.Any any = null;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynUnion dynAny = null;
      
      any = orb.create_any ();
      any.insert_string ("Hello");

      tc = orb.create_union_tc
      (
         ID,
         NAME,
         orb.get_primitive_tc (TCKind.tk_short),
         getUnionMembers ()
      );
      dynAny = createDynAnyFromTypeCode (tc);
            
      msg = "TypeMismatch exception not thrown by DynAny::from_any ";
      msg += "operation when DynAny and Any operands have different types";
      try
      {
         dynAny.from_any (any);

         fail (msg);
      }
      catch (org.omg.DynamicAny.DynAnyPackage.TypeMismatch ex)
      {
         // success
      }
      catch (org.omg.DynamicAny.DynAnyPackage.InvalidValue ex)
      {
         fail (msg + ": " + ex);
      }
   }


   /**
    * Test generating an Any value from a DynAny object.
    */
   public void testGenerateAnyFromDynAny ()
   {
      String msg;
      org.omg.CORBA.Any any = null;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynUnion dynAny = null;
      org.omg.DynamicAny.DynUnion dynAny2 = null;

      tc = UnionDefaultIntTypeHelper.type ();
      dynAny = createDynAnyFromTypeCode (tc);

      any = orb.create_any ();
      any = dynAny.to_any ();
      dynAny2 = createDynAnyFromAny (any);

      msg = "The DynAny::to_any operation failed to create an Any ";
      msg += "object with the same value as the DynAny object";
      assertTrue (msg, dynAny.equal (dynAny2));
   }


   /**
    * Test destroying a DynAny object.
    */
   public void testDestroyDynAny ()
   {
      String msg;
      UnionDefaultIntType type;
      org.omg.CORBA.Any any = null;
      org.omg.DynamicAny.DynUnion dynAny = null;
      
      type = new UnionDefaultIntType ();
      type.win (10);
      any = orb.create_any ();
      UnionDefaultIntTypeHelper.insert (any, type);
      dynAny = createDynAnyFromAny (any);
      dynAny.destroy ();

      try
      {
         dynAny.type ();

         msg = "Failed to destroy DynAny using DynAny::destroy operation - ";
         msg += "calling DynAny::type operation on a destroyed DynAny object ";
         msg += "did not raise OBJECT_NOT_EXIST exception";
         fail (msg);
      }
      catch (org.omg.CORBA.OBJECT_NOT_EXIST ex)
      {
         // success
      }

      msg = "Failed to destroy DynAny using DynAny::destroy operation - ";
      msg += "calling DynAny::current_component operation on a destroyed ";
      msg += "DynAny object did not raise OBJECT_NOT_EXIST exception";
      try
      {
         dynAny.current_component ();

         fail (msg);
      }
      catch (org.omg.CORBA.OBJECT_NOT_EXIST ex)
      {
         // success
      }      
      catch (org.omg.DynamicAny.DynAnyPackage.TypeMismatch ex)
      {
         fail (msg + ": " + ex);
      }
   }


   /**
    * Test destroying a component of a DynAny object.
    */
   public void testDestroyComponent ()
   {
      String msg;
      UnionDefaultIntType type;
      org.omg.CORBA.Any any = null;
      org.omg.DynamicAny.DynUnion dynAny = null;
      org.omg.DynamicAny.DynAny comp = null;
      
      type = new UnionDefaultIntType ();
      type.win (10);
      any = orb.create_any ();
      UnionDefaultIntTypeHelper.insert (any, type);
      dynAny = createDynAnyFromAny (any);

      try
      {
         comp = dynAny.current_component ();
      }
      catch (Throwable ex)
      {
         msg = "Failed to get the current component of the DynAny using the ";
         msg += "DynAny::current_component operation before calling the ";
         msg += "DynAny::destroy operation";
         fail (msg + ": " + ex);
      }

      comp.destroy ();
      try
      {
         comp = dynAny.current_component ();
      }
      catch (Throwable ex)
      {
         msg = "Failed to get the current component of the DynAny using the ";
         msg += "DynAny::current_component operation after calling the ";
         msg += "DynAny::destroy operation";
         fail (msg + ": " + ex);
      }      

      try
      {
         comp.type ();
      }
      catch (org.omg.CORBA.OBJECT_NOT_EXIST ex)
      {
         msg = "Calling destroy on a component resulted in destroying the ";
         msg += "component object";
         fail (msg + ": " + ex);
      }
   }


   /**
    * Test creating a copy of a DynAny object.
    */
   public void testCopyDynAny ()
   {
      String msg;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynUnion dynAny = null;
      org.omg.DynamicAny.DynUnion dynAny2 = null;
      
      tc = UnionDefaultIntTypeHelper.type ();
      dynAny = createDynAnyFromTypeCode (tc);      
      dynAny2 = (org.omg.DynamicAny.DynUnion) dynAny.copy ();

      msg = "The DynAny object created with the DynAny::copy operation ";
      msg += "is not equal to the DynAny object it was copied from";
      assertTrue (msg, dynAny.equal (dynAny2));
   }


   private static class Setup extends TestSetup
   {
      public Setup (Test test)
      {
         super (test);
      }

      protected void setUp ()
      {
         org.omg.CORBA.Object obj = null;

         orb = ORBSetup.getORB ();
         try
         {
            obj = orb.resolve_initial_references ("DynAnyFactory");
         }
         catch (org.omg.CORBA.ORBPackage.InvalidName ex)
         {
            fail ("Failed to resolve DynAnyFactory: " + ex);
         }
         try
         {
            factory = org.omg.DynamicAny.DynAnyFactoryHelper.narrow (obj);
         }
         catch (Throwable ex)
         {
            fail ("Failed to narrow to DynAnyFactory: " + ex);
         }
      }

      protected void tearDown ()
      {
      }
   }


   /**
    * Create a DynAny object from an Any object.
    */   
   private static org.omg.DynamicAny.DynUnion createDynAnyFromAny
      (org.omg.CORBA.Any any)
   {      
      String msg;
      org.omg.DynamicAny.DynUnion dynAny = null;

      try
      {
         dynAny = (org.omg.DynamicAny.DynUnion) factory.create_dyn_any (any);
      }
      catch (Throwable ex)
      {
         msg = "Factory failed to create DynAny from Any using ";
         msg += "DynAny::create_dyn_any operation: " + ex;
         fail (msg);
      }
      return dynAny;
   }


   /**
    * Create a DynAny object from a TypeCode object.
    */   
   private static org.omg.DynamicAny.DynUnion createDynAnyFromTypeCode
      (org.omg.CORBA.TypeCode tc)
   {
      String msg;      
      org.omg.DynamicAny.DynUnion dynAny = null;

      try
      {
         dynAny = (org.omg.DynamicAny.DynUnion)
            factory.create_dyn_any_from_type_code (tc);
      }
      catch (Throwable ex)
      {
         msg = "Factory failed to create DynAny from TypeCode using ";
         msg += "DynAny::create_dyn_any_from_type_code operation: " + ex;
         fail (msg);
      }
      return dynAny;
   }


   /**
    * Create a sequence of fields for use in creating a union TypeCode.
    */
   private static org.omg.CORBA.UnionMember [] getUnionMembers ()
   {
      final org.omg.CORBA.UnionMember [] members =
         new org.omg.CORBA.UnionMember [2];
      org.omg.CORBA.Any any = null;

      any = orb.create_any ();
      any.insert_short ((short) 0);
      members [0] = new org.omg.CORBA.UnionMember
      (
         "zero",
         any,
         orb.get_primitive_tc (TCKind.tk_string),
         null
      );

      any = orb.create_any ();
      any.insert_short ((short) 1);
      members [1] = new org.omg.CORBA.UnionMember
      (
         "one",
         any,
         orb.get_primitive_tc (TCKind.tk_char),
         null
      );

      return members;
   }
   
}

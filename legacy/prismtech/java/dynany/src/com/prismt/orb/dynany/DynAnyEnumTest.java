package com.prismt.orb.dynany;

/**
 * DynAnyEnumTest.java
 *
 * DynAny tests for enumeration types.
 *
 */

import junit.framework.*;
import junit.extensions.TestSetup;
import com.prismt.common.ORBSetup;
import com.prismt.orb.Tests.EnumType;
import com.prismt.orb.Tests.EnumTypeHelper;

public class DynAnyEnumTest extends TestCase
{
   private static org.omg.DynamicAny.DynAnyFactory factory = null;
   private static org.omg.CORBA.ORB orb = null;

   private static final String ID = "IDL:test:1.0";
   private static final String NAME = "MyEnum";
   private static final String [] ENUM = {"one", "two", "three"};


   public DynAnyEnumTest (String name)
   {
      super (name);
   }


   public static Test suite ()
   {
      TestSuite suite = new TestSuite ("DynEnum Tests");
      Setup setup = new Setup (suite);
      ORBSetup osetup = new ORBSetup (setup);

      suite.addTest (new DynAnyEnumTest ("testFactoryCreateFromAny"));
      suite.addTest (new DynAnyEnumTest ("testFactoryCreateFromTypeCode"));
      suite.addTest (new DynAnyEnumTest ("testFactoryCreateFromIDLTypeCode"));
      suite.addTest (new DynAnyEnumTest ("testCompareDynAny"));
      suite.addTest (new DynAnyEnumTest ("testAccessEnumValue"));
      suite.addTest (new DynAnyEnumTest ("testAccessInvalidValueEx"));
      suite.addTest (new DynAnyEnumTest ("testDynAnyTypeCode"));
      suite.addTest (new DynAnyEnumTest ("testInitDynAnyFromDynAny"));
      suite.addTest (new DynAnyEnumTest ("testInitDynAnyFromAny"));
      suite.addTest (new DynAnyEnumTest ("testInitFromAnyTypeMismatchEx"));
      suite.addTest (new DynAnyEnumTest ("testGenerateAnyFromDynAny"));
      suite.addTest (new DynAnyEnumTest ("testDestroyDynAny"));
      suite.addTest (new DynAnyEnumTest ("testCopyDynAny"));
      suite.addTest (new DynAnyEnumTest ("testIterateDynAny"));

      return osetup;
   }


   /**
    * Tests creating a DynAny object from an Any object using the
    * DynAnyFactory object.
    */
   public void testFactoryCreateFromAny ()
   {
      org.omg.CORBA.Any any = null;

      any = orb.create_any ();
      EnumTypeHelper.insert (any, EnumType.from_int (0));
      
      createDynAnyFromAny (any);
   }


   /**
    * Tests creating a DynAny object from a TypeCode object using the
    * DynAnyFactory object.
    */
   public void testFactoryCreateFromTypeCode ()
   {
      org.omg.CORBA.TypeCode tc = null;

      tc = orb.create_enum_tc (ID, NAME, ENUM);
      createDynAnyFromTypeCode (tc);
   }


   /**
    * Tests creating a DynAny object from a TypeCode object generated from
    * IDL using the DynAnyFactory object.
    */
   public void testFactoryCreateFromIDLTypeCode ()
   {
      org.omg.CORBA.TypeCode tc = null;

      tc = EnumTypeHelper.type ();
      createDynAnyFromTypeCode (tc);
   }


   /**
    * Test comparing DynAny values.
    */
   public void testCompareDynAny ()
   {
      String msg;
      org.omg.CORBA.Any any = null;
      org.omg.DynamicAny.DynEnum dynAny = null;
      org.omg.DynamicAny.DynEnum dynAny2 = null;
      
      any = orb.create_any ();
      EnumTypeHelper.insert (any, EnumType.from_int (0));
      dynAny = createDynAnyFromAny (any);
      dynAny2 = createDynAnyFromAny (any);
      
      msg = "Comparing two equal DynAny values using DynAny::equal failed";
      assertTrue (msg, dynAny.equal (dynAny2));
   }


   /**
    * Test accessing a value in a DynEnum object.
    */
   public void testAccessEnumValue ()
   {
      String msg;
      String type1;
      int type2;
      org.omg.CORBA.Any any = null;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynEnum dynAny = null;
      org.omg.DynamicAny.DynEnum dynAny2 = null;

      tc = EnumTypeHelper.type ();
      dynAny = createDynAnyFromTypeCode (tc);

      any = orb.create_any ();
      EnumTypeHelper.insert (any, EnumType.from_int (0));
      dynAny2 = createDynAnyFromAny (any);

      type1 = "first"; // specific to enum
      try
      {
         dynAny.set_as_string (type1);
      }
      catch (Throwable ex)
      {
         msg = "Failed to set value as string in DynEnum object with ";
         msg += "DynEnum::set_as_string";
         fail (msg + ": " + ex);
      }

      type2 = 1;
      try
      {
         dynAny2.set_as_ulong (type2);
      }
      catch (Throwable ex)
      {
         msg = "Failed to set value as int in DynEnum object with ";
         msg += "DynEnum::set_as_ulong";
         fail (msg + ": " + ex);
      }

      msg = "String value inserted into DynEnum object is not equal to value ";
      msg += "extracted from same DynEnum object with DynEnum::get_as_string";
      assertEquals (msg, type1, dynAny.get_as_string ());

      msg = "Integer value inserted into DynEnum object is not equal to value ";
      msg += "extracted from same DynEnum object with DynEnum::get_as_ulong";
      assertEquals (msg, type2, dynAny2.get_as_ulong ());
   }


   /**
    * Test that an InvalidValue exception is raised if values inserted into
    * DynEnum objects are out of range.
    */
   public void testAccessInvalidValueEx ()
   {
      String msg;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynEnum dynAny = null;

      tc = orb.create_enum_tc (ID, NAME, ENUM);
      dynAny = createDynAnyFromTypeCode (tc);

      try
      {
         dynAny.set_as_string ("BadValue");

         msg = "InvalidValue exception not thrown by DynEnum::set_as_string ";
         msg += "operation when value is out of range";
         fail (msg);
      }
      catch (org.omg.DynamicAny.DynAnyPackage.InvalidValue ex)
      {
         // success
      }

      try
      {
         dynAny.set_as_ulong (-1);

         msg = "InvalidValue exception not thrown by DynEnum::set_as_ulong ";
         msg += "operation when value is out of range";
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
      org.omg.DynamicAny.DynEnum dynAny = null;

      tc = orb.create_enum_tc (ID, NAME, ENUM);
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
      org.omg.CORBA.Any any = null;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynEnum dynAny = null;
      org.omg.DynamicAny.DynEnum dynAny2 = null;      
      
      tc = EnumTypeHelper.type ();
      dynAny = createDynAnyFromTypeCode (tc);

      any = orb.create_any ();
      EnumTypeHelper.insert (any, EnumType.from_int (1));
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
      org.omg.CORBA.Any any = null;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynEnum dynAny = null;
      org.omg.DynamicAny.DynEnum dynAny2 = null;
      
      tc = EnumTypeHelper.type ();
      dynAny = createDynAnyFromTypeCode (tc);

      any = orb.create_any ();
      EnumTypeHelper.insert (any, EnumType.from_int (1));
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
      org.omg.DynamicAny.DynEnum dynAny = null;
      
      any = orb.create_any ();
      any.insert_string ("Hello");

      tc = orb.create_enum_tc (ID, NAME, ENUM);
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
      org.omg.DynamicAny.DynEnum dynAny = null;
      org.omg.DynamicAny.DynEnum dynAny2 = null;

      tc = EnumTypeHelper.type ();
      dynAny = createDynAnyFromTypeCode (tc);

      any = orb.create_any ();
      any = dynAny.to_any ();
      dynAny2 = createDynAnyFromAny (any);

      msg = "Failed to initialize an Any object from a DynAny object ";
      msg += "using the DynAny::to_any operation";
      assertTrue (msg, dynAny.equal (dynAny2));
   }


   /**
    * Test destroying a DynAny object.
    */
   public void testDestroyDynAny ()
   {
      String msg;
      org.omg.CORBA.Any any = null;
      org.omg.DynamicAny.DynEnum dynAny = null;
      
      any = orb.create_any ();
      EnumTypeHelper.insert (any, EnumType.from_int (1));
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
    * Test creating a copy of a DynAny object.
    */
   public void testCopyDynAny ()
   {
      String msg;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynEnum dynAny = null;
      org.omg.DynamicAny.DynEnum dynAny2 = null;
      
      tc = EnumTypeHelper.type ();
      dynAny = createDynAnyFromTypeCode (tc);      
      dynAny2 = (org.omg.DynamicAny.DynEnum) dynAny.copy ();

      msg = "The DynAny object created with the DynAny::copy operation ";
      msg += "is not equal to the DynAny object it was copied from";
      assertTrue (msg, dynAny.equal (dynAny2));
   }


   /**
    * Test iterating through components of a DynAny.
    */
   public void testIterateDynAny ()
   {
      String msg;
      int compCount = -1;
      boolean seek;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynEnum dynAny = null;
      
      tc = EnumTypeHelper.type ();
      dynAny = createDynAnyFromTypeCode (tc);

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
      assertEquals (msg, 0, compCount);
      
      // test if there is a first component
      msg = "The DynAny::seek operation indicates that a valid component ";
      msg += "exists but the DynAny should have no components";
      seek = dynAny.seek (0);
      assertTrue (msg, !seek);
      
      // test getting the current component
      try
      {
         dynAny = (org.omg.DynamicAny.DynEnum) dynAny.current_component ();

         msg = "A TypeMismatch exception was not raised by the ";         
         msg += "DynAny::current_component operation when trying to access ";
         msg += "the current component of a DynAny with no components";
         fail (msg);
      }
      catch (org.omg.DynamicAny.DynAnyPackage.TypeMismatch ex)
      {
         // success
      }
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
   private static org.omg.DynamicAny.DynEnum createDynAnyFromAny
      (org.omg.CORBA.Any any)
   {      
      String msg;
      org.omg.DynamicAny.DynEnum dynAny = null;

      try
      {
         dynAny = (org.omg.DynamicAny.DynEnum) factory.create_dyn_any (any);
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
   private static org.omg.DynamicAny.DynEnum createDynAnyFromTypeCode
      (org.omg.CORBA.TypeCode tc)
   {
      String msg;      
      org.omg.DynamicAny.DynEnum dynAny = null;

      try
      {
         dynAny = (org.omg.DynamicAny.DynEnum)
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

}

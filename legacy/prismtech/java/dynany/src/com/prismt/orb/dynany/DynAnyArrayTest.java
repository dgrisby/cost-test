package com.prismt.orb.dynany;

/**
 * DynAnyArrayTest.java
 *
 * DynAny tests for array types.
 *
 */

import junit.framework.*;
import junit.extensions.TestSetup;
import org.omg.CORBA.TCKind;

import com.prismt.common.ORBSetup;
import com.prismt.orb.Tests.Bound;
import com.prismt.orb.Tests.ArrayTypeHelper;

public class DynAnyArrayTest extends TestCase
{
   private static org.omg.DynamicAny.DynAnyFactory factory = null;
   private static org.omg.CORBA.ORB orb = null;

   public DynAnyArrayTest (String name)
   {
      super (name);
   }


   public static Test suite ()
   {
      TestSuite suite = new TestSuite ("DynArray Tests");
      Setup setup = new Setup (suite);
      ORBSetup osetup = new ORBSetup (setup);

      suite.addTest (new DynAnyArrayTest ("testFactoryCreateFromAny"));
      suite.addTest (new DynAnyArrayTest ("testFactoryCreateFromTypeCode"));
      suite.addTest (new DynAnyArrayTest ("testFactoryCreateFromIDLTypeCode"));
      suite.addTest (new DynAnyArrayTest ("testCompareDynAny"));
      suite.addTest (new DynAnyArrayTest ("testIterateDynAny"));
      suite.addTest (new DynAnyArrayTest ("testAccessArrayElements"));
      suite.addTest (new DynAnyArrayTest ("testAccessArrayDynAnyElements"));
      suite.addTest (new DynAnyArrayTest ("testAccessArrayElementsEx"));
      suite.addTest (new DynAnyArrayTest ("testDynAnyTypeCode"));
      suite.addTest (new DynAnyArrayTest ("testInitDynAnyFromDynAny"));
      suite.addTest (new DynAnyArrayTest ("testInitDynAnyFromAny"));
      suite.addTest (new DynAnyArrayTest ("testInitFromAnyTypeMismatchEx"));
      suite.addTest (new DynAnyArrayTest ("testGenerateAnyFromDynAny"));
      suite.addTest (new DynAnyArrayTest ("testDestroyDynAny"));
      suite.addTest (new DynAnyArrayTest ("testDestroyComponent"));
      suite.addTest (new DynAnyArrayTest ("testCopyDynAny"));

      return osetup;
   }


   /**
    * Tests creating a DynAny object from an Any object using the
    * DynAnyFactory object.
    */
   public void testFactoryCreateFromAny ()
   {
      int [] type = null;
      org.omg.CORBA.Any any = null;

      type = getIntArray ();
      any = orb.create_any ();
      ArrayTypeHelper.insert (any, type);
      
      createDynAnyFromAny (any);
   }


   /**
    * Tests creating a DynAny object from a TypeCode object using the
    * DynAnyFactory object.
    */
   public void testFactoryCreateFromTypeCode ()
   {
      org.omg.CORBA.TypeCode tc = null;

      tc = orb.get_primitive_tc (TCKind.tk_long);
      tc = orb.create_array_tc (Bound.value, tc);
      createDynAnyFromTypeCode (tc);
   }


   /**
    * Tests creating a DynAny object from a TypeCode object generated from
    * IDL using the DynAnyFactory object.
    */
   public void testFactoryCreateFromIDLTypeCode ()
   {
      org.omg.CORBA.TypeCode tc = null;

      tc = ArrayTypeHelper.type ();
      createDynAnyFromTypeCode (tc);
   }


   /**
    * Test comparing DynAny values.
    */
   public void testCompareDynAny ()
   {
      String msg;
      int [] type;
      org.omg.CORBA.Any any = null;
      org.omg.DynamicAny.DynArray dynAny = null;
      org.omg.DynamicAny.DynArray dynAny2 = null;
      
      type = getIntArray ();
      any = orb.create_any ();
      ArrayTypeHelper.insert (any, type);
      dynAny = createDynAnyFromAny (any);
      dynAny2 = createDynAnyFromAny (any);
      
      msg = "Comparing two equal DynAny values using DynAny::equal failed";
      assertTrue (msg, dynAny.equal (dynAny2));
   }
   

   /**
    * Test iterating through components of a DynAny.
    */
   public void testIterateDynAny ()
   {
      String msg;
      int [] type;
      int compCount = -1;
      boolean seek;
      org.omg.CORBA.Any any = null;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynArray dynAny = null;
      org.omg.DynamicAny.DynAny compSeek = null;
      org.omg.DynamicAny.DynAny compRewind = null;

      type = getIntArray ();
      any = orb.create_any ();
      ArrayTypeHelper.insert (any, type);
      dynAny = createDynAnyFromAny (any);

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
      assertEquals (msg, Bound.value, compCount);

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
         fail (msg + ": " + ex);
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
         fail (msg + ": " + ex);
      }
      msg = "The component at DynAny::seek(0) is not equal to the ";
      msg += "component at DynAny::rewind";
      assertTrue (msg, compSeek.equal (compRewind));
   }


   /**
    * Test accessing the elements in a DynArray object.
    */
   public void testAccessArrayElements ()
   {
      String msg;
      int len;
      int curVal;
      boolean next;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynArray dynAny = null;
      org.omg.CORBA.Any [] anys = null;

      tc = ArrayTypeHelper.type ();
      dynAny = createDynAnyFromTypeCode (tc);

      // test setting the elements
      len = Bound.value;
      anys = new org.omg.CORBA.Any [len];
      for (int i = 0; i < len; i++)
      {
         anys [i] = orb.create_any ();
         anys [i].insert_long (i);
      }

      try
      {
         dynAny.set_elements (anys);
      }
      catch (Throwable ex)
      {
         msg = "Failed to set the elements using the DynArray::set_elements ";
         msg += "operation";
         fail (msg + ": " + ex);
      }

      // test getting the elements
      anys = dynAny.get_elements ();
      for (int i = 0; i < anys.length; i++)
      {
         msg = "Failed to get the correct value of the DynArray at ";
         msg += "position " + i + " when array element is returned using ";
         msg += "DynArray::get_elements operation";
         curVal = anys [i].extract_long ();
         assertEquals (msg, i, curVal);
      }
   }


   /**
    * Test accessing the elements in a DynArray object as DynAnys.
    */
   public void testAccessArrayDynAnyElements ()
   {
      String msg;
      int len;
      int curVal;
      boolean next;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynArray dynAny = null;
      org.omg.DynamicAny.DynAny [] dynAnys = null;

      tc = ArrayTypeHelper.type ();
      dynAny = createDynAnyFromTypeCode (tc);
      
      // test setting the elements
      len = Bound.value;
      dynAnys = new org.omg.DynamicAny.DynAny [len];
      tc = orb.get_primitive_tc (TCKind.tk_long);
      for (int i = 0; i < len; i++)
      {
         try
         {
            dynAnys [i] = factory.create_dyn_any_from_type_code (tc);
         }
         catch (Throwable ex)
         {
            fail ("Failed to create a DynAny at position " + i + ": " + ex);
         }         

         try
         {
            dynAnys [i].insert_long (i);
         }
         catch (Throwable ex)
         {
            msg = "Failed to insert a value into a DynAny at position " + i;
            msg += ": " + ex;
            fail (msg);
         }         
      }

      try
      {
         dynAny.set_elements_as_dyn_any (dynAnys);
      }
      catch (Throwable ex)
      {
         msg = "Failed to set the elements using the ";
         msg += "DynArray::set_elements_as_dyn_any operation";
         fail (msg + ": " + ex);
      }

      // test getting the elements
      dynAnys = dynAny.get_elements_as_dyn_any ();
      for (int i = 0; i < dynAnys.length; i++)
      {
         msg = "Failed to get the correct value of the DynArray at ";
         msg += "position " + i + " when array element is returned using ";
         msg += "DynArray::get_elements_as_dyn_any operation";
         curVal = -1;
         try
         {
            curVal = dynAnys [i].get_long ();
         }
         catch (Throwable ex)
         {
            fail (msg + ": " + ex);
         }
         assertEquals (msg, i, curVal);
      }
   }


   /**
    * Test that the correct exceptions are raised when accessing the elements
    * in a DynArray object incorrectly.
    */
   public void testAccessArrayElementsEx ()
   {
      String msg;
      int len;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynArray dynAny = null;
      org.omg.CORBA.Any [] anys = null;

      tc = ArrayTypeHelper.type ();
      dynAny = createDynAnyFromTypeCode (tc);

      // test setting the elements with components of an invalid type
      len = Bound.value;
      anys = new org.omg.CORBA.Any [len];
      for (int i = 0; i < len; i++)
      {
         anys [i] = orb.create_any ();
         anys [i].insert_string ("BadType");
      }
      
      msg = "Failed to raise a TypeMismatch exception when setting a ";
      msg += "DynArray object with components of the wrong type using ";
      msg += "DynArray::set_elements";
      try
      {
         dynAny.set_elements (anys);

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

      // test setting the elements with an incorrect number of elements
      len = Bound.value + 1;
      anys = new org.omg.CORBA.Any [len];
      for (int i = 0; i < len; i++)
      {
         anys [i] = orb.create_any ();
         anys [i].insert_long (i);
      }

      msg = "Failed to raise an InvalidValue exception when setting a ";
      msg += "DynArray object with an incorrect number of elements using ";
      msg += "DynArray::set_elements";
      try
      {
         dynAny.set_elements (anys);
      }
      catch (org.omg.DynamicAny.DynAnyPackage.InvalidValue ex)
      {
         // success
      }
      catch (org.omg.DynamicAny.DynAnyPackage.TypeMismatch ex)
      {
         fail (msg + ": " + ex);
      }      
   }


   /**
    * Test obtaining the TypeCode associated with a DynAny object.
    */
   public void testDynAnyTypeCode ()
   {
      String msg;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynArray dynAny = null;

      tc = orb.get_primitive_tc (TCKind.tk_long);
      tc = orb.create_array_tc (Bound.value, tc);
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
      int [] type;
      org.omg.CORBA.Any any = null;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynArray dynAny = null;
      org.omg.DynamicAny.DynArray dynAny2 = null;      
      
      tc = ArrayTypeHelper.type ();
      dynAny = createDynAnyFromTypeCode (tc);

      type = getIntArray ();
      any = orb.create_any ();
      ArrayTypeHelper.insert (any, type);
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
      int [] type;
      org.omg.CORBA.Any any = null;
      org.omg.CORBA.TypeCode tc = null;
      org.omg.DynamicAny.DynArray dynAny = null;
      org.omg.DynamicAny.DynArray dynAny2 = null;
      
      tc = ArrayTypeHelper.type ();
      dynAny = createDynAnyFromTypeCode (tc);

      type = getIntArray ();
      any = orb.create_any ();
      ArrayTypeHelper.insert (any, type);
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
      org.omg.DynamicAny.DynArray dynAny = null;
      
      any = orb.create_any ();
      any.insert_string ("Hello");

      tc = orb.get_primitive_tc (TCKind.tk_long);
      tc = orb.create_array_tc (Bound.value, tc);
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
      org.omg.DynamicAny.DynArray dynAny = null;
      org.omg.DynamicAny.DynArray dynAny2 = null;

      tc = ArrayTypeHelper.type ();
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
      int [] type;
      org.omg.CORBA.Any any = null;
      org.omg.DynamicAny.DynArray dynAny = null;
      
      type = getIntArray ();
      any = orb.create_any ();
      ArrayTypeHelper.insert (any, type);
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
      int [] type;
      org.omg.CORBA.Any any = null;
      org.omg.DynamicAny.DynArray dynAny = null;
      org.omg.DynamicAny.DynAny comp = null;
      
      type = getIntArray ();
      any = orb.create_any ();
      ArrayTypeHelper.insert (any, type);
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
      org.omg.DynamicAny.DynArray dynAny = null;
      org.omg.DynamicAny.DynArray dynAny2 = null;
      
      tc = ArrayTypeHelper.type ();
      dynAny = createDynAnyFromTypeCode (tc);      
      dynAny2 = (org.omg.DynamicAny.DynArray) dynAny.copy ();

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
   private static org.omg.DynamicAny.DynArray createDynAnyFromAny
      (org.omg.CORBA.Any any)
   {      
      String msg;
      org.omg.DynamicAny.DynArray dynAny = null;

      try
      {
         dynAny = (org.omg.DynamicAny.DynArray) factory.create_dyn_any (any);
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
   private static org.omg.DynamicAny.DynArray createDynAnyFromTypeCode
      (org.omg.CORBA.TypeCode tc)
   {
      String msg;      
      org.omg.DynamicAny.DynArray dynAny = null;

      try
      {
         dynAny = (org.omg.DynamicAny.DynArray)
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
    * Create an array of integers of fixed length.
    */
   private static int [] getIntArray ()
   {
      int [] type = new int [Bound.value];
      for (int i = 0; i < Bound.value; i++)
      {
         type [i] = i;
      }
      return type;
   }
   
}

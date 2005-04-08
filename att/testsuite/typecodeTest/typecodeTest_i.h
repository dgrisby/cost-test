// -*- Mode: C++; -*-
//
//    Copyright (C) 2001 AT&T Laboratories Cambridge Ltd.
//
//    This file is part of the OMNI Testsuite.
//
//    The testsuite is free software; you can redistribute it and/or
//    modify it under the terms of the GNU Library General Public
//    License as published by the Free Software Foundation; either
//    version 2 of the License, or (at your option) any later version.
//
//    This library is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//    Library General Public License for more details.
//
//    You should have received a copy of the GNU Library General Public
//    License along with this library; if not, write to the Free
//    Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
//    02111-1307, USA
//
//
// typecodeTest_i.h
//

#ifndef _typecodeTest_i_h_
#define _typecodeTest_i_h_

#include "typecodeTest.hh"

class typecodeTest_i : public POA_typecodeTest,
		       public PortableServer::RefCountServantBase
{
 public:
  
  typecodeTest_i() { }
  virtual ~typecodeTest_i() { }

  virtual CORBA::TypeCode_ptr testOp(CORBA::TypeCode_ptr aTC, 
				     CORBA::TypeCode_ptr& bTC,
				     CORBA::TypeCode_out cTC );
};


#endif
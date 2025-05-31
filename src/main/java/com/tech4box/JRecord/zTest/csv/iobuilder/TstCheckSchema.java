/*  -------------------------------------------------------------------------
 *
 *                Project: JRecord
 *    
 *    Sub-Project purpose: Provide support for reading Cobol-Data files 
 *                        using a Cobol Copybook in Java.
 *                         Support for reading Fixed Width / Binary / Csv files
 *                        using a Xml schema.
 *                         General Fixed Width / Csv file processing in Java.
 *    
 *                 Author: Bruce Martin
 *    
 *                License: LGPL 2.1 or latter
 *                
 *    Copyright (c) 2016, Bruce Martin, All Rights Reserved.
 *   
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *   
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 * ------------------------------------------------------------------------ */

package com.tech4box.JRecord.zTest.csv.iobuilder;

import com.tech4box.JRecord.JRecordInterface1;
import com.tech4box.JRecord.External.ExternalRecord;
import com.tech4box.JRecord.External.Def.ExternalField;
import com.tech4box.JRecord.Types.Type;
import junit.framework.TestCase;

public class TstCheckSchema extends TestCase {
	public void testSchema01() throws Exception {
		ExternalRecord xr;
		
		xr = JRecordInterface1.CSV .newIOBuilder().getExternalRecord();
		assertEquals("\"", xr.getQuote());
		assertEquals(",",  xr.getDelimiter());
		
		xr = JRecordInterface1.CSV .newIOBuilder(";", "`").getExternalRecord();
		assertEquals("`",  xr.getQuote());
		assertEquals(";",  xr.getDelimiter());
	}
	
	public void testSchema02() throws Exception {
		ExternalRecord xr;
		
		xr = JRecordInterface1.CSV 
				.newIOBuilder("|", "'")
					.defineFields()
						.addCsvField("f1", Type.ftChar, 0)
						.addCsvField("f2", Type.ftNumAnyDecimal, 0)
						.addCsvField("f3", Type.ftNumLeftJustified, 2)
						.addCsvField("f4", Type.ftNumRightJustifiedPN, 3)
					.endOfRecord()
				.getExternalRecord();
		assertEquals("'", xr.getQuote());
		assertEquals("|", xr.getDelimiter());
	
		assertEquals(4, xr.getNumberOfRecordFields());
		check(xr.getRecordField(0), 1, "f1", Type.ftChar, 0);                    
		check(xr.getRecordField(1), 2, "f2", Type.ftNumAnyDecimal, 0);
		check(xr.getRecordField(2), 3, "f3", Type.ftNumLeftJustified, 2);
		check(xr.getRecordField(3), 4, "f4", Type.ftNumRightJustifiedPN, 3);
	}
	
	private void check(ExternalField fld, int pos, String n, int type, int decimal) {
		assertEquals(n, fld.getName());
		assertEquals(pos, fld.getPos());
		assertEquals(type, fld.getType());
		assertEquals(decimal, fld.getDecimal());
		assertTrue(fld.getLen() < 0);
	}
}

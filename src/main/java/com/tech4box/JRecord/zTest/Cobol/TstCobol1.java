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

package com.tech4box.JRecord.zTest.Cobol;

import java.io.ByteArrayInputStream;

import com.tech4box.JRecord.Common.FieldDetail;
import com.tech4box.JRecord.Common.RecordException;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.External.CobolCopybookLoader;
import com.tech4box.JRecord.External.CopybookLoader;
import com.tech4box.JRecord.Log.TextLog;
import com.tech4box.JRecord.Numeric.ICopybookDialects;
import com.tech4box.JRecord.Types.Type;
import com.tech4box.JRecord.Types.TypeManager;
import com.tech4box.JRecord.Types.TypeNum;
import junit.framework.TestCase;

/**
 * This class checks the type and positive attribute for
 * various Cobol Numeric Types
 *
 * @author Bruce Martin
 *
 */
public class TstCobol1 extends TestCase {

	private static final String POSITIVE_NUMERIC_COPYBOOK
					=  "           01  ZZ-TTT4.\n"
					 + "             03 f01         pic 999V99.\n"
					 + "             03 f02         pic 999V99 comp-3.\n"
					 + "             03 f03         pic 999V99 comp.\n"
					 + "             03 f04         pic 999V99 comp-5.\n"
					 + "\n"
					 + "             03 f05         pic 9999.99.\n";

	private static final String SIGNED_NUMERIC_COPYBOOK
					=  "           01  ZZ-TTT4.\n"
					 + "             03 f01         pic S999V99.\n"
					 + "             03 f02         pic S999V99 comp-3.\n"
					 + "             03 f03         pic S999V99 comp.\n"
					 + "             03 f04         pic S999V99 comp-5.\n"
					 + "\n"
					 + "             03 f05         pic -9999.99.\n"
	                 + "             03 f06         pic +9999.99.\n"
					 + "\n"
					 + "             03 f07         pic ----9.99.\n"
	                 + "             03 f08         pic ++++9.99.\n"
					 + "\n"
					 + "             03 f07         pic -----.99.\n"
	                 + "             03 f08         pic +++++.99.\n"

	                 ;

	public void testPositiveConversion() throws RecordException {
		int[] intelTypes = {22, 33, 23, 23, 25, };
		int[] mainframeTypes = {22, 33, 39, 39, 25, };

		System.out.println();
		System.out.println("Intel: ");
		testPositiveConversion(ICopybookDialects.FMT_INTEL, intelTypes);

		System.out.println();
		System.out.println("Mainframe: ");
		testPositiveConversion(ICopybookDialects.FMT_MAINFRAME, mainframeTypes);
	}

	public void testPositiveConversion(int cobolDialect, int[] types) throws RecordException {
		LayoutDetail l = getCobolLayout(cobolDialect, POSITIVE_NUMERIC_COPYBOOK);
		TypeManager m = TypeManager.getInstance();

		System.out.println();
		System.out.print("{");
		for (int i = 0; i < l.getRecord(0).getFieldCount(); i++) {
			FieldDetail field = l.getRecord(0).getField(i);
			//Type t = m.getType(field.getType());

			System.out.print(field.getType() + ", ");
		}
		System.out.println("};");
		System.out.println();


		for (int i = 0; i < l.getRecord(0).getFieldCount(); i++) {
			FieldDetail field = l.getRecord(0).getField(i);
			Type t = m.getType(field.getType());

			assertTrue("Field: " + field.getName(), t instanceof TypeNum && ((TypeNum) t).isPositive());
			assertEquals("Index: " + i, types[i], field.getType());
		}

	}


	public void testSignedConversion() throws RecordException {
		int[] intelTypes = {46, 31, 15, 15, 7, 24, 6, 29, 6, 29, };
		int[] mainframeTypes = {32, 31, 35, 35, 7, 24, 6, 29, 6, 29, };

		System.out.println();
		System.out.println("Signed Intel: ");
		testSignedConversion(ICopybookDialects.FMT_INTEL, intelTypes);

		System.out.println();
		System.out.println("Signed Mainframe: ");
		testSignedConversion(ICopybookDialects.FMT_MAINFRAME, mainframeTypes);
	}

	public void testSignedConversion(int cobolDialect, int[] types) throws RecordException {
		LayoutDetail l = getCobolLayout(cobolDialect, SIGNED_NUMERIC_COPYBOOK);
		TypeManager m = TypeManager.getInstance();

		System.out.println();
		System.out.print("{");
		for (int i = 0; i < l.getRecord(0).getFieldCount(); i++) {
			FieldDetail field = l.getRecord(0).getField(i);
			//Type t = m.getType(field.getType());

			System.out.print(field.getType() + ", ");
		}
		System.out.println("};");
		System.out.println();


		for (int i = 0; i < l.getRecord(0).getFieldCount(); i++) {
			FieldDetail field = l.getRecord(0).getField(i);
			Type t = m.getType(field.getType());

			assertTrue("Field: " + field.getName(), (t instanceof TypeNum && (! ((TypeNum) t).isPositive())));
			assertEquals("Field Type: " + field.getName() + " " + cobolDialect, types[i], field.getType());
		}

	}


	private static LayoutDetail getCobolLayout(int cobolDialect, String cobolCopybook) throws RecordException {
		CobolCopybookLoader loader = new CobolCopybookLoader();
		ByteArrayInputStream bs = new ByteArrayInputStream(cobolCopybook.getBytes());


		return loader.loadCopyBook(bs, "ZZ-TTT4", CopybookLoader.SPLIT_01_LEVEL, 0, "", cobolDialect, 0, new TextLog())
				.asLayoutDetail();
	}
}

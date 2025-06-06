/*  -------------------------------------------------------------------------
 *
 *            Sub-Project: JRecord IOBuilder examples
 *    
 *    Sub-Project purpose: Examples of using JRecord IOBuilders
 *                        to perform IO on Cobol Data files
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
      
package com.tech4box.JRecord.zExamples.iob.cobol.specialCases;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.tech4box.JRecord.JRecordInterface1;
import com.tech4box.JRecord.Common.Constants;
import com.tech4box.JRecord.Common.IFieldDetail;
import com.tech4box.JRecord.Common.RecordException;
import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.Details.RecordDetail;
import com.tech4box.JRecord.IO.AbstractLineReader;
import com.tech4box.JRecord.Numeric.ICopybookDialects;
import com.tech4box.JRecord.def.IO.builders.ICobolIOBuilder;

/**
 * This example illustrates using getUniqueField method.
 * This method retrieves a Field Definition using both the Field-Name and Group-Names.
 * You only need specify enough "Group-Names" to uniquely identify a field but you do
 * not need to specify all of them. The Group names can be specified in any sequence.
 *
 * There is a second method getUniqueFieldGroupsInSequence where group-names must be
 * specified in the correct sequence. See Xmpl_DuplicateNames2 for an example
 * of using getUniqueFieldGroupsInSequence
 *
 * @author Bruce Martin
 *
 */
public class Xmpl_DuplicateNames2 {

	private static String cobolCopybook
					= "        01  xx.\n"
					+ "            03 A.\n"
					+ "               05 B.\n"
					+ "                  07 Field-1    pic x(3).\n"
					+ "               05 C.\n"
					+ "                  07 Field-1    pic 9(4).\n"
					+ "            03 B.\n"
					+ "               05 A.\n"
					+ "                  07 Field-1    pic s9(6).\n"
					+ "            03 C.\n"
					+ "               05 A.\n"
					+ "                  07 Field-1    pic s9(3)v999.\n"
					+ "               05 D.\n"
					+ "                  07 Field-1    pic s9(5)v999.\n"
			;


	private static String dataFile
				= "11 001200001C01400{0001500{\n"
				+ "21 002200002C02400{0002500{\n"
				+ "31 003200003C03400{0003500{\n"
				+ "41 004200004C04400{0004500{\n"
				;


	public static void main(String[] args) throws RecordException, IOException {

		ICobolIOBuilder ioBldr = JRecordInterface1.COBOL
				.newIOBuilder(new ByteArrayInputStream(cobolCopybook.getBytes()), "COMPANY-RECORD")
					.setDialect(ICopybookDialects.FMT_MAINFRAME)
					.setFileOrganization(Constants.IO_BIN_TEXT);
		
		LayoutDetail schema = ioBldr.getLayout();
		int recordIdx = 0;  // since there is only one record type, the record index must be zero
		                    // If there where more than one record, you could use:
		                    //    schema.getRecordIndex("COMPANY-RECORD");

			// Retrieve the Record-Definition
		RecordDetail record = schema.getRecord(recordIdx);

			// ** Retrieve the Field definitions from the RecordDefinition **
			// ** -------------------------------------------------------- **
		IFieldDetail abField = record.getGroupField("A", "B", "Field-1");
		IFieldDetail baField = record.getGroupField("B", "A", "Field-1");
		IFieldDetail caField = record.getGroupField("C", "A", "Field-1");

			// Retrieve the File-Reader Constants.IO_BIN_TEXT does byte level read lines
			// i.e. it retrieves lines (of bytes) from the file.
		AbstractLineReader reader  = ioBldr.newReader(new ByteArrayInputStream(dataFile.getBytes()));
		AbstractLine line;

		while ((line = reader.read()) != null) {
			System.out.println(
					  line.getFieldValue(abField) .asString() + "\t"
					+ line.getFieldValue(baField) .asString()   + "\t"
					+ line.getFieldValue(caField) .asString()
			);
		}

		reader.close();
	}
}

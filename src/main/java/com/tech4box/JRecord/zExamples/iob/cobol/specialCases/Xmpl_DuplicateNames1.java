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
public class Xmpl_DuplicateNames1 {

	private static String cobolCopybook
			= "      01 COMPANY-RECORD.\n"
			+ "         05 COMPANY-NAME     PIC X(30).\n"
			+ "         05 EMPLOYEE-LIST.\n"
			+ "            10 PRESIDENT.\n"
			+ "               15 LAST-NAME  PIC X(15).\n"
			+ "               15 FIRST-NAME PIC X(8).\n"
			+ "            10 VICE-PRESIDENT.\n"
			+ "               15 LAST-NAME  PIC X(15).\n"
			+ "               15 FIRST-NAME PIC X(8).\n"
			+ "            10 OTHERS.\n"
			+ "               15 TITLE      PIC X(10).\n"
			+ "               15 LAST-NAME  PIC X(15).\n"
			+ "               15 FIRST-NAME PIC X(8).\n"
			+ "         05 ADDRESS          PIC X(15).\n"
			+ "         05 CITY             PIC X(15).\n"
			+ "         05 STATE            PIC XX.\n"
			+ "         05 ZIP              PIC 9(5).\n";


	private static String dataFile
				= "BM                            Martin         Bruce   XX             YY      Mr        Aa             Bb      Me             George Town    Ta07253\n"
				+ "JPY                           John           Young   Young          George  Mr        Hary           Vander  123            456            1100111\n"
				+ "Fleetwood Mac                 Fleetwood      Mick    Stevie         Nicks   Ms        McVie          Christinx              y              z 01234\n"
				;

	public static void main(String[] args) throws RecordException, IOException {
		ICobolIOBuilder ioBldr = JRecordInterface1.COBOL
				.newIOBuilder(new ByteArrayInputStream(cobolCopybook.getBytes()), "COMPANY-RECORD")
					.setDialect(ICopybookDialects.FMT_INTEL)
					.setFileOrganization(Constants.IO_BIN_TEXT);
		
		LayoutDetail schema = ioBldr.getLayout();

		int recordIdx = 0;  // since there is only one record type, the record index must be zero
		                    // If there where more than one record, you could use:
		                    //    schema.getRecordIndex("COMPANY-RECORD");

			// Retrieve the Record-Definition
		RecordDetail record = schema.getRecord(recordIdx);

			// ** Retrieve the Field definitions from the RecordDefinition **
			// ** -------------------------------------------------------- **
		IFieldDetail presidentFirstNameFld     = record.getGroupField("PRESIDENT", "FIRST-NAME");
		IFieldDetail vicePresidentFirstNameFld = record.getGroupField("VICE-PRESIDENT", "FIRST-NAME");
		IFieldDetail otherFirstNameFld         = record.getGroupField("OTHERS", "FIRST-NAME");

		IFieldDetail presidentLastNameFld      = record.getGroupField("PRESIDENT", "LAST-NAME");
		IFieldDetail vicePresidentLastNameFld  = record.getGroupField("VICE-PRESIDENT", "LAST-NAME");
		IFieldDetail otherLastNameFld          = record.getGroupField("OTHERS", "LAST-NAME");


			//Get the compane-name field definition.
		IFieldDetail companyNameFld = schema.getFieldFromName("COMPANY-NAME");

		AbstractLineReader reader  = ioBldr.newReader(new ByteArrayInputStream(dataFile.getBytes())) ;
		AbstractLine line;


		while ((line = reader.read()) != null) {
			System.out.println(
					  line.getFieldValue(companyNameFld)           .asString() + "\t"
					+ line.getFieldValue(presidentFirstNameFld)    .asString() + "\t"
					+ line.getFieldValue(presidentLastNameFld)     .asString() + "\t|\t"
					+ line.getFieldValue(vicePresidentFirstNameFld).asString() + "\t"
					+ line.getFieldValue(vicePresidentLastNameFld) .asString() + "\t|\t"
					+ line.getFieldValue(otherFirstNameFld)        .asString() + "\t"
					+ line.getFieldValue(otherLastNameFld)         .asString()
			);
		}

		reader.close();
	}
}

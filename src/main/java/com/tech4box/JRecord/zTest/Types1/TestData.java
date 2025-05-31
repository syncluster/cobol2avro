/**
 * 
 */
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

package com.tech4box.JRecord.zTest.Types1;

import com.tech4box.JRecord.Common.Constants;
import com.tech4box.JRecord.Common.Conversion;
import com.tech4box.JRecord.Common.FieldDetail;
import com.tech4box.JRecord.Common.IFieldDetail;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.External.CopybookLoader;
import com.tech4box.JRecord.External.CopybookLoaderFactory;
import com.tech4box.JRecord.External.ExternalRecord;
import com.tech4box.JRecord.Log.TextLog;
import com.tech4box.JRecord.Numeric.ICopybookDialects;

/**
 * This class holds the TestData definition + its
 * Fields
 * 
 * @author Bruce Martin
 *
 */
public class TestData {
	public final LayoutDetail testDataDefinition;
	public final String charset;
	public final IFieldDetail[] spaceFields = new FieldDetail[6];
	public final IFieldDetail typeDescription, typeNumber, fieldLength, decimalLength, testValue, testResult, testResultHex;
	
	/**
	 * This method loads the TestData cobol copybook
	 * @param charset
	 */
	public TestData(String charset) throws Exception { 
		
		this.charset = charset;

		testDataDefinition = loadCopybook(Conversion.DEFAULT_ASCII_CHARSET);
		
		/**
		 * Extracting the field Definitions, it is slightly more efficient
		 * to access a field using a field definition instead of using the field-name
		 * Although IO overheads will overwhelm any differences.
		 */
		for (int i = 0; i < 6; i++) {
			spaceFields[i] = testDataDefinition.getFieldFromName("sp" + (i+1));
		}
		
		typeDescription = testDataDefinition.getFieldFromName("Type-Description");
		typeNumber = testDataDefinition.getFieldFromName("Type-Number"); 
		fieldLength = testDataDefinition.getFieldFromName("Field-Length"); 
		decimalLength = testDataDefinition.getFieldFromName("Decimal-Length"); 
		testValue = testDataDefinition.getFieldFromName("Test-Value"); 
		testResult  = testDataDefinition.getFieldFromName("Test-Result");
		testResultHex = testDataDefinition.getFieldFromName("Test-Result-Hex");

	}
	

	public LayoutDetail getCopybook(String charset) {
		try {
			return loadCopybook(charset);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	

	private LayoutDetail loadCopybook(String charset) throws Exception {
		String copyName = this.getClass().getResource("TestData.cbl").getFile();
		CopybookLoader cpybookLoader = CopybookLoaderFactory.getInstance().getLoader(
				CopybookLoaderFactory.COBOL_LOADER);

		/* Load as interchange format */
		ExternalRecord rec = cpybookLoader.loadCopyBook(
					copyName, CopybookLoader.SPLIT_NONE, 0,
					/* Font name */ charset, ICopybookDialects.FMT_MAINFRAME_COMMA_DECIMAL, 
					0, new TextLog());
		rec.setFileStructure(Constants.IO_TEXT_LINE);

		/* Create Layout / Description */
		return rec.asLayoutDetail();

	}
}

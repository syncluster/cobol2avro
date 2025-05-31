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

package com.tech4box.JRecord.zTest.detailsSelection;

import junit.framework.TestCase;
import com.tech4box.JRecord.Common.FieldDetail;
import com.tech4box.JRecord.Types.Type;
import com.tech4box.JRecord.detailsSelection.FieldSelect;
import com.tech4box.JRecord.detailsSelection.FieldSelectX;

public class TstFieldSelectX1 extends TestCase {

	public static final String[][] GET_RESULTS_TEXT_FIELD = {
		{"=", "com.tech4box.JRecord.detailsSelection.FieldSelectX$EqualsSelect", "false"},
		{"eq", "com.tech4box.JRecord.detailsSelection.FieldSelectX$EqualsSelect", "false"},
		{"!=", "com.tech4box.JRecord.detailsSelection.FieldSelectX$NotEqualsSelect", "false"},
		{"<>", "com.tech4box.JRecord.detailsSelection.FieldSelectX$NotEqualsSelect", "false"},
		//{"<>", "com.tech4box.JRecord.detailsSelection.FieldSelectX$EqualsSelect", "false"},
		{"ne", "com.tech4box.JRecord.detailsSelection.FieldSelectX$NotEqualsSelect", "false"},
		{"<> (Text)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$NotEqualsSelect", "false"},
		{"<> (Numeric)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$NotEqualsSelect", "true"},
		{">", "com.tech4box.JRecord.detailsSelection.FieldSelectX$GreaterThan", "false"},
		{"gt", "com.tech4box.JRecord.detailsSelection.FieldSelectX$GreaterThan", "false"},
		{">=", "com.tech4box.JRecord.detailsSelection.FieldSelectX$GreaterThan", "false"},
		{"ge", "com.tech4box.JRecord.detailsSelection.FieldSelectX$GreaterThan", "false"},
		{"<", "com.tech4box.JRecord.detailsSelection.FieldSelectX$LessThan", "false"},
		{"lt", "com.tech4box.JRecord.detailsSelection.FieldSelectX$LessThan", "false"},
		{"<=", "com.tech4box.JRecord.detailsSelection.FieldSelectX$LessThan", "false"},
		{"le", "com.tech4box.JRecord.detailsSelection.FieldSelectX$LessThan", "false"},
		{"Starts With", "com.tech4box.JRecord.detailsSelection.FieldSelect$StartsWith", ""},
		{"Doesn't Contain", "com.tech4box.JRecord.detailsSelection.FieldSelect$DoesntContain", ""},
		{"Contains", "com.tech4box.JRecord.detailsSelection.FieldSelect$Contains", ""},
		{"= (Numeric)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$EqualsSelect", "true"},
		{"> (Numeric)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$GreaterThan", "true"},
		{">= (Numeric)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$GreaterThan", "true"},
		{"< (Numeric)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$LessThan", "true"},
		{"<= (Numeric)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$LessThan", "true"},
		{"= (Text)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$EqualsSelect", "false"},
		{"> (Text)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$GreaterThan", "false"},
		{">= (Text)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$GreaterThan", "false"},
		{"< (Text)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$LessThan", "false"},
		{"<= (Text)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$LessThan", "false"},
	};


	public static final String[][] GET_RESULTS_NUM_FIELD = {
		{"=", "com.tech4box.JRecord.detailsSelection.FieldSelectX$EqualsSelect", "true"},
		{"eq", "com.tech4box.JRecord.detailsSelection.FieldSelectX$EqualsSelect", "true"},
		{"!=", "com.tech4box.JRecord.detailsSelection.FieldSelectX$NotEqualsSelect", "true"},
		//{"<>", "com.tech4box.JRecord.detailsSelection.FieldSelectX$EqualsSelect", "true"},
		{"<>", "com.tech4box.JRecord.detailsSelection.FieldSelectX$NotEqualsSelect", "true"},
		{"ne", "com.tech4box.JRecord.detailsSelection.FieldSelectX$NotEqualsSelect", "true"},
		{"<> (Text)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$NotEqualsSelect", "false"},
		{"<> (Numeric)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$NotEqualsSelect", "true"},
		{">", "com.tech4box.JRecord.detailsSelection.FieldSelectX$GreaterThan", "true"},
		{"gt", "com.tech4box.JRecord.detailsSelection.FieldSelectX$GreaterThan", "true"},
		{">=", "com.tech4box.JRecord.detailsSelection.FieldSelectX$GreaterThan", "true"},
		{"ge", "com.tech4box.JRecord.detailsSelection.FieldSelectX$GreaterThan", "true"},
		{"<", "com.tech4box.JRecord.detailsSelection.FieldSelectX$LessThan", "true"},
		{"lt", "com.tech4box.JRecord.detailsSelection.FieldSelectX$LessThan", "true"},
		{"<=", "com.tech4box.JRecord.detailsSelection.FieldSelectX$LessThan", "true"},
		{"le", "com.tech4box.JRecord.detailsSelection.FieldSelectX$LessThan", "true"},
		{"Starts With", "com.tech4box.JRecord.detailsSelection.FieldSelect$StartsWith", ""},
		{"Doesn't Contain", "com.tech4box.JRecord.detailsSelection.FieldSelect$DoesntContain", ""},
		{"Contains", "com.tech4box.JRecord.detailsSelection.FieldSelect$Contains", ""},
		{"= (Numeric)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$EqualsSelect", "true"},
		{"> (Numeric)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$GreaterThan", "true"},
		{">= (Numeric)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$GreaterThan", "true"},
		{"< (Numeric)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$LessThan", "true"},
		{"<= (Numeric)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$LessThan", "true"},
		{"= (Text)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$EqualsSelect", "false"},
		{"> (Text)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$GreaterThan", "false"},
		{">= (Text)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$GreaterThan", "false"},
		{"< (Text)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$LessThan", "false"},
		{"<= (Text)", "com.tech4box.JRecord.detailsSelection.FieldSelectX$LessThan", "false"},

	};


	public void testGetTextField() {
		FieldSelect fs;
		String num;
		FieldDetail fd = new FieldDetail("", "", Type.ftChar, 0, "", 0, "");
		fd.setPosOnly(1);

		for (int i = 0; i < GET_RESULTS_TEXT_FIELD.length; i++) {
			fs = FieldSelectX.get("", "0", GET_RESULTS_TEXT_FIELD[i][0], fd);

			num = "";
			if (fs instanceof FieldSelectX) {
				num = "" + ((FieldSelectX) fs).isNumeric();
			}
			assertEquals("Checking Class For: " + GET_RESULTS_TEXT_FIELD[i][0], GET_RESULTS_TEXT_FIELD[i][1], fs.getClass().getName());
			assertEquals("Checking Numeric For: " + GET_RESULTS_TEXT_FIELD[i][0], GET_RESULTS_TEXT_FIELD[i][2], num);
			//System.out.println("\t{\"" + Constants.VALID_COMPARISON_OPERATORS[i]
			//		+"\", \"" + fs.getClass().getName() + "\", \"" + num + "\"}," );
		}
	}



	public void testGetNumField() {
		FieldSelect fs;
		String num;
		FieldDetail fd = new FieldDetail("", "", Type.ftNumLeftJustified, 0, "", 0, "");
		fd.setPosOnly(1);

		for (int i = 0; i < GET_RESULTS_NUM_FIELD.length; i++) {
			fs = FieldSelectX.get("", "0", GET_RESULTS_NUM_FIELD[i][0], fd);

			num = "";
			if (fs instanceof FieldSelectX) {
				num = "" + ((FieldSelectX) fs).isNumeric();
			}
			assertEquals("Checking Class For: " + GET_RESULTS_NUM_FIELD[i][0], GET_RESULTS_NUM_FIELD[i][1], fs.getClass().getName());
			assertEquals("Checking Numeric For: " + GET_RESULTS_NUM_FIELD[i][0], GET_RESULTS_NUM_FIELD[i][2], num);
//			System.out.println("\t{\"" + Constants.VALID_COMPARISON_OPERATORS[i]
//					+"\", \"" + fs.getClass().getName() + "\", \"" + num + "\"}," );
		}
	}

}

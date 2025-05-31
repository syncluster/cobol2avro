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

import java.io.IOException;

import com.tech4box.JRecord.Common.AbstractFieldValue;
import com.tech4box.JRecord.Common.Conversion;
import com.tech4box.JRecord.Common.FieldDetail;
import com.tech4box.JRecord.Common.IFieldDetail;
import com.tech4box.JRecord.Common.RecordException;
import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.Details.CharLine;
import com.tech4box.JRecord.Details.Line;
import com.tech4box.JRecord.External.base.ExternalConversion;
import com.tech4box.JRecord.IO.AbstractLineWriter;
import com.tech4box.JRecord.IO.LineIOProvider;
import com.tech4box.JRecord.Types.Type;
import com.tech4box.JRecord.Types.TypeManager;
import com.tech4box.JRecord.Types.TypeNum;
import com.tech4box.JRecord.Types.TypeSignSeparate;

/**
 * @author Bruce Martin
 *
 */
public class WriteTestFiles {
	TestData td;
	
	
	/**
	 * This method creates a Test Data file.
	 * The File created should be checked manually and it can then be used
	 * to run Tests
	 */
	private void createFile(String charset) {
		try {
			td = new TestData(charset);
			String[] values1 = {"0", "5", "10", "32", "432", "6543", "-5", "-10", "-32", "-432", "255", "256", "257",
					"-255", "-256", "-257"};
			String[] values2 = {"1.1", "-2.1", "54.3"};
			AbstractLineWriter w = LineIOProvider.getInstance().getLineWriter(td.testDataDefinition.getFileStructure(), charset);
			AbstractLine l;
	
			TypeManager m = TypeManager.getInstance();
			Type tch = m.getType(Type.ftChar);
			boolean multiByte = Conversion.isMultiByte(charset);
			
			if (multiByte) {
				l = new CharLine(td.getCopybook(charset), "         ");  // Note it does not matter what record-layout is used 
			} else {
				l = new Line(td.getCopybook(charset));					  // Note it does not matter what record-layout is used 
			}
			
			try {
				w.open("G:\\Temp\\TestData_" + charset +".txt");//TestDataConstants.getTestDataFileName(charset));
				for (int i = 0; i < 200; i++) {
					switch (i) {
					case Type.ftFloat:
					case Type.ftDouble:
					case Type.ftHex:
					case Type.ftBit:
						break;
					case Type.ftCharRestOfRecord:
					case Type.ftCharNullPadded:
					case Type.ftCharNullTerminated:
						break;
					default:
				
						Type type = m.getType(i);
						if ( (  type.isBinary())
						|| (type == tch && i != Type.ftChar)) {	
							
						} else {
							FieldDetail fld1 = TestDataConstants.getType(1, 4, 0, i, charset);
							FieldDetail fld2 = TestDataConstants.getType(1, 4, 1, i, charset);
							
							setValue(w, l, fld1, type, values1);
							
							setValue(w, l, fld2, type, values2);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (RecordException e) {
				e.printStackTrace();
			} finally {		
				w.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Set a field to all the values in an array and write the result to the file
	 * @param writer writer to write the result of the Test to the file
	 * @param line line to be updated
	 * @param fld field to update
	 * @param type Type of the field
	 * @param values values to be used to update the line
	 * @throws RecordException any error from update
	 * @throws IOException any IO error that occurs
	 */
	private void setValue(AbstractLineWriter writer, AbstractLine line, FieldDetail fld, Type type, String[] values) 
	throws RecordException, IOException {
		String s = ExternalConversion.getTypeAsString(0, fld.getType());
		for (String v : values) {
			if ((v.startsWith("-") && (type instanceof TypeNum && ((TypeNum) type).isPositive()))
			|| (v.length() == 4 && (! v.startsWith("-") && type instanceof TypeSignSeparate))
			) {
				
			} else {
				Line outLine = new Line(td.testDataDefinition);	
				AbstractFieldValue fieldValue = line.getFieldValue(fld);
				fieldValue.set(v);
				
				for (IFieldDetail spFld : td.spaceFields) {
					outLine.getFieldValue(spFld).set("");
				}
				outLine.getFieldValue(td.typeDescription).set(s);
				outLine.getFieldValue(td.typeNumber     ).set(fld.getType());
				outLine.getFieldValue(td.fieldLength    ).set(fld.getLen());
				outLine.getFieldValue(td.decimalLength  ).set(fld.getDecimal());
				outLine.getFieldValue(td.testValue      ).set(v);
				outLine.getFieldValue(td.testResult     ).set("");
				outLine.getFieldValue(td.testResultHex  ).set(fieldValue.asHex());
				if (! fieldValue.isBinary()) {
					outLine.getFieldValue(td.testResult)
							.set(line.getFullLine().substring(fld.getPos() - 1, fld.getLen()));
				}
				s = "";
				writer.write(outLine);
			}
		}
	}
	
	/**
	 * This program writes 3 Test files. These files should be checked manually
	 * @param args
	 */
	public static void main(String[] args) {
		
		WriteTestFiles w = new WriteTestFiles();
	
		w.createFile("");
		w.createFile("CP037");
		w.createFile("CP273");

	}

}

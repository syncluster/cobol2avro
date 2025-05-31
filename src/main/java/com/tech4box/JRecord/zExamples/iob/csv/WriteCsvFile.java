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
      
package com.tech4box.JRecord.zExamples.iob.csv;

import java.io.IOException;

import com.tech4box.JRecord.JRecordInterface1;
import com.tech4box.JRecord.Common.AbstractFieldValue;
import com.tech4box.JRecord.Common.CommonBits;
import com.tech4box.JRecord.Common.RecordException;
import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.Details.CharLine;
import com.tech4box.JRecord.Details.FieldIterator;
import com.tech4box.JRecord.IO.AbstractLineReader;
import com.tech4box.JRecord.IO.AbstractLineWriter;
import com.tech4box.JRecord.Types.Type;
import com.tech4box.JRecord.def.IO.builders.ICsvIOBuilder;
import com.tech4box.JRecord.def.IO.builders.IFixedWidthIOBuilder;
import com.tech4box.JRecord.zTest.Common.TstConstants;


/**
 * Copy a Fixed width file to a Csv
 * The Fixed-Width files and Csv files are defined with IOBuilders.
 * 
 * @author Bruce Martin
 *
 */
public class WriteCsvFile {

	public WriteCsvFile() {
		
		CommonBits.setUseCsvLine(true);
		try {
			IFixedWidthIOBuilder inIOBuilder = JRecordInterface1.FIXED_WIDTH.newIOBuilder()
						.defineFieldsByLength()
							.addFieldByLength("Sku"  , Type.ftChar,              8, 0)
							.addFieldByLength("Store", Type.ftNumRightJustified, 3, 0)
							.addFieldByLength("Date" , Type.ftNumRightJustified, 6, 0)
							.addFieldByLength("Dept" , Type.ftNumRightJustified, 3, 0)
							.addFieldByLength("Qty"  , Type.ftNumRightJustified, 2, 0)
							.addFieldByLength("Price", Type.ftNumRightJustified, 6, 2)
						.endOfRecord();

			ICsvIOBuilder outIOBuilder = JRecordInterface1.CSV.newIOBuilder(",", "'")
						.defineFields()
							.addCsvField("Sku",   Type.ftChar, 0)
							.addCsvField("Store", Type.ftNumAnyDecimal, 0)
							.addCsvField("Date",  Type.ftNumAnyDecimal, 0)
							.addCsvField("Dept",  Type.ftNumAnyDecimal, 0)
							.addCsvField("Qty",   Type.ftNumAnyDecimal, 0)
							.addCsvField("Price", Type.ftNumAnyDecimal, 0)
						.endOfRecord();


			String outputFileName = TstConstants.TEMP_DIRECTORY + "csvDTAR020_Tst1.csv";
			AbstractLine saleRecord;
			AbstractLine outCsvRecord = new CharLine(outIOBuilder.getLayout(), "");
			AbstractLineReader reader = inIOBuilder.newReader(this.getClass().getResource("DTAR020_tst1.bin.txt").getFile());
			AbstractLineWriter writer = outIOBuilder.newWriter(outputFileName);

			System.out.println("Output File: " + outputFileName);
			try {
				while ((saleRecord = reader.read()) != null) {
					 FieldIterator fields = saleRecord.getFieldIterator(0);
					 
					 for (AbstractFieldValue fieldValue : fields) {
						 outCsvRecord.getFieldValue(fieldValue.getFieldDetail().getName())
						 			 .set(fieldValue.asString());
					 }
					 writer.write(outCsvRecord);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				reader.close();
				writer.close();
			}
		} catch (RecordException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new WriteCsvFile();
	}
}

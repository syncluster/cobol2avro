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

package com.tech4box.JRecord.zTest.Cobol.occursDependingOn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import com.tech4box.JRecord.Common.AbstractFieldValue;
import com.tech4box.JRecord.Common.Constants;
import com.tech4box.JRecord.Common.IFieldDetail;
import com.tech4box.JRecord.Common.RecordException;
import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.IO.CobolIoProvider;
import com.tech4box.JRecord.Numeric.ICopybookDialects;
import com.tech4box.JRecord.def.IO.builders.ICobolIOBuilder;

/**
 * Test Occurs depending on with one nested occurs !!!
 * @author Bruce Martin
 *
 */
public class TstOccursDepending25 extends TestCase {

	private static final String MONTHS = "months";
	private static final String WEEK_NO = "week-no";

	public void testPositionCalc1() throws Exception {
		try {
			tstPosition("OccursDependingOn25.cbl");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	

	
	private  void tstPosition(String copybookFile)  throws IOException, RecordException {
		String copybookFileName = WriteSampleFile.class.getResource(copybookFile).getFile();
		ICobolIOBuilder ioBuilder = CobolIoProvider.getInstance()
				.newIOBuilder(copybookFileName, ICopybookDialects.FMT_MAINFRAME)
					.setFileOrganization(Constants.IO_STANDARD_TEXT_FILE);


		boolean normal = copybookFile.endsWith("1.cbl");
		for (int i = 0; i < 14; i++) {
			for (int j = 0; j < 8; j++) {
				for (int week = 1; week < 4; week++) {
					for (int day = 1; day < 8; day++) {
						tstLine(ioBuilder.newLine(), i, j, week, day, normal);
					}
				}
			}
		}

	}
	

	private void tstLine(AbstractLine line, int purchaseCount, int salesCount, int week, int day, boolean normalPos) throws RecordException {
		String idm = purchaseCount + "~" + salesCount + ":" + week + " ~ ";
		LayoutDetail layout = line.getLayout();
		IFieldDetail weekNoFld = layout.getFieldFromName(WEEK_NO);
		IFieldDetail monthFld = layout.getFieldFromName(MONTHS);
		IFieldDetail weekCountFld = layout.getFieldFromName("week-of-month");
		IFieldDetail dayFld = layout.getFieldFromName("days");
		//IFieldDetail purchCountFld = layout.getFieldFromName("total-purchase-count");
		
			/** Setting the Occurs Depending fields !!! **/
		line.getFieldValue(monthFld).set(salesCount);
		line.getFieldValue(weekCountFld).set(week);
		line.getFieldValue(dayFld).set(day);
		@SuppressWarnings("deprecation")
		int pos = dayFld.getEnd() + 1;
		ArrayList<IFieldDetail> l = new ArrayList<IFieldDetail>(200);
		
		line.getFieldValue(weekNoFld).set(purchaseCount);
				
		check(l, line, layout.getFieldFromName("Location-Number"));
		check(l, line, layout.getFieldFromName("Location-Name"));

		l.add(monthFld);
		l.add(weekCountFld);
		l.add(dayFld);

		for (int i = 0; i < salesCount; i++) {
			//System.out.println("==> " + purchaseCount + ", sc=" + salesCount + ", week=" + week + " " + i);
			IFieldDetail countFld = layout.getFieldFromName("sales-count (" + i + ")");
			IFieldDetail valueFld = layout.getFieldFromName("sales-value (" + i + ")");
			if (i == 1 && salesCount==2 && week==2) {
				System.out.print('*');
			}
			for (int w = 0; w < week; w++) {
				for (int d = 0; d < day; d++) {
					pos = check(l, line, layout.getFieldFromName("daily-sales (" + i + ", " + w + ", " + d + ")"), pos);
				}
			}
			pos = check(l, line, countFld, pos);
			pos = check(l, line, valueFld, pos);
		}

		pos = check(l, line, layout.getFieldFromName("total-sales"), pos);
		pos = check(l, line, layout.getFieldFromName(WEEK_NO), pos);		
	

		for (int i = 0; i < purchaseCount; i++) {
			pos = check(l, line, layout.getFieldFromName("purchase-count (" + i + ")"), pos);
			pos = check(l, line, layout.getFieldFromName("purchase-value (" + i + ")"), pos);
		}

		pos = check(l, line, layout.getFieldFromName("total-purchase-count"), pos);
		pos = check(l, line, layout.getFieldFromName("total-purchase-value"), pos);


		Code.checkFieldIteratore(line, idm, l);

//		FieldIterator fi = line.getFieldIterator(0);
//		int i = 0;
//		while (fi.hasNext()) {
//			AbstractFieldValue fv = fi.next();
//			if (fv.getFieldDetail() != l.get(i)) {
//				//System.out.println(fv.getFieldDetail().getName() + " \t" + l.get(i).getName() );
//				assertEquals(fv.getFieldDetail().getName(), l.get(i).getName() );
//			}
//			i += 1;
//		}
	
//		System.out.println("** line: " + purchaseCount + " " + salesCount + " length=" + line.getData().length);
	}

	private void check(List<IFieldDetail> fieldList, AbstractLine line, IFieldDetail fld) throws RecordException {
		check(fieldList, line, fld, fld.getPos());
	}
	
	private int check(List<IFieldDetail> fieldList, AbstractLine line, IFieldDetail fld, int pos) throws RecordException {
		String id = fld.getName();
		int calculatedPosition = fld.calculateActualPosition(line);
		fieldList.add(fld);
		
		if (pos != calculatedPosition) {
			calculatedPosition = fld.calculateActualPosition(line);
			assertEquals(id, pos, calculatedPosition);
		}
		int end = pos + fld.getLen() - 1;
		assertEquals(id, end, fld.calculateActualEnd(line));
		
		if (WEEK_NO.equalsIgnoreCase(fld.getName()) || MONTHS.equalsIgnoreCase(fld.getName())) {
			
		} else {
			for (int i = 0; i < 4; i++) {
				setAndCheck(line, fld, i);
			}
		}
		assertTrue(line.getFieldValue(fld).isFieldInRecord());

		assertTrue(line.isFieldInLine(fld));
		return end + 1;
	}
	
	private void setAndCheck(AbstractLine line, IFieldDetail fld, int value) throws RecordException {
		
		AbstractFieldValue fieldValue = line.getFieldValue(fld);
		fieldValue.set(value);
		if (fieldValue.isNumeric()) {
			if (fld.getDecimal() == 0) {
				assertEquals(value, fieldValue.asInt());
			} else {
				assertEquals(Integer.toString(value) + ".00", fieldValue.asString());
			}
		} else {
			assertEquals(Integer.toString(value), fieldValue.asString());
		}
	}

}

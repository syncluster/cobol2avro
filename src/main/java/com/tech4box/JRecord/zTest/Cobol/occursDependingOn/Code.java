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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import com.tech4box.JRecord.Common.AbstractFieldValue;
import com.tech4box.JRecord.Common.FieldDetail;
import com.tech4box.JRecord.Common.IFieldDetail;
import com.tech4box.JRecord.Common.RecordException;
import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.Details.FieldIterator;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.occursDepending.IOccursDependingPositionCalculation;
import com.tech4box.JRecord.occursDepending.ODCalculationComplex;

public class Code {
	
	public static final String MONTHS_FIELD_NAME = "months";
	public static final String WEEK_NO_FIELD_NAME = "week-no";

	private static final BigDecimal BD_1p1 = new BigDecimal("1.1");
	private static final BigDecimal BD_3p1 = new BigDecimal("3.1");
	private static final BigDecimal BD_5p1 = new BigDecimal("5.1");

	
	
	public static AbstractLine generateLine(AbstractLine line, int purchaseCount, int salesCount) throws RecordException {
		generateSalesLine(line, purchaseCount, salesCount, true);
	
		int count = (purchaseCount * salesCount * 2) % 100;
		line.getFieldValue("total-purchase-count").set(count);
		BigDecimal expected = BigDecimal.valueOf(count).multiply(BD_5p1).setScale(2, BigDecimal.ROUND_HALF_UP);
		line.getFieldValue("total-purchase-value").set(expected); 

		for (int i = 0; i < purchaseCount; i++) {
//			System.out.println("  purch: " + purchaseCount + " " + salesCount + " " + i);
			line.getFieldValue("purchase-count (" + i + ")").set(i + salesCount);
			line.getFieldValue("purchase-value (" + i + ")").set((i +salesCount) * 1.1);
		}
		
//		System.out.println("** line: " + purchaseCount + " " + salesCount + " length=" + line.getData().length);
		return line;
	}


	/**
	 * @param line
	 * @param purchaseCount
	 * @param salesCount
	 * @param layout
	 */
	public static AbstractLine generateSalesLine(AbstractLine line, int purchaseCount,
			int salesCount, boolean writeValue) {
		LayoutDetail layout = line.getLayout();
		line.getFieldValue("months").set(salesCount);     // month should be set first
		line.getFieldValue("week-no").set(purchaseCount); // before week-no because week-no's position is determined by months value !!
		
//		System.out.println("  week pos: " + purchaseCount + " " + salesCount + "  " 
//				+ " " + weekFld.calculateActualPosition(line)
//				+ " " + purchCountFld.calculateActualPosition(line));
		
		line.getFieldValue("Location-Number").set(salesCount * 100 + purchaseCount);
		line.getFieldValue("Location-Name").set("Store " + salesCount + ", " + purchaseCount);

		line.getFieldValue("total-sales").set(salesCount * 100 + purchaseCount);
	
		for (int i = 0; i < salesCount; i++) {
			IFieldDetail countFld = layout.getFieldFromName("sales-count (" + i + ")");
	
//			if (salesCount == 7 && i == 5) {
//				System.out.print('*');
//			}
//			System.out.println("  sales: " + purchaseCount + " " + salesCount + " " + i
//					+ " " + weekFld.calculateActualPosition(line)
//					+ " " + countFld.calculateActualPosition(line)
//					+ " " + valueFld.calculateActualPosition(line)
//					+ " " + (valueFld.calculateActualPosition(line) + valueFld.getLen() - 1));
			line.getFieldValue(countFld).set(i + purchaseCount);
			if (writeValue) {
				IFieldDetail valueFld = layout.getFieldFromName("sales-value (" + i + ")");
				line.getFieldValue(valueFld).set((i + purchaseCount) * 3.1);
			}
		}
		
		return line;
	}
	
	
	public static void checkLine(AbstractLine line, int purchaseCount, int salesCount) throws RecordException {
//		String storeName = "Store " + salesCount + ", " + purchaseCount;
//		System.out.println("** line: " + purchaseCount + " " + salesCount + " length=" + line.getData().length 
//				+ " " + storeName
//				+ " " + salesCount + " ~ " + line.getFieldValue("months").asInt()
//				+ " " + purchaseCount + " ~ " + line.getFieldValue("week-no").asInt()
//		);

		checkSalesRecord(line, purchaseCount, salesCount, true);

		int count = (purchaseCount * salesCount * 2) % 100;
		TestCase.assertEquals(count, line.getFieldValue("total-purchase-count").asInt());
		
		BigDecimal expected = BigDecimal.valueOf(count).multiply(BD_5p1).setScale(2, BigDecimal.ROUND_HALF_UP);
//		System.out.println(expected + " " + count + " " + (count * 5.1) + " " );
		TestCase.assertEquals(expected, line.getFieldValue("total-purchase-value").asBigDecimal()); 
		for (int i = 0; i < purchaseCount; i++) {
			TestCase.assertEquals(i + salesCount, line.getFieldValue("purchase-count (" + i + ")").asInt());
			TestCase.assertEquals(
						BigDecimal.valueOf(i + salesCount).multiply(BD_1p1).setScale(2),
						line.getFieldValue("purchase-value (" + i + ")").asBigDecimal());
			
        }
		
	}


	/**
	 * @param line
	 * @param purchaseCount
	 * @param salesCount
	 * @param layout
	 * @param storeName
	 */
	public static void checkSalesRecord(AbstractLine line, int purchaseCount,
			int salesCount, boolean checkSalesValue) {
		LayoutDetail layout = line.getLayout();
		String storeName = "Store " + salesCount + ", " + purchaseCount;
		TestCase.assertEquals(salesCount, line.getFieldValue("months").asInt());
		TestCase.assertEquals(purchaseCount, line.getFieldValue("week-no").asInt()); 

		TestCase.assertEquals(salesCount * 100 + purchaseCount, line.getFieldValue("Location-Number").asInt());
		TestCase.assertEquals(storeName, line.getFieldValue("Location-Name").asString());

		TestCase.assertEquals(salesCount * 100 + purchaseCount, line.getFieldValue("total-sales").asBigDecimal().intValue());

		for (int i = 0; i < salesCount; i++) {
			IFieldDetail countFld = layout.getFieldFromName("sales-count (" + i + ")");
			
			if (salesCount == 7 && i == 5) {
				System.out.print('*');
			}

			TestCase.assertEquals(i + purchaseCount, line.getFieldValue(countFld).asInt());
			if (checkSalesValue) {
				IFieldDetail valueFld = layout.getFieldFromName("sales-value (" + i + ")");
				TestCase.assertEquals(
						BigDecimal.valueOf(i + purchaseCount).multiply(BD_3p1).setScale(2), 
						line.getFieldValue(valueFld).asBigDecimal());
			}
		}
	}

	public static int check(List<IFieldDetail> fieldList, AbstractLine line, IFieldDetail fld, int pos) throws RecordException {
		String id = fld.getName();
		fieldList.add(fld);
		
		if (pos != fld.calculateActualPosition(line)) {
			TestCase.assertEquals(id + " :- " + fld.getPos(), pos, fld.calculateActualPosition(line));
		}
		int end = pos + fld.getLen() - 1;
		TestCase.assertEquals(id, end, fld.calculateActualEnd(line));
		
		if (WEEK_NO_FIELD_NAME.equalsIgnoreCase(fld.getName()) || MONTHS_FIELD_NAME.equalsIgnoreCase(fld.getName())) {
			
		} else {
			for (int i = 0; i < 4; i++) {
				setAndCheck(line, fld, i);
			}
		}
		
		if (! line.isFieldInLine(fld)) {
			TestCase.assertTrue(fld.getName(), line.isFieldInLine(fld));
		}
		TestCase.assertTrue(fld.getName(), line.getFieldValue(fld).isFieldInRecord());

		return end + 1;
	}
	
	private static void setAndCheck(AbstractLine line, IFieldDetail fld, int value) throws RecordException {
		
		AbstractFieldValue fieldValue = line.getFieldValue(fld);
		fieldValue.set(value);
		if (fieldValue.isNumeric()) {
			if (fld.getDecimal() == 0) {
				TestCase.assertEquals(value, fieldValue.asInt());
			} else {
				TestCase.assertEquals(Integer.toString(value) + ".00", fieldValue.asString());
			}
		} else {
			TestCase.assertEquals(Integer.toString(value), fieldValue.asString());
		}
	}

	

	private static IOccursDependingPositionCalculation odCalc2;
	private static LayoutDetail lastLayout;

	public static void checkFieldIteratore(AbstractLine line, String tstId, ArrayList<IFieldDetail> l) {
		FieldIterator fi = line.getFieldIterator(0);
		int i = 0;
		
		LayoutDetail layout = line.getLayout();
		if (layout != lastLayout) {
			odCalc2 = new ODCalculationComplex(layout.getRecord(0).getDependingOn());
			lastLayout = layout;
		}
		while (fi.hasNext()) {
			AbstractFieldValue fv = fi.next();
			FieldDetail fieldDetail = (FieldDetail) fv.getFieldDetail();
			if (fieldDetail != l.get(i)) {
				TestCase.assertEquals(fieldDetail.getName(), l.get(i).getName() );
			}

			if (i == 7 && tstId.startsWith("1~0:1")) {
				System.out.print('*');
			}
			TestCase.assertEquals(tstId + i + ") " + fieldDetail.getName(),
					fieldDetail.calculateActualPosition(line), 
					odCalc2.calculateActualPosition(line, fieldDetail.getDependingOnDtls(), fieldDetail.getPos()));
			i += 1;
		}
	}

}

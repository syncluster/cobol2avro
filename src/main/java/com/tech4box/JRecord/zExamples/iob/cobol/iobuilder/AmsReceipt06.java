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
      
package com.tech4box.JRecord.zExamples.iob.cobol.iobuilder;

import java.io.IOException;

import com.tech4box.JRecord.Common.Constants;
import com.tech4box.JRecord.Common.IFieldDetail;
import com.tech4box.JRecord.Common.RecordException;
import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.External.CopybookLoader;
import com.tech4box.JRecord.External.ExternalRecord;
import com.tech4box.JRecord.IO.AbstractLineReader;
import com.tech4box.JRecord.IO.CobolIoProvider;
import com.tech4box.JRecord.Numeric.ICopybookDialects;
import com.tech4box.JRecord.def.IO.builders.ICobolIOBuilder;


/**
 * <b>Purpose:</b> This program demonstrates the following<ol>
 *  <li>Using the schema method getGroupField to get a field definition.
 *  <li>adding Record-Selection-Criteria to a Cobol-Copybook.
 *  <li>processing Multiple record types in a file
 * </ol>
 * 
 * <p>The key to doing adding Record-Selection-Criteria is to update the <b>ExternalRecord</b>. 
 * The <b>ExternalRecord</b> is essentially a 
 * <b>"Builder"</b> class for the <b>LayoutDetail</b> (record-schema) class. My background is not Java so I 
 * do not always no the correct OO terminology.
 * 
 * <pre>
 * This testing can also be done in Java code, See:
 * *  AmsReceipt01 - processing with just Java code no Record-Selection-Criteria. This will use less 
 *                   resources than using the  Record-Selection-Criteria   
 * *  AmsReceipt03 - Combines Record-Selection-Details from a Xml file with Field Details from 
 *                   a Cobol Copybook
 *</pre>                   
 *                   
 * @author Bruce Martin
 *
 */
public class AmsReceipt06 {

	
    public static void main(String[] args) throws Exception {
    	new AmsReceipt06();
    }
	
	private static final String copybookFileName = "AmsReceipt.cbl";
	
	//private LayoutDetail schema;
	private final ICobolIOBuilder iobuilder;
    
    
    private AmsReceipt06() throws Exception {
    	String copybookFileame = this.getClass().getResource(copybookFileName).getFile();
    	iobuilder = CobolIoProvider.getInstance()
    						.newIOBuilder(copybookFileame, ICopybookDialects.FMT_FUJITSU)
    								.setFileOrganization(Constants.IO_STANDARD_TEXT_FILE)
    								.setSplitCopybook(CopybookLoader.SPLIT_REDEFINE);
    		
    	readFile();
    }
    
     
    
    private void readFile() throws IOException, RecordException {
    	AbstractLineReader r = iobuilder.newReader("G:\\Users\\BruceTst01\\RecordEditor_HSQL\\SampleFiles\\Ams_Receipt_05AUG08190103.txt");
    	LayoutDetail schema = iobuilder.getLayout();
    	AbstractLine l;
    	String id;
    	
    	// Note:
    	// 1) Specifying the primary record "STDR-DETAIL-RECORD" is not strictly needed
    	// 2) The XXX group is missed for most of the fields; you only need to specify enough
    	//    groups to uniquely identify the field.
    	//
    	IFieldDetail recordType = schema.getGroupField("STDR-DETAIL-RECORD", "XXX", "STDR-RECORD-TYPE");
  
    	IFieldDetail day        = schema.getGroupField("STDR-DETAIL-RECORD", "STDR-FH-CRD-DD");
    	IFieldDetail month      = schema.getGroupField("STDR-DETAIL-RECORD", "STDR-FH-CRD-DD");
    	IFieldDetail century    = schema.getGroupField("STDR-DETAIL-RECORD", "STDR-FH-CRD-DD");
    	IFieldDetail year       = schema.getGroupField("STDR-DETAIL-RECORD", "STDR-FH-CRD-DD");
    	
    	IFieldDetail brand           = schema.getGroupField("STDR-RH", "BRAND-ID-RH");
    	IFieldDetail orderNumber     = schema.getGroupField("STDR-RH", "ORDER-NO-RH");
    	IFieldDetail receiptLocation = schema.getGroupField("STDR-RH", "RECEIPT-LOCN-RH");
    	IFieldDetail receiptNumber   = schema.getGroupField("STDR-RH", "RECEIPT-NO-RH");
    	
    	while ((l = r.read()) != null) {
    		id = l.getFieldValue(recordType).asString();
    		if ("FH".equals(id)) {
    			System.out.println("fh: " 
    					+         l.getFieldValue(day).asString()
    					+ " / " + l.getFieldValue(month).asString()
    					+ " / " + l.getFieldValue(century).asString()
    					+         l.getFieldValue(year).asString()
    			);
    		} else if ("RH".equals(id)) {
       			System.out.println("rh: " 
    					+       l.getFieldValue(brand).asString()
    					+ " " + l.getFieldValue(orderNumber).asString()
    					+ " " + l.getFieldValue(receiptLocation).asString()
    					+ " " + l.getFieldValue(receiptNumber).asString()
   			);
    		}
    	}
    	r.close();
    }
    
    
    /**
     * This method adds a Record-Selection-Test to a Record, three times.
     * The 3 tests will be will be combined with a boolean AND operator   
     *  
     * @param extlayoutCBL Group or parent record
     * @param recordName name of the record to be update
     * @param fieldName Field to Test
     * @param value value to be tested
     */
    @SuppressWarnings("unused")
	private static void addFieldTest3times(ExternalRecord extlayoutCBL, String recordName, String fieldName, String value) {
    	int idx = findRecordIndex(extlayoutCBL, recordName);
    	
    	if (idx < 0) {
    		System.out.println("Record " + recordName + " was not found");
    	} else {
    		// This add a test to the record three times.
    		// The 3 tests will be joined with a logical AND
    		// Obviously this would only make sense if the tests where different.
    		// I did not have any sensible examples handy
    		extlayoutCBL.getRecord(idx).addTstField(fieldName, value);
    		extlayoutCBL.getRecord(idx).addTstField(fieldName, value);
       		extlayoutCBL.getRecord(idx).addTstField(fieldName, value);
    	}
    }

    private static int findRecordIndex(ExternalRecord extlayoutCBL, String recordName) {
    	int ret = -1;
    	
    	for (int i = 0; i < extlayoutCBL.getNumberOfRecords(); i++) {
    		if (recordName.equalsIgnoreCase(extlayoutCBL.getRecord(i).getRecordName())) {
    			ret = i;
    			break;
    		}
    	}
    	
    	return ret;
    }
 }

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
      
package com.tech4box.JRecord.zExamples.iob.recordEditorXml.readWrite;

import com.tech4box.JRecord.JRecordInterface1;
import com.tech4box.JRecord.Common.AbstractFieldValue;
import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.IO.AbstractLineReader;
import com.tech4box.JRecord.IO.AbstractLineWriter;
import com.tech4box.JRecord.def.IO.builders.IIOBuilder;
import com.tech4box.JRecord.zTest.Common.TstConstants;

/**
 * Reading / writing files using a RecordEditor-XML copybook
 * 
 * @author Bruce Martin
 *
 */
public final class XmplLineIOG {


	    /**
	     * Example of<ul>
	     *   <li> Loading an XML copybook (RecordEditor-Xml)
	     *   <li> LineReader / LineWrite classes
	     *   <li> Using <b>isNumeric()</b> method on the fieldValue
	     * </ul>
	     */
	    private XmplLineIOG() {
	        super();

		    double GST_CONVERSION = 1.1;
	
		    String installDir     = TstConstants.SAMPLE_DIRECTORY;
		    String salesFile      = installDir + "DTAR020.bin";
		    String salesFileOut   = installDir + "DTAR020outG.bin";
		    String copybookName   = TstConstants.RE_XML_DIRECTORY
	    					+ "DTAR020.Xml";
	        int lineNum = 0;
	        AbstractFieldValue fldValue;

	        AbstractLine salesRecord;
	        
	        System.out.println("Output File: " + salesFileOut);
	        

	        try {
	        	IIOBuilder ioBldr = JRecordInterface1.SCHEMA_XML.newIOBuilder(copybookName);
	            
	            AbstractLineReader reader  = ioBldr.newReader(salesFile);
	            AbstractLineWriter writer  = ioBldr.newWriter(salesFileOut);

	            while ((salesRecord = reader.read()) != null) {
	                AbstractFieldValue keycode = salesRecord.getFieldValue("KEYCODE-NO");
	                AbstractFieldValue qtySold = salesRecord.getFieldValue("QTY-SOLD");
	                AbstractFieldValue salePrice = salesRecord.getFieldValue("SALE-PRICE");
	                lineNum += 1;

	                System.out.print(keycode.asString()
	                        + " " + qtySold.asString()
	                        + " " + salePrice.asString());

	                fldValue = salesRecord.getFieldValue("KEYCODE-NO");
	                if (fldValue.isNumeric()) {
	                	fldValue.set(0);
	                } else {
	                	fldValue.set("");
	                }
	                fldValue = salesRecord.getFieldValue("DATE");
	                if (fldValue.isNumeric()) {
	                	fldValue.set(0);
	                } else {
	                	fldValue.set("");
	                }
	                
	                salePrice.set(salePrice.asDouble() / GST_CONVERSION);
	                writer.write(salesRecord);

	                System.out.println(" " + salePrice.asString());
	            }

	            reader.close();
	            writer.close();
	        } catch (Exception e) {
	            System.out.println("~~> " + lineNum + " " + e.getMessage());
	            System.out.println();

	            e.printStackTrace();
	        }
	    }
	    


	    public static void main(String[] args) {
	    	new XmplLineIOG();
	    }
}

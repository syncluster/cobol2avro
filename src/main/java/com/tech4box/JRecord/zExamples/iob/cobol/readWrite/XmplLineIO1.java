/*
 * @Author Bruce Martin
 * Created on 9/09/2005
 *
 * Purpose:
 *   This Example program demonstrates Reading a file using Line Based
 * Routines
 *
 * Requirements:
 *
 * 1) Check the values in Constants.java are correct !!!
 *
 */
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
      
package com.tech4box.JRecord.zExamples.iob.cobol.readWrite;

import com.tech4box.JRecord.JRecordInterface1;
import com.tech4box.JRecord.Common.Constants;
import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.IO.AbstractLineReader;
import com.tech4box.JRecord.Numeric.ICopybookDialects;
import com.tech4box.JRecord.def.IO.builders.ICobolIOBuilder;
import com.tech4box.JRecord.zTest.Common.TstConstants;


/**
 * This Example program demonstrates Reading a file using Line Based
 * Routines
 *
 * @author Bruce Martin
 *
 */
public final class XmplLineIO1 {

    /**
     *
     */
    private XmplLineIO1() {
        super();

        String vendorFile          = TstConstants.SAMPLE_DIRECTORY
		                           + "Ams_VendorDownload_20041229.txt";
        String copybookName        = TstConstants.COBOL_DIRECTORY
		                           + "AmsVendor.cbl";
        
        ICobolIOBuilder ioBldr = JRecordInterface1.COBOL
        		.newIOBuilder(copybookName)
        			.setDialect( ICopybookDialects.FMT_INTEL)
        			.setFileOrganization(Constants.IO_TEXT_LINE);
        			
        //CobolIoProvider ioProvider = CobolIoProvider.getInstance();
        AbstractLine line;
        int lineNumber = 0;

        try {
        	AbstractLineReader reader  = ioBldr.newReader(vendorFile);
        			

            System.out.println("  Vendor \t Name");
            System.out.println("  ===========================================");


            while ((line = reader.read()) != null) {
                lineNumber += 1;
                    System.out.println(
                            "  "    + line.getFieldValue("Vendor-Number").asString()
                          + "  \t " + line.getFieldValue("Vendor-Name").asString());

            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error Line " + lineNumber + " " + e.getMessage());
            System.out.println("      File " + vendorFile);
            System.out.println();
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("Lines Read " + lineNumber);
    }


    /**
     * LineIO example
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        new XmplLineIO1();
    }
}

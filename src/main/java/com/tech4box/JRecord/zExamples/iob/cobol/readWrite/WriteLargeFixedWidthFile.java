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

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.tech4box.JRecord.JRecordInterface1;
import com.tech4box.JRecord.Common.Constants;
import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.IO.AbstractLineReader;
import com.tech4box.JRecord.IO.AbstractLineWriter;
import com.tech4box.JRecord.Numeric.ICopybookDialects;
import com.tech4box.JRecord.def.IO.builders.ICobolIOBuilder;
import com.tech4box.JRecord.zTest.Common.TstConstants;

/**
 * Read / Write Mainframe Cobol file using a Cobol Copybook
 * 
 * 
 * @author Bruce Martin
 *
 */
public final class WriteLargeFixedWidthFile {


    private String installDir     = TstConstants.SAMPLE_DIRECTORY;
    private String salesFile      = installDir + "DTAR020.bin";
    private String salesFileOut   = "F:\\Temp\\Large\\DTAR020_Large.cbl";
    private String copybookName   = TstConstants.COBOL_DIRECTORY + "DTAR020.cbl";

    /**
     * Example of LineReader / LineWrite classes
     */
    private WriteLargeFixedWidthFile() {
        super();

        AbstractLine saleRecord;
        List<AbstractLine> lines = new ArrayList<AbstractLine>(300);

        try {
        	ICobolIOBuilder iob = JRecordInterface1.COBOL
        			.newIOBuilder(copybookName)
        				.setDialect(ICopybookDialects.FMT_MAINFRAME)
        				.setFileOrganization(Constants.IO_FIXED_LENGTH);
           
            AbstractLineReader reader  = iob.newReader(salesFile);
            AbstractLineWriter writer  = iob.newWriter(new BufferedOutputStream(new FileOutputStream(salesFileOut), 256 * 256 * 64));

            while ((saleRecord = reader.read()) != null) {
            	lines.add(saleRecord);
            }
            reader.close();
            
            
            for (int i = 0; i < 300000000; i++) { 
                writer.write(lines.get(i % lines.size()));
                if (i % 1000000 == 0) {
                	System.out.print("\t" + (i /1000000));
                	 if (i % 10000000 == 0) System.out.println();
                }
            }

            
            writer.close();
        } catch (Exception e) {
            System.out.println();

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
    	new WriteLargeFixedWidthFile();
    }
}

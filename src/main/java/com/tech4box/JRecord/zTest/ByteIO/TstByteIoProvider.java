/*
 * @Author Bruce Martin
 * Created on 19/03/2007
 *
 * Purpose:
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

package com.tech4box.JRecord.zTest.ByteIO;

import junit.framework.TestCase;
import com.tech4box.JRecord.ByteIO.ByteIOProvider;
import com.tech4box.JRecord.ByteIO.FixedLengthByteReader;
import com.tech4box.JRecord.ByteIO.FixedLengthByteWriter;
import com.tech4box.JRecord.ByteIO.FujitsuVbByteReader;
import com.tech4box.JRecord.ByteIO.FujitsuVbByteWriter;
import com.tech4box.JRecord.ByteIO.VbByteReader;
import com.tech4box.JRecord.ByteIO.VbByteWriter;
import com.tech4box.JRecord.ByteIO.VbDumpByteReader;
import com.tech4box.JRecord.ByteIO.VbDumpByteWriter;
import com.tech4box.JRecord.Common.BasicFileSchema;
import com.tech4box.JRecord.Common.Constants;

/**
 *
 *
 * @author Bruce Martin
 *
 */
public class TstByteIoProvider extends TestCase {

    private int[] ioIds = {
            Constants.IO_FIXED_LENGTH,
            Constants.IO_VB,
            Constants.IO_VB_DUMP,
            Constants.IO_VB_FUJITSU,
    };
 	@SuppressWarnings("rawtypes")
	private Class[] classReaders = {
            FixedLengthByteReader.class,
            VbByteReader.class,
            VbDumpByteReader.class,
            FujitsuVbByteReader.class
    };

	@SuppressWarnings("rawtypes")
	private Class[] classWriters = {
            FixedLengthByteWriter.class,
            VbByteWriter.class,
            VbDumpByteWriter.class,
            FujitsuVbByteWriter.class
    };

    @SuppressWarnings("deprecation")
	public void testIoProviderReaders() {

        for (int i = 0; i < ioIds.length; i++) {
            System.out.println("--> " + i + " " + ioIds[i]  + " " + classReaders[i].getName());
            assertEquals("Error Reader " + ioIds[i],
                    classReaders[i],
                    ByteIOProvider.getInstance().getByteReader(ioIds[i]).getClass());
        }
    }


    public void testIoProviderWriters1() {

        System.out.println();
        for (int i = 1; i < ioIds.length; i++) {
            System.out.println("--> " + i + " " + ioIds[i] + " " + classWriters[i].getName());
            assertEquals("Error Writer " + ioIds[i],
                    classWriters[i],
                    ByteIOProvider.getInstance()
                    	.getByteWriter(ioIds[i])
                    		.getClass());
        }
    }

    

    public void testIoProviderWriters2() {

        System.out.println();
        for (int i = 0; i < ioIds.length; i++) {
            System.out.println("--> " + i + " " + ioIds[i] + " " + classWriters[i].getName());
            assertEquals("Error Writer " + ioIds[i],
                    classWriters[i],
                    ByteIOProvider.getInstance()
                    		.getByteWriter(
                    				BasicFileSchema.newFixedSchema(ioIds[i], false, 40, "")
                    		).getClass());
        }
    }

}

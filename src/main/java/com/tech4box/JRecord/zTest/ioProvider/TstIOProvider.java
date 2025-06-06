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

package com.tech4box.JRecord.zTest.ioProvider;

import com.tech4box.JRecord.ByteIO.ByteTextReader;
import com.tech4box.JRecord.ByteIO.ByteTextWriter;
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
import com.tech4box.JRecord.Common.Conversion;
import com.tech4box.JRecord.Details.DefaultLineProvider;
import com.tech4box.JRecord.IO.AbstractLineReader;
import com.tech4box.JRecord.IO.AbstractLineWriter;
import com.tech4box.JRecord.IO.BinTextReader;
import com.tech4box.JRecord.IO.BinTextWriter;
import com.tech4box.JRecord.IO.FixedLengthWriter;
import com.tech4box.JRecord.IO.LineIOProvider;
import com.tech4box.JRecord.IO.LineReaderWrapper;
import com.tech4box.JRecord.IO.LineWriterWrapper;
import junit.framework.TestCase;


/**
 * Check the Line Readers / Writers are using the
 * correct Byte Reader / Writers
 * 
 * @author Bruce Martin
 *
 */
public class TstIOProvider extends TestCase {

	private static RWDetails[] READER_WRAPPER_DETAILS1 = {
		new RWDetails(Constants.IO_FIXED_LENGTH , LineReaderWrapper.class, FixedLengthByteReader.class),       
		new RWDetails(Constants.IO_VB           , LineReaderWrapper.class, VbByteReader.class),       
		new RWDetails(Constants.IO_VB_DUMP      , LineReaderWrapper.class, VbDumpByteReader.class),       
		new RWDetails(Constants.IO_VB_FUJITSU   , LineReaderWrapper.class, FujitsuVbByteReader.class),       
		new RWDetails(Constants.IO_VB_GNU_COBOL, LineReaderWrapper.class, VbByteReader.class),       
		new RWDetails(Constants.IO_BIN_TEXT     , BinTextReader.class,     ByteTextReader.class),
	};
	
	private static RWDetails[] READER_WRAPPER_DETAILS2 = {
		new RWDetails(Constants.IO_FIXED_LENGTH , LineReaderWrapper.class, FixedLengthByteReader.class),       
		new RWDetails(Constants.IO_VB           , LineReaderWrapper.class, VbByteReader.class),       
		new RWDetails(Constants.IO_VB_DUMP      , LineReaderWrapper.class, VbDumpByteReader.class),       
		new RWDetails(Constants.IO_VB_FUJITSU   , LineReaderWrapper.class, FujitsuVbByteReader.class),       
		new RWDetails(Constants.IO_VB_GNU_COBOL, LineReaderWrapper.class, VbByteReader.class),       
		new RWDetails(Constants.IO_BIN_TEXT     , LineReaderWrapper.class, ByteTextReader.class),
	};

	private static RWDetails[] WRITER_WRAPPER_DETAILS1 = {
		new RWDetails(Constants.IO_FIXED_LENGTH , FixedLengthWriter.class, FixedLengthByteWriter.class),       
		new RWDetails(Constants.IO_VB           , LineWriterWrapper.class, VbByteWriter.class),       
		new RWDetails(Constants.IO_VB_DUMP      , LineWriterWrapper.class, VbDumpByteWriter.class),       
		new RWDetails(Constants.IO_VB_FUJITSU   , LineWriterWrapper.class, FujitsuVbByteWriter.class),       
		new RWDetails(Constants.IO_VB_GNU_COBOL, LineWriterWrapper.class, VbByteWriter.class),       
		new RWDetails(Constants.IO_BIN_TEXT     , BinTextWriter.class, ByteTextWriter.class),
	};

	
	@SuppressWarnings("deprecation")
	public void testGetReader1() {
		LineIOProvider iop = LineIOProvider.getInstance();
		
		for (RWDetails rd : READER_WRAPPER_DETAILS1) {
			check("1: ", rd, iop.getLineReader(rd.fileOrg));
		}
	}
	
	public void testGetReader2() {
		LineIOProvider iop = LineIOProvider.getInstance();
		
		for (RWDetails rd : READER_WRAPPER_DETAILS2) {
			check("2: ", rd, iop.getLineReader(BasicFileSchema.newFixedSchema(rd.fileOrg, false, 121, Conversion.getDefaultSingleByteCharacterset())));
			
			check("3: ", rd, iop.getLineReader(BasicFileSchema.newFixedSchema(rd.fileOrg, true, 121, "cp037")));

		}
	}
	
	@SuppressWarnings("deprecation")
	public void testGetWriter1() {
		LineIOProvider iop = LineIOProvider.getInstance();
		
		for (RWDetails rd : WRITER_WRAPPER_DETAILS1) {
			check("1: ", rd, iop.getLineWriter(rd.fileOrg));
		}
	}

	public void testGetWriter2() {
		LineIOProvider iop = LineIOProvider.getInstance();
		
		for (RWDetails rd : WRITER_WRAPPER_DETAILS1) {
			check("2: ", rd, iop.getLineWriter(BasicFileSchema.newFixedSchema(rd.fileOrg, true, 121, "cp037")));
		}
	}
	
	private void check(String id, RWDetails rd, AbstractLineReader lineReader) {
		
		String msg = id + rd.lineClass.getClass().getName();
		chkClass(msg, rd.lineClass, lineReader.getClass());
		chkClass(msg, rd.byteClass, ((LineReaderWrapper)lineReader).getReader().getClass());
		chkClass(msg, DefaultLineProvider.class, ((LineReaderWrapper)lineReader).getLineProvider().getClass());
	}
	
	private void check(String id, RWDetails rd, AbstractLineWriter lineWriter) {
		
		String msg = id + rd.lineClass.getClass().getName();
		chkClass(msg, rd.lineClass, lineWriter.getClass());
		if (lineWriter.getClass() == LineWriterWrapper.class) {
			chkClass(msg, rd.byteClass, ((LineWriterWrapper)lineWriter).getWriter().getClass());
		}
	}

	@SuppressWarnings("rawtypes")
	private void chkClass(String msg, Class class1, Class class2) {
		assertEquals(msg, class1, class2);

	}
	
	private static class RWDetails {
		
		final int fileOrg;
		@SuppressWarnings("rawtypes")
		final Class lineClass, byteClass;
		
		@SuppressWarnings("rawtypes")
		protected RWDetails(int fileOrg, Class lineClass, Class byteClass) {
			super();
			this.fileOrg = fileOrg;
			this.lineClass = lineClass;
			this.byteClass = byteClass;
		}
		
		
		
	}
}

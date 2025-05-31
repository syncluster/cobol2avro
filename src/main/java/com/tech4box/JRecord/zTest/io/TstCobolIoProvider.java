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

package com.tech4box.JRecord.zTest.io;

import junit.framework.TestCase;
import com.tech4box.JRecord.ByteIO.FixedLengthByteReader;
import com.tech4box.JRecord.ByteIO.FujitsuVbByteReader;
import com.tech4box.JRecord.ByteIO.VbByteReader;
import com.tech4box.JRecord.ByteIO.VbByteWriter;
import com.tech4box.JRecord.ByteIO.VbDumpByteWriter;
import com.tech4box.JRecord.Common.BasicFileSchema;
import com.tech4box.JRecord.Common.Constants;
import com.tech4box.JRecord.External.CopybookLoader;
import com.tech4box.JRecord.IO.AbstractLineReader;
import com.tech4box.JRecord.IO.AbstractLineWriter;
import com.tech4box.JRecord.IO.CobolIoProvider;
import com.tech4box.JRecord.IO.FixedLengthWriter;
import com.tech4box.JRecord.IO.LineReaderWrapper;
import com.tech4box.JRecord.IO.LineWriterWrapper;
import com.tech4box.JRecord.IO.TextLineWriter;
import com.tech4box.JRecord.Numeric.ICopybookDialects;
import com.tech4box.JRecord.zTest.Common.TstConstants;
import com.tech4box.cb2xml.def.Cb2xmlConstants;

/**
 * Testing the correct LineReader's and LineWriter's are returned by
 * the CobolIoProvider class
 * 
 * @author Bruce Martin
 *
 */
public class TstCobolIoProvider extends TestCase {

	private static String copybookName = TstConstants.COBOL_DIRECTORY + "DTAR020.cbl";
	private static final String filename = TstConstants.SAMPLE_DIRECTORY + "DTAR020.bin";
	private static final String outfilename = TstConstants.TEMP_DIRECTORY + "DTAR020.ttt.bin";

	/**
	 * Test GetLineReaders (standard structure
	 * 
	 * @throws Exception
	 */
	public void testGetReader1() throws Exception {
		CobolIoProvider ioProvider = CobolIoProvider.getInstance();
		
		AbstractLineReader lineReader;
		lineReader = ioProvider.getLineReader(Constants.IO_FIXED_LENGTH, ICopybookDialects.FMT_MAINFRAME, CopybookLoader.SPLIT_NONE, copybookName, filename);
		
		System.out.println(lineReader.getClass().getName());
		System.out.println(((LineReaderWrapper) lineReader).getReader().getClass().getName());
		assertTrue(((LineReaderWrapper) lineReader).getReader() instanceof FixedLengthByteReader);
		lineReader.close();
		
		lineReader = ioProvider.getLineReader(Constants.IO_VB, ICopybookDialects.FMT_MAINFRAME, CopybookLoader.SPLIT_NONE, copybookName, filename);

		System.out.println(lineReader.getClass().getName());
		System.out.println(((LineReaderWrapper) lineReader).getReader().getClass().getName());
		assertTrue(((LineReaderWrapper) lineReader).getReader() instanceof VbByteReader);
		lineReader.close();
		
		lineReader = ioProvider.getLineReader(Constants.IO_VB_GNU_COBOL, ICopybookDialects.FMT_MAINFRAME, CopybookLoader.SPLIT_NONE, copybookName, filename);

		System.out.println(lineReader.getClass().getName());
		System.out.println(((LineReaderWrapper) lineReader).getReader().getClass().getName());
		assertTrue(((LineReaderWrapper) lineReader).getReader() instanceof VbByteReader);
		lineReader.close();
		
		lineReader = ioProvider.getLineReader(Constants.IO_VB_FUJITSU, ICopybookDialects.FMT_MAINFRAME, CopybookLoader.SPLIT_NONE, copybookName, filename);

		System.out.println(lineReader.getClass().getName());
		System.out.println(((LineReaderWrapper) lineReader).getReader().getClass().getName());
		assertTrue(((LineReaderWrapper) lineReader).getReader() instanceof FujitsuVbByteReader);
		lineReader.close();
	}
	

	/**
	 * Test getLineReader where (specifying a copybook structure
	 * @throws Exception
	 */
	public void testGetReader2() throws Exception {
		CobolIoProvider ioProvider = CobolIoProvider.getInstance();
		
		AbstractLineReader lineReader;
		lineReader = ioProvider.getLineReader(Constants.IO_FIXED_LENGTH, ICopybookDialects.FMT_MAINFRAME, CopybookLoader.SPLIT_NONE, Cb2xmlConstants.USE_STANDARD_COLUMNS, copybookName, filename);
		
		System.out.println(lineReader.getClass().getName());
		System.out.println(((LineReaderWrapper) lineReader).getReader().getClass().getName());
		assertTrue(((LineReaderWrapper) lineReader).getReader() instanceof FixedLengthByteReader);
		lineReader.close();
		
		lineReader = ioProvider.getLineReader(Constants.IO_VB, ICopybookDialects.FMT_MAINFRAME, CopybookLoader.SPLIT_NONE, Cb2xmlConstants.USE_STANDARD_COLUMNS, copybookName, filename);

		System.out.println(lineReader.getClass().getName());
		System.out.println(((LineReaderWrapper) lineReader).getReader().getClass().getName());
		assertTrue(((LineReaderWrapper) lineReader).getReader() instanceof VbByteReader);
		lineReader.close();
		
		lineReader = ioProvider.getLineReader(Constants.IO_VB_GNU_COBOL, ICopybookDialects.FMT_MAINFRAME, CopybookLoader.SPLIT_NONE, Cb2xmlConstants.USE_STANDARD_COLUMNS, copybookName, filename);

		System.out.println(lineReader.getClass().getName());
		System.out.println(((LineReaderWrapper) lineReader).getReader().getClass().getName());
		assertTrue(((LineReaderWrapper) lineReader).getReader() instanceof VbByteReader);
		lineReader.close();
		
		lineReader = ioProvider.getLineReader(Constants.IO_VB_FUJITSU, ICopybookDialects.FMT_MAINFRAME, CopybookLoader.SPLIT_NONE, Cb2xmlConstants.USE_STANDARD_COLUMNS, copybookName, filename);

		System.out.println(lineReader.getClass().getName());
		System.out.println(((LineReaderWrapper) lineReader).getReader().getClass().getName());
		assertTrue(((LineReaderWrapper) lineReader).getReader() instanceof FujitsuVbByteReader);
		lineReader.close();
	}

	/**
	 * Test getLineWriter using just the FileStructure
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public void testGetWriter1() throws Exception {
		CobolIoProvider ioProvider = CobolIoProvider.getInstance();
		
		AbstractLineWriter lineWriter;
		lineWriter = ioProvider.getLineWriter(Constants.IO_FIXED_LENGTH, outfilename);
		System.out.println(lineWriter.getClass().getName());
		assertTrue(lineWriter instanceof FixedLengthWriter);
		lineWriter.close();
		
		lineWriter = ioProvider.getLineWriter(Constants.IO_VB, outfilename);
		System.out.println(lineWriter.getClass().getName());
		System.out.println(((LineWriterWrapper)lineWriter).getWriter().getClass().getName());
		assertTrue(((LineWriterWrapper)lineWriter).getWriter() instanceof VbByteWriter);
		lineWriter.close();
		
		
		lineWriter = ioProvider.getLineWriter(Constants.IO_VB_DUMP, outfilename);
		System.out.println(lineWriter.getClass().getName());
		System.out.println(((LineWriterWrapper)lineWriter).getWriter().getClass().getName());
		assertTrue(((LineWriterWrapper)lineWriter).getWriter() instanceof VbDumpByteWriter);
		lineWriter.close();
		
		lineWriter = ioProvider.getLineWriter(Constants.IO_TEXT_LINE, outfilename);
		System.out.println(lineWriter.getClass().getName());
		assertTrue(lineWriter instanceof TextLineWriter);
		lineWriter.close();
		
		lineWriter = ioProvider.getLineWriter(Constants.IO_UNICODE_TEXT, outfilename);
		System.out.println(lineWriter.getClass().getName());
		assertTrue(lineWriter instanceof TextLineWriter);
		lineWriter.close();
	}
	
	/**
	 * Test getLineWriter using a "FileSchema"
	 * @throws Exception
	 */
	public void testGetWriter2() throws Exception {
		CobolIoProvider ioProvider = CobolIoProvider.getInstance();
		
		AbstractLineWriter lineWriter;
		lineWriter = ioProvider.getLineWriter(BasicFileSchema.newFixedSchema(Constants.IO_FIXED_LENGTH), outfilename);
		System.out.println(lineWriter.getClass().getName());
		assertTrue(lineWriter instanceof FixedLengthWriter);
		lineWriter.close();
		
		lineWriter = ioProvider.getLineWriter(BasicFileSchema.newFixedSchema(Constants.IO_VB), outfilename);
		System.out.println(lineWriter.getClass().getName());
		System.out.println(((LineWriterWrapper)lineWriter).getWriter().getClass().getName());
		assertTrue(((LineWriterWrapper)lineWriter).getWriter() instanceof VbByteWriter);
		lineWriter.close();
		
		
		lineWriter = ioProvider.getLineWriter(BasicFileSchema.newFixedSchema(Constants.IO_VB_DUMP), outfilename);
		System.out.println(lineWriter.getClass().getName());
		System.out.println(((LineWriterWrapper)lineWriter).getWriter().getClass().getName());
		assertTrue(((LineWriterWrapper)lineWriter).getWriter() instanceof VbDumpByteWriter);
		lineWriter.close();
		
		lineWriter = ioProvider.getLineWriter(BasicFileSchema.newFixedSchema(Constants.IO_TEXT_LINE), outfilename);
		System.out.println(lineWriter.getClass().getName());
		assertTrue(lineWriter instanceof TextLineWriter);
		lineWriter.close();
		
		lineWriter = ioProvider.getLineWriter(BasicFileSchema.newFixedSchema(Constants.IO_UNICODE_TEXT), outfilename);
		System.out.println(lineWriter.getClass().getName());
		assertTrue(lineWriter instanceof TextLineWriter);
		lineWriter.close();
	}

}

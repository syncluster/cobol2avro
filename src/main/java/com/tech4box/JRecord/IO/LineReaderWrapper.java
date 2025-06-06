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

package com.tech4box.JRecord.IO;

import java.io.IOException;
import java.io.InputStream;

//import com.tech4box.JRecord.ByteIO.AbstractByteReader;
import com.tech4box.JRecord.ByteIO.IByteReader;
import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.Details.LineProvider;

/**
 * Creates a LineReader from a Byte-Reader (ByteIO package).
 * A Byte-Reader reads Lines from a File as a series of Bytes.
 * There a Byte Readers for <ol compact>
*   <li>Fixed Line Length files
 *   <li>Length Based lines
 * </ol>
 *
 * @author Bruce Martin
 *
 */
public class LineReaderWrapper extends AbstractLineReader {

    private IByteReader reader;
    int i = 0;

    /**
     *  Create a LineReader from a Byte reader
     */
    public LineReaderWrapper(IByteReader byteReader) {
        super();

        reader = byteReader;
    }

    /**
     * @param provider
     */
    public LineReaderWrapper(LineProvider provider, IByteReader byteReader) {
        super(provider);

        reader = byteReader;
    }

    /**
     * @see AbstractLineReader#open(InputStream, LayoutDetail)
     */
    public void open(InputStream inputStream, LayoutDetail pLayout)
            throws IOException {

        reader.setLineLength(pLayout.getMaximumRecordLength());
        reader.open(inputStream);
        super.setLayout(pLayout);
    }


    /**
     * @see AbstractLineReader#read()
     */
    public AbstractLine readImplementation() throws IOException {
        byte bytes[] = reader.read();

        if (bytes == null) {
            return null;
        }
        return getLine(bytes);
    }

    protected byte[] rawRead() throws IOException {
    	return reader.read();
    }
    /**
     * @see AbstractLineReader#close()
     */
    public void close() throws IOException {
        reader.close();
    }

	/**
	 * @return the reader
	 */
	public final IByteReader getReader() {
		return reader;
	}

	/**
	 * @param reader the reader to set
	 */
	public final void setReader(IByteReader reader) {
		this.reader = reader;
	}

}

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

import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.Details.LineProvider;
import com.tech4box.JRecord.charIO.FixedLengthCharReader;

/**
 * Fixed Length Character file line reader.
 * A reader for a file where all the records are the same length
 * 
 * @author Bruce Martin
 *
 */
public class FixedLengthTextReader extends BasicTextLineReader {

	public FixedLengthTextReader(LineProvider provider) {
		super(provider);
	}


    /**
     * @see AbstractLineReader#open(InputStream, LayoutDetail)
     */
    public void open(InputStream inputStream, LayoutDetail layout)
    throws IOException {
    	String font = "";
		if (layout != null) {
			font = layout.getFontName();
		}
    	
    	super.open(new FixedLengthCharReader(layout.getMaximumRecordLength()), inputStream, layout, font);
    }
}

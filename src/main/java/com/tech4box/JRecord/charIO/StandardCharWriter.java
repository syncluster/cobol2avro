/*  -------------------------------------------------------------------------
 *
 *            Sub-Project: JRecord Common
 *    
 *    Sub-Project purpose: Common Low-Level Code shared between 
 *                        the JRecord and Record Projects
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
      
package com.tech4box.JRecord.charIO;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class StandardCharWriter extends BaseCharWriter {

	private final String eol; 
	private final String font;
	
	public StandardCharWriter(String eol, String charset) {
		super();
		this.eol = eol;
		this.font = charset;
	}

	@Override
	public void open(OutputStream outputStream) throws IOException {
		if (font == null || font.length() == 0) {
			w = new OutputStreamWriter(outputStream);
		} else {
			w = new OutputStreamWriter(outputStream, font);
		}
	}

	@Override
	public void write(char[] line) throws IOException {

		w.write(line);
		w.write(eol);
	}
}

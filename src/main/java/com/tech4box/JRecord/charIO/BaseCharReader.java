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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class BaseCharReader implements ICharReader {

	protected BufferedReader reader;


	@Override
	public void open(String fileName, String font) throws IOException {
	    open(new FileInputStream(fileName), font);
	}

	@Override
	public void open(InputStream inputStream, String font) throws IOException {
		InputStreamReader stdReader;
		if (font == null || font.length() == 0) {
		    stdReader = new InputStreamReader(inputStream);
		} else {
		    try {
		        stdReader = new InputStreamReader(inputStream, font);
		    } catch (Exception e) {
		        stdReader = new InputStreamReader(inputStream);
		    }
		}
	
		reader = new BufferedReader(stdReader);
	
	}

	@Override
	public void close() throws IOException {
		reader.close();
	}

}
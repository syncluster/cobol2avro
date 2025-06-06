/*
 * Created on 8/01/2005
 *
 */
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
      
package com.tech4box.JRecord.Log;

/**
 * A do nothing Log
 *
 * @author Bruce Martin
 *
 */
public class NullLog implements AbsSSLogger {

	/**
	 * @see com.tech4box.JRecord.Log#setReportLevel(int)
	 */
	public void setReportLevel(int level) {
	}

	/**
	 * @see com.tech4box.JRecord.Log#logException(int, Exception)
	 */
	public void logException(int level, Exception ex) {
	}
	
	/**
	 * @see com.tech4box.JRecord.Log#logMsg(int, String)
	 */
	public void logMsg(int level, String msg) {
	}
	
	public static AbsSSLogger getLog(AbsSSLogger log) {
		if (log == null) {
			log = new NullLog();
		}
		return log;
	}
}
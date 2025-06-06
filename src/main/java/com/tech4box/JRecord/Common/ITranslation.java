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
      
package com.tech4box.JRecord.Common;


/**
 * Purpose: provide basic Language Translation services
 *
 * @author Bruce Martin
 *
 */
public interface ITranslation {

	public static final int ST_MESSAGE = 2;
	public static final int ST_ERROR   = 14;

	public String convert(int type, String s);
	public String convert(String s);
	public String convert(String s, String defaultStr);
	public abstract String convertMsg(int type, String s, Object... params);
	public abstract String convert(int type, String s, String param);
}

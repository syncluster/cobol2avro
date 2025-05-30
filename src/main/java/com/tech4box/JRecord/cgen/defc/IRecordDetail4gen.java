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
      
package com.tech4box.JRecord.cgen.defc;

import com.tech4box.JRecord.Common.FieldDetail;
import com.tech4box.JRecord.Option.IRecordPositionOption;
import com.tech4box.JRecord.definitiuons.CsvCharDetails;
import com.tech4box.JRecord.detailsSelection.RecordSelection;

public interface IRecordDetail4gen {

	public abstract int getFieldCount();
	
	public abstract FieldDetail getField(int idx);
	
	public abstract String getRecordName();
	
	public abstract RecordSelection getRecordSelection();
	
	public abstract IRecordPositionOption getRecordPositionOption();
	
	public int getRecordType();

	public abstract CsvCharDetails getQuoteDefinition();
}

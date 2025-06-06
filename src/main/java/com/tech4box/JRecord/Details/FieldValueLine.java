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

package com.tech4box.JRecord.Details;

import com.tech4box.JRecord.Common.IFieldDetail;

public class FieldValueLine extends FieldValue {

	private final Line theLine; 

	protected FieldValueLine(Line line, IFieldDetail fieldDetails) {
		super(line, fieldDetails);
		theLine = line;
	}

	protected FieldValueLine(Line line, int recordIndex, int fieldIndex) {
		super(line, line.getLayout().getField(recordIndex, fieldIndex));
		theLine = line;
	}

	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.Details.FieldValue#isLowValues()
	 */
	@Override
	public boolean isLowValues() {
		return checkFor((byte) 0);
	}

	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.Details.FieldValue#isHighValues()
	 */
	@Override
	public boolean isHighValues() {
		return checkFor((byte) -1);
	}
	
	
	
	@Override
	public boolean isSpaces() {
		return checkFor(theLine.getLayout().getSpaceByte());
	}

	private boolean checkFor(byte b) {
		byte[] bytes = theLine.getFieldBytes(field);
		
		if (bytes == null) {
			return b == 0;
		} 
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] != b) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * @see FieldValue#setHex(String)
	 */
	@Override
	public void setHex(String val) {
		theLine.setFieldHex(field, val); 
	}

	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.Details.FieldValue#setToLowValues()
	 */
	@Override
	public void setToLowValues() {
		theLine.setFieldToByte(field, (byte) 0);
	}

	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.Details.FieldValue#setToHighValues()
	 */
	@Override
	public void setToHighValues() {
		theLine.setFieldToByte(field, (byte) 0xFF);
	}



}

package com.tech4box.JRecord.schema.jaxb.impl;

import com.tech4box.JRecord.Common.IFieldDetail;
import com.tech4box.JRecord.Types.TypeManager;
import com.tech4box.JRecord.schema.jaxb.IItem;
import com.tech4box.JRecord.schema.jaxb.interfaces.IFormatField;

public class AddPlusToNumeric implements IFormatField {
	
	public static final AddPlusToNumeric INSTANCE = new AddPlusToNumeric();

	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.interfaces.IFormatField#format(com.tech4box.JRecord.schema.jaxb.IItem, com.tech4box.JRecord.Common.IFieldDetail, java.lang.String)
	 */
	@Override
	public String format(IItem itemDef, IFieldDetail fieldDef, String value) {
		if (TypeManager.isNumeric(fieldDef.getType()) 
		&&  TypeManager.isSignLeading(fieldDef.getType()) 
		&&  value != null && value.length() > 0 
		&& (value.charAt(0) != '-') && (value.charAt(0) != '+')) {
			value = '+' + value.trim();
		}
		return value;
	}

	
}

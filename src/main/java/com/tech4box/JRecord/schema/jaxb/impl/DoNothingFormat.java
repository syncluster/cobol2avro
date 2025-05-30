package com.tech4box.JRecord.schema.jaxb.impl;

import com.tech4box.JRecord.Common.IFieldDetail;
import com.tech4box.JRecord.schema.jaxb.IItem;
import com.tech4box.JRecord.schema.jaxb.interfaces.IFormatField;

public class DoNothingFormat implements IFormatField {
	public static final DoNothingFormat INSTANCE = new DoNothingFormat();
	
	@Override
	public String format(IItem itemDef, IFieldDetail fieldDef, String value) {
		return value;
	}

}

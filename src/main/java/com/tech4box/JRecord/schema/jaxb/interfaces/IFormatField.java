package com.tech4box.JRecord.schema.jaxb.interfaces;

import com.tech4box.JRecord.Common.IFieldDetail;
import com.tech4box.JRecord.schema.jaxb.IItem;

public interface IFormatField {
	public String format(IItem itemDef, IFieldDetail fieldDef, String value);
}

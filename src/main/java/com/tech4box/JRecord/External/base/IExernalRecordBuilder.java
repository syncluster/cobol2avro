package com.tech4box.JRecord.External.base;

public interface IExernalRecordBuilder<XRecord extends BaseExternalRecord<XRecord>> {

	public XRecord getNullRecord(
				final String pRecordName,
				final int recordType,
				final String fontName);

}

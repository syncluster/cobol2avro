package com.tech4box.JRecord.External;

import com.tech4box.JRecord.External.base.IExernalRecordBuilder;

public class ExternalRecordBuilder implements IExernalRecordBuilder<ExternalRecord> {

	@Override
	public ExternalRecord getNullRecord(String pRecordName, int recordType, String fontName) {
		return ExternalRecord.getNullRecord(pRecordName, recordType, fontName);
	}

}

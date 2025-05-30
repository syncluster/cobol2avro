package com.tech4box.JRecord.External.Def;

import java.util.List;

import com.tech4box.JRecord.Common.AbstractRecordX;
import com.tech4box.JRecord.Common.IFieldDetail;

public interface IDependingOnIndexDtls {

	DependingOn getDependingOn();

	int getIndex();

	void updateFieldInChildren(AbstractRecordX<? extends IFieldDetail> rec);

	List<DependingOn> getChildren();

}
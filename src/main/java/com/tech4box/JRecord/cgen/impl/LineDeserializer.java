package com.tech4box.JRecord.cgen.impl;

import com.tech4box.JRecord.cgen.def.IAsPojoSetData;
import com.tech4box.JRecord.cgen.def.IDeserializer;

public class LineDeserializer<Line> implements IDeserializer<Line> {

	public static <Line> LineDeserializer<Line> create(IAsPojoSetData<Line> line)  {
		return new LineDeserializer<Line>(line);
	}
	private final IAsPojoSetData<Line> line;
	
	
	protected LineDeserializer(IAsPojoSetData<Line> line) {
		super();
		this.line = line;
	}


	@Override
	public Line deserialize(byte[] rec) {
		line.setData(rec);
		return line.asPojo();
	}

}

package com.tech4box.JRecord.cgen.impl;

import com.tech4box.JRecord.Common.IGetData;
import com.tech4box.JRecord.cgen.def.ILineToBytes;
import com.tech4box.JRecord.cgen.def.ISerializer;

public class LineSerializer<Line> implements ISerializer<Line> {
	
	public static <Line> LineSerializer<Line> create(ILineToBytes<Line> jrLine) {
		return new LineSerializer<Line>(jrLine);
	}
	
	private final ILineToBytes<Line> jrLine;
	
	protected LineSerializer(ILineToBytes<Line> jrLine) {
		super();
		this.jrLine = jrLine;
	}

	@Override	 
	public byte[] serialize(Line rec) {
	        
        if (rec instanceof IGetData) {
            return ((IGetData) rec).getData();
        }
        
        jrLine.set(rec);
        return jrLine.getData();
    }
}

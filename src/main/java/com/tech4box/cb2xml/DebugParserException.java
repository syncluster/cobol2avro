package com.tech4box.cb2xml;

import com.tech4box.cb2xml.sablecc.parser.ParserException;

@SuppressWarnings("serial")
public class DebugParserException extends ParserException {

	public final String buffer;
	
	DebugParserException(ParserException oe, String buffer) {
		super(oe.getToken(), oe.getMessage());

		this.buffer = buffer;
		super.setStackTrace(oe.getStackTrace());
	}

}

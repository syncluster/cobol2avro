package com.tech4box.JRecord.zTest.Cobol.iobuilder;

import java.io.IOException;

import com.tech4box.JRecord.JRecordInterface1;
import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.def.IO.builders.ICobolIOBuilder;
import com.tech4box.JRecord.zTest.Common.TstConstants;
import junit.framework.TestCase;

/**
 * Testing values are assigned correctly to Right justified fields
 * 
 * @author Bruce Martin
 *
 */
public class TstRighJustified extends TestCase {
    private static final String RIGHTJUST_COPYBOOK = TstConstants.COBOL_DIRECTORY + "RightJust.cbl";

    public void testRightJustified() throws IOException {
    	String str = "abcdefghij";
    	ICobolIOBuilder iob = JRecordInterface1.COBOL.newIOBuilder(RIGHTJUST_COPYBOOK);
    	AbstractLine line = iob.newLine();
    	
    	for (int i = 0; i <= str.length(); i++) {
    		String value = str.substring(i);
    		line.getFieldValue(0, 0).set(value);
    		line.getFieldValue(0, 1).set(value);
    		line.getFieldValue(0, 2).set(value);
    		String lineTxt = line.getFullLine();
    		checkRighJust( lineTxt.substring(0, 20), value );
    		checkRighJust( lineTxt.substring(20, 40), value);
    		assertEquals(value, lineTxt.substring(40).trim());
    		
    		checkRighJust( line.getFieldValue(0, 0).asString(), value );
    		checkRighJust( line.getFieldValue(0, 1).asString(), value );
       	 	assertEquals(value, line.getFieldValue(0, 2).asString());
    	}
    }
    
    private void checkRighJust(String fldValue, String value) {
    	if (value.length() > 0) {
    		assertEquals(20, fldValue.length());
    	}
    	assertEquals(value, fldValue.trim());
    	assertTrue(fldValue.endsWith(value));
    }
}

package com.tech4box.JRecord.zTest.Details;

import java.io.IOException;
import java.io.StringReader;

import junit.framework.TestCase;
import com.tech4box.JRecord.JRecordInterface1;
import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.Details.CharLine;
import com.tech4box.JRecord.Details.IFieldValue;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.Types.Type;
import com.tech4box.JRecord.def.IO.builders.ICobolIOBuilder;
import com.tech4box.JRecord.def.IO.builders.ICsvIOBuilder;


/**
 * Test getFieldValue for CharLine and CsvLine
 * 
 * @author Bruce Martin
 *
 */
public class TestFieldValue extends TestCase {
	
	private static final String FIELD1 = "field1";


	public static final String SPACES = "           "; 
	

	public static final int SPACES_TST = 3;
	public static final int OTHER_TST = 4;
	
	public static String CBL
	     = "      01  CBL-CPY.\n"
	     + "          03  " + FIELD1 +"   pic s9(3)";
	
	public static String[] DECIMAL_OPTS = {
		"", "V9", "V99" 	
	};
	
	
	
	/**
	 * Check the isPresent, isSpaces, isLowValues, isHighValues 
	 * for a Cobol CharLine (String line)
	 * 
	 * @throws IOException
	 */
	public void testCobolChecks() throws IOException {
		for (String decimal : DECIMAL_OPTS) {
			doCobolTests(CBL + decimal+  ".\n");
		}
	}
	
	
	private void doCobolTests(String cbl) throws IOException {
		//System.out.println(cbl);
		ICobolIOBuilder iob = JRecordInterface1.COBOL
									.newIOBuilder(new StringReader(cbl), "CBL-CPY")
									.setFont("cp037");
		LayoutDetail layout = iob.getLayout();
		AbstractLine line = iob.newLine();	
		assertTrue(! line.getFieldValue(FIELD1).isFieldPresent());
		
		chkFieldValue(new CharLine(layout, SPACES), SPACES_TST, false);
		chkFieldValue(new CharLine(layout, "123456789"), OTHER_TST, true);
		
		System.out.println();
	}


	private void chkFieldValue(AbstractLine line, int tstId, boolean isPresent) {
		IFieldValue fieldValue = line.getFieldValue(FIELD1);
		assertTrue((isPresent) == fieldValue.isFieldPresent());
		assertTrue(false == fieldValue.isLowValues());
		assertTrue(false ==  fieldValue.isHighValues());
		assertTrue((tstId == SPACES_TST) ==  fieldValue.isSpaces());
	}
	
	/**
	 * Check the isPresent, isSpaces, isLowValues, isHighValues 
	 * for a CsvLine 
	 * 
	 * @throws IOException
	 */
	public void testCsvLine() throws IOException {
		ICsvIOBuilder iob = JRecordInterface1.CSV.newIOBuilder(",", "\'")
				.defineFields()
					.addCsvField(FIELD1, Type.ftChar, 0)
					.addCsvField("field2", Type.ftChar, 0)
				.endOfRecord();
		
		assertTrue(! iob.newLine().getFieldValue(FIELD1).isFieldPresent());
		assertTrue(! createLine(iob, "").getFieldValue("field2").isFieldPresent());
		assertTrue(createLine(iob, ",234").getFieldValue(FIELD1).isFieldPresent());
		chkFieldValue(createLine(iob, " ,234"), SPACES_TST, true);
		chkFieldValue(createLine(iob, "1,234"), OTHER_TST, true);
		
		
	}
	
	private AbstractLine createLine(ICsvIOBuilder iob, String data) throws IOException {
		AbstractLine line = iob.newLine();
		
		line.setData(data);
		
		return line;
	}
	
}

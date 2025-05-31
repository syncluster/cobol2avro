package com.tech4box.JRecord.zTest.Cobol.occursDependingOn;

import java.io.IOException;

import junit.framework.TestCase;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.Details.RecordDetail;
import com.tech4box.JRecord.IO.CobolIoProvider;
import com.tech4box.JRecord.Numeric.ICopybookDialects;
import com.tech4box.JRecord.def.IO.builders.ICobolIOBuilder;

public class TstOccursDepending51 extends TestCase {

	public void test01() throws IOException {
		String copybookFileName = WriteSampleFile.class.getResource("OccursDependingOn51.cbl").getFile();
		ICobolIOBuilder ioBuilder = CobolIoProvider.getInstance()
				.newIOBuilder(copybookFileName, ICopybookDialects.FMT_MAINFRAME);
		LayoutDetail l = ioBuilder.getLayout();
		RecordDetail record = l.getRecord(0);
		
		assertTrue(record.getDependingOnLevel() + "", RecordDetail.DO_COMPLEX <= record.getDependingOnLevel());
		System.out.println(record.getDependingOnLevel());
	}
	
	public void test02() throws IOException {
		String copybookFileName = WriteSampleFile.class.getResource("OccursDependingOn52.cbl").getFile();
		ICobolIOBuilder ioBuilder = CobolIoProvider.getInstance()
				.newIOBuilder(copybookFileName, ICopybookDialects.FMT_MAINFRAME);
		LayoutDetail l = ioBuilder.getLayout();
		RecordDetail record = l.getRecord(0);
		
		assertEquals(RecordDetail.DO_COMPLEX_SIZE_IN_ARRAY, record.getDependingOnLevel());
		System.out.println(record.getDependingOnLevel());
	}

}

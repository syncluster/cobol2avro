package com.tech4box.JRecord.zTest.Cobol.occursDependingOn;

import java.io.IOException;

import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.IO.CobolIoProvider;
import com.tech4box.JRecord.Numeric.ICopybookDialects;
import com.tech4box.JRecord.def.IO.builders.ICobolIOBuilder;

public class WriteOD61 {

	public static void main(String[] args) throws IOException {
		String copybookFileName = WriteOD61.class.getResource("OccursDependingOn61.cbl").getFile();
		ICobolIOBuilder ioBuilder = CobolIoProvider.getInstance()
				.newIOBuilder(copybookFileName, ICopybookDialects.FMT_MAINFRAME);
		AbstractLine line;
		
		for (int i = 0; i < 30; i++) {
			line = ioBuilder.newLine();
			line.getFieldValue("Test-id").set("line " + i);
			
			for (int j = 0; j < 4; j++) {
				int count = (i + j) % 5 + 1;
				line.getFieldValue("text (" + j + ")").set(" " + j + ">");
				line.getFieldValue("week-of-month (" + j + ")").set(count);
				
				for (int k = 0; k < count; k++) {
					String index = " (" + j + ", " + k + ")";
					line.getFieldValue("sep" + index)          .set(" ");
					line.getFieldValue("daily-sales" + index)  .set(i * 100 + j * 10 + k);
				}
			}
			System.out.println(line.getFullLine());
		}

	}
}

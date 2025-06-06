package com.tech4box.JRecord.zExamples.iob.cobol.iobuilder;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.TreeMap;

import com.tech4box.JRecord.JRecordInterface1;
import com.tech4box.JRecord.Common.FieldDetail;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.Details.RecordDetail;
import com.tech4box.JRecord.def.IO.builders.ICobolIOBuilder;

public class TstUnderScore01 {

	private static String copybook
				= "       01  Tst_Redef.\n"
				+ "           03  fld1                pic x(20).\n"
				+ "           03  fld2                pic 9(8).\n"
				+ "           03  redefines fld2.\n"
				+ "               05                  pic x.\n"
				+ "               05  fld_21          pic 9(3).\n"
				+ "               05  fld_22          pic 9(1).\n"
				+ "           03  fld3.\n"
				+ "               05  fld_31          pic x(10).\n"
				+ "               05  fld_32 occurs 20.\n"
				+ "                   07 fld_321      pic xx.\n"
				+ "                   07 fld_322      pic x(4).\n"
				+ "           03 fld4 redefines fld3.\n"
				+ "              05  fld_41           pic x.\n"
				+ "              05  fld_42           pic x(2).\n"
				+ "              05  fld_43 occurs 4.\n"
				+ "                  07 fld_431       pic x(5).\n"
				+ "                  07 fld_432       pic 9(5).\n";
	
	public static void main(String[] args) throws IOException {
		ICobolIOBuilder iob =JRecordInterface1.COBOL.newIOBuilder(new StringReader(copybook), "Tst-Redef");
		
		LayoutDetail l = iob.getLayout();
		RecordDetail rec = l.getRecord(0);
		FieldDetail[] flds = new FieldDetail[rec.getFieldCount()];
		TreeMap<String, FieldDetail> dups = new TreeMap<String, FieldDetail>();
		
		for (int i = 0; i < flds.length; i++) {
			flds[i] = rec.getField(i);
		}
		
		Arrays.sort(flds, new Comparator<FieldDetail>() {
			@Override public int compare(FieldDetail o1, FieldDetail o2) {
				int ret = 1;
				if (o1.getPos() < o2.getPos()) {
					ret = -1;
				} else if (o1.getPos() == o2.getPos()) {
					ret = 0;
					if (o1.getEnd() > o2.getEnd()) {
						ret = -1;
					}
				}
				return ret;
			}
			
		});
		
		FieldDetail last = flds[0];
		
		for (int i = 1; i < flds.length; i++) {
			if (last.getEnd() >= flds[i].getPos()) {
				dups.put(last.getLookupName().toLowerCase(), last);
				dups.put(flds[i].getLookupName().toLowerCase(), flds[i]);
			}
			if (last.getEnd() < flds[i].getEnd()) {
				last = flds[i];
			}
		}
		
		Collection<FieldDetail> values = dups.values();
		
		for (FieldDetail v : values) {
			System.out.println(v.getLookupName() + "\t" + v.getPos() + "\t" + v.getLen());
		}
	}

}

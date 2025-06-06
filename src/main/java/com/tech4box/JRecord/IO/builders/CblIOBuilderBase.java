/*  -------------------------------------------------------------------------
 *
 *                Project: JRecord
 *    
 *    Sub-Project purpose: Provide support for reading Cobol-Data files 
 *                        using a Cobol Copybook in Java.
 *                         Support for reading Fixed Width / Binary / Csv files
 *                        using a Xml schema.
 *                         General Fixed Width / Csv file processing in Java.
 *    
 *                 Author: Bruce Martin
 *    
 *                License: LGPL 2.1 or latter
 *                
 *    Copyright (c) 2016, Bruce Martin, All Rights Reserved.
 *   
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *   
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Lesser General Public License for more details.
 *
 * ------------------------------------------------------------------------ */

package com.tech4box.JRecord.IO.builders;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.TreeMap;

import com.tech4box.JRecord.Common.CommonBits;
import com.tech4box.JRecord.Common.Constants;
import com.tech4box.JRecord.Common.Conversion;
import com.tech4box.JRecord.Details.AbstractLine;
import com.tech4box.JRecord.Details.CharLineProvider;
import com.tech4box.JRecord.Details.LayoutDetail;
import com.tech4box.JRecord.Details.LineProvider;
import com.tech4box.JRecord.Details.RecordDecider;
import com.tech4box.JRecord.External.CopybookLoader;
import com.tech4box.JRecord.External.ExternalRecord;
import com.tech4box.JRecord.ExternalRecordSelection.ExternalSelection;
import com.tech4box.JRecord.IO.AbstractLineReader;
import com.tech4box.JRecord.IO.AbstractLineWriter;
import com.tech4box.JRecord.IO.LineIOProvider;
import com.tech4box.JRecord.Log.AbsSSLogger;
import com.tech4box.JRecord.Log.TextLog;
import com.tech4box.JRecord.Option.IRecordPositionOption;


/**
 * Base class for the various IOBuilders. IOBuilders are used to create
 * LineReaders (readers for Cobol files) and LineWriter (Writers for cobol files)
 * 
 * @author Bruce Martin
 *
 */
public abstract class CblIOBuilderBase<IOB> /*implements ISchemaIOBuilder*/  {

	private static final TextLog DEFAULT_LOG = new TextLog();
	
	@SuppressWarnings("unchecked")
	protected final IOB self = (IOB) this;
	private LayoutDetail layout = null;
	private LineProvider lineProvider;
	private RecordDecider recordDecider=null;
	Map<String, RecordUpdate> recordSelectionMap = null;

	int dialect;
    //final int copybookType;

	int splitCopybook = CopybookLoader.SPLIT_NONE;
	private String defaultFont = null;
	private String font = null;
	int copybookFileFormat = 1; // actually Cb2xmlConstants.USE_STANDARD_COLUMNS;
	int fileOrganization = Constants.NULL_INTEGER;
//	int defaultFileOrganization = Constants.NULL_INTEGER;
	boolean dropCopybookNameFromFields = false;
	Boolean initToSpaces = null;
	int recordLength = -1;
	 
	
    AbsSSLogger log = DEFAULT_LOG; 
    
	protected CblIOBuilderBase(int dialect) {
		super();
		this.dialect = dialect;
	}

 
	protected CblIOBuilderBase(LayoutDetail schema) {
		super();
		//this.copybookType = copybookType;
		this.layout = schema;
		this.lineProvider = LineIOProvider.getInstance().getLineProvider(layout);
	}


	/**
	 * @param dialect the dialect to set
	 */
	public final IOB setDialect(int dialect) {
		this.dialect = dialect;
		clearLayout();
		return self;
	}




	/**
	 * @see com.tech4box.JRecord.def.IO.ICobolIOBuilder#setSplitCopybook(int)
	 */
	public IOB setSplitCopybook(int splitCopybook) {
		this.splitCopybook = splitCopybook;
		clearLayout();
		return self;
	}

	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.IO.IIOBuilder#setFont(java.lang.String)
	 */
	public IOB setFont(String font) {
		this.font = font;
		this.defaultFont = font;
		clearLayout();
		return self;
	}

	public IOB setDefaultFont(String defaultFont) {
		this.defaultFont = defaultFont;
		clearLayout();
		return self;
	}


	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.IO.IIOBuilder#setCopybookFileFormat(int)
	 */
	public final IOB setCopybookFileFormat(int copybookFileFormat) {
		this.copybookFileFormat = copybookFileFormat;
		clearLayout();
		return self;
	}

	

	/**
	 * @return the fileOrganization
	 */
	protected int getFileOrganization() {
//		if (fileOrganization < 0 && defaultFileOrganization >= 0) {
//			return defaultFileOrganization;
//		}
		return fileOrganization;
	}
//
//	public IOB setDefaultFileOrganization(int defaultFileOrganization) {
//		this.defaultFileOrganization = defaultFileOrganization;
//		clearLayout();
//		return self;
//	}


	/**
	 * @param fileOrganization the fileOrganization to set
	 */
	public IOB setFileOrganization(int fileOrganization) {
		this.fileOrganization = fileOrganization;
		clearLayout();
		return self;
	}




	/**
	 * @return the dropCopybookNameFromFields
	 */
	public final boolean isDropCopybookNameFromFields() {
		return dropCopybookNameFromFields;
	}


	/**
	 * @param dropCopybookNameFromFields the dropCopybookNameFromFields to set
	 */
	public final IOB setDropCopybookNameFromFields(
			boolean dropCopybookNameFromFields) {
		this.dropCopybookNameFromFields = dropCopybookNameFromFields;
		clearLayout();
		return self;
	}




	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.IO.IIOBuilder#setLog(com.tech4box.JRecord.Log.AbsSSLogger)
	 */
	public final IOB setLog(AbsSSLogger log) {
		this.log = log;
		clearLayout();
		return self;
	} 
	
	
    /**
	 * @see com.tech4box.JRecord.def.IO.builders.ICobolIOBuilder#newLine()
	 */
	public AbstractLine newLine() throws IOException {
		LayoutDetail schema = getLayout();
		
		return lineProvider.getLine(schema);
	}


    /**
	 * @see com.tech4box.JRecord.def.IO.builders.ICobolIOBuilder#newLine()
	 */
	public AbstractLine newLine(byte[] data) throws IOException {
		LayoutDetail schema = getLayout();
		
		return lineProvider.getLine(schema, data);
	}


	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.IO.IIOBuilder#newReader(java.lang.String)
	 */
	public final AbstractLineReader newReader(String filename) throws FileNotFoundException, IOException {
		//checkOk(true);
		return newReader(new FileInputStream(filename));
	}
	
    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.IO.IIOBuilder#newReader(java.io.InputStream)
	 */
	public final AbstractLineReader newReader(InputStream datastream) throws IOException {
		checkOk(true);
		LayoutDetail schema = getLayout();
		AbstractLineReader r = LineIOProvider.getInstance().getLineReader(schema);
		
		r.open(datastream, schema);
		return r;
	}
	
	
	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.IO.IIOBuilder#newWriter(java.lang.String)
	 */
	public final AbstractLineWriter newWriter(String filename) throws FileNotFoundException, IOException {
		checkOk(false);
		return newWriter(new FileOutputStream(filename));
	}

	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.IO.IIOBuilder#newWriter(java.io.OutputStream)
	 */
	public final AbstractLineWriter newWriter(OutputStream datastream) throws IOException {
		checkOk(false);
		LayoutDetail schema = getLayout();
		AbstractLineWriter r = LineIOProvider.getInstance().getLineWriter(schema);
		
		r.open(datastream);
		return r;
	}
	
	
	/**
	 * Method to allow ChildBuilders to validate the schema prior to
	 * creating Reader / Writer
	 * @param input
	 */
	protected void checkOk(boolean input) {
		
	}
	
	/**
	 * Get all the attributes:
	 * <pre>
	 *     dialect,
	 *     splitCopybook,
	 *     copybookFileFormat,
	 *     fileOrganization,
	 *     font,
	 *     dropCopybookNameFromFields,
	 * </pre>    
	 * Used to test if attributes are set correctly
	 * @return all the attributes
	 * 
	 * @deprecated for testing
	 */ @Deprecated
	public final Object[] getAllAttributes() {
		Object[] r = {
				dialect, 
				splitCopybook,
				copybookFileFormat,// actually Cb2xmlConstants.USE_STANDARD_COLUMNS;
				fileOrganization,
				getFont(),
				dropCopybookNameFromFields,
				//defaultFileOrganization,
				defaultFont
				
		};
		 
		return r;

	}
	
	
	
	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.IO.builders.ICobolIOBuilder#setRecordSelection(java.lang.String, com.tech4box.JRecord.ExternalRecordSelection.ExternalSelection)
	 */
	public IOB setRecordSelection(String recordName, ExternalSelection selectionCriteria) {
		getRecordUpdate(recordName).selection = selectionCriteria;
		return self;
	}


	public IOB setRecordPositionCode(String recordName, IRecordPositionOption positionOption) {
		getRecordUpdate(recordName).positionCode = positionOption;
		return self;
	}


	public IOB setRecordParent(String recordName, String parentName) {
		getRecordUpdate(recordName).parentName = parentName;
		return self;
	}


	/**
	 * @param recordDecider the recordDecider to set
	 */
	public final IOB setRecordDecider(RecordDecider recordDecider) {
		this.recordDecider = recordDecider;
		return self;
	}

	
	public IOB setRecordLength(int recordLength) {
		this.recordLength = recordLength;
		return self;
	}


	/**
	 * @param initToSpaces the initToSpaces to set
	 */
	public final IOB setInitToSpaces(boolean initToSpaces) {
		this.initToSpaces = Boolean.valueOf(initToSpaces);
		return self;
	}


	public String getFont() {
		return deriveActualFont(font == null ? defaultFont : font);
	}
	
//	public String getDefaultFont() {	
//		return deriveActualFont(defaultFont);
//	}
//

	private String deriveActualFont(String f) {
		
		if (f == null) {
			f = "";
			if (Conversion.DEFAULT_CHARSET_DETAILS.isMultiByte
			&&  CommonBits.getLineType(fileOrganization) == CommonBits.LT_BYTE) {
				f = Conversion.getDefaultSingleByteCharacterset();
			}
		}
		return f;
	}

	public final ExternalRecord getExternalRecord() throws IOException  {			
		ExternalRecord schema =  getExternalRecordImpl();
				
		schema.setRecordDecider(recordDecider);
		schema.setRecordLength(recordLength);
		//schema.setFontName(getFont());
		
		if (font != null) {
			schema.setFontName(font);
		}
		
		if (fileOrganization >= 0) {
			schema.setFileStructure(fileOrganization);
//		} else if (defaultFileOrganization >= 0 && schema.getFileStructure() <= 0 
//				&& schema.isFileStructureUpdated() == false) {
//			schema.setFileStructure(defaultFileOrganization);
		}
		
		if (initToSpaces == null) {
			schema.setInitToSpaces(! schema.isBinary());
		} else {
			schema.setInitToSpaces(initToSpaces);
		}
		
		if (recordSelectionMap != null) {
			for (int i = 0; i < schema.getNumberOfRecords(); i++) {
				ExternalRecord record = schema.getRecord(i);
				RecordUpdate recUpdate = recordSelectionMap.get(record.getRecordName().toLowerCase());
				if (recUpdate != null) {
					if (recUpdate.selection != null) {	
						record.setRecordSelection(recUpdate.selection);
					}
		
					if (recUpdate.positionCode != null) {
						record.setRecordPositionOption(recUpdate.positionCode);
					}
					
					if (recUpdate.parentName != null) {
						record.setParentName(recUpdate.parentName);
					}
				}
			}
		}

		return schema; 
	}
	
	private RecordUpdate getRecordUpdate(String recordName) {
		RecordUpdate rec;
		if (recordSelectionMap == null) {
			recordSelectionMap = new TreeMap<String, RecordUpdate>();
		}
		
		String key = recordName.toLowerCase();
		rec = recordSelectionMap.get(key);
		if (rec == null) {
			rec = new RecordUpdate();
			recordSelectionMap.put(key, rec);
		}

		return rec;
	}
	
	protected abstract ExternalRecord getExternalRecordImpl() throws IOException;

	protected void clearLayout() {
		layout = null;
	}


	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.IO.IIOBuilder#getLayout()
	 */
	public final LayoutDetail getLayout() throws IOException {
		if (layout == null) {
			layout = getExternalRecord()	.asLayoutDetail();
			lineProvider = LineIOProvider.getInstance().getLineProvider(layout);
		}
		
		if (layout.hasBinaryField()) {
			if (CommonBits.getLineType(layout.getFileStructure()) != CommonBits.LT_BYTE) {
				throw new RuntimeException("This is a binary Layout (Schema) but you have selected a Character file Organisation,"
						+ " you must use you must Byte based Organisation");
			}
			if (lineProvider instanceof CharLineProvider) {
				throw new RuntimeException("This is a binary schema (requires byte based line-provider), but the line provider is a Text Based Line provider");
			}
		}

		return layout;
	}

	
	
	private static class RecordUpdate {
		ExternalSelection selection = null;
		String parentName = null;
		IRecordPositionOption positionCode = null;
	}
	
}

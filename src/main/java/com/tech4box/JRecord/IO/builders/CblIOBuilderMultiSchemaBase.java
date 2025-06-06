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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;

import com.tech4box.JRecord.Common.Constants;
import com.tech4box.JRecord.Common.RecordException;
import com.tech4box.JRecord.External.ExternalRecord;
import com.tech4box.JRecord.External.ICopybookLoaderStream;
import com.tech4box.JRecord.External.ISetDropCopybookName;
import com.tech4box.JRecord.External.Def.ExternalField;
import com.tech4box.JRecord.ExternalRecordSelection.ExternalSelection;
import com.tech4box.JRecord.Log.AbsSSLogger;
import com.tech4box.JRecord.Numeric.ICopybookDialects;


public class CblIOBuilderMultiSchemaBase<IOB>
extends CblIOBuilderBase<IOB> implements IGetLoader  { 

	private final String copybookname;
	final ICopybookLoaderStream loader;
	final ArrayList<ICreateExternal> copybooks = new ArrayList<ICreateExternal>();
	
	boolean keepFillers = false;


	/**
	 * Create Multicopybokk builder
	 * @param copybookName name of the copybook
	 * @param loader copybook (schema) loader
	 */
	public CblIOBuilderMultiSchemaBase(String copybookname, ICopybookLoaderStream loader) {
		this(copybookname, loader, ICopybookDialects.FMT_MAINFRAME);
	}
	
	/**
	 * Create Multicopybokk builder
	 * @param copybookName name of the copybook
	 * @param loader copybook (schema) loader
	 */
	protected CblIOBuilderMultiSchemaBase(String copybookname, ICopybookLoaderStream loader, int dialect) {
		super(dialect);
		this.copybookname = copybookname;
		this.loader = loader;
	}

	
	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.def.IO.builders.ICobolMultiCopybookIOBuilder#addCopyBook(java.lang.String)
	 */
	public IOB addCopyBook(String fileName) {
		copybooks.add(new CreateExternalFromFile(this, fileName));
		return super.self;
	} 

	public IOB addCopyBook(InputStream inStream, String copybookName) {
		copybooks.add(new CreateExternalFromStream(this, inStream, copybookName));
		return super.self;
	}

	public IOB addCopyBook(Reader reader, String copybookName) {
		copybooks.add(new CreateExternalFromReader(this, reader, copybookName));
		return super.self;
	}

	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.IO.builders.CblIOBuilderBase#getExternalRecordImpl()
	 */
	@Override
	protected ExternalRecord getExternalRecordImpl() throws IOException { 
		
		if (copybooks.size() == 0) {
			throw new RecordException("No copybooks have been specified");
		}
		if (loader instanceof ISetDropCopybookName) { 
			ISetDropCopybookName xLoader = (ISetDropCopybookName) loader;
			xLoader.setDropCopybookFromFieldNames(super.dropCopybookNameFromFields);
			xLoader.setKeepFillers(keepFillers);
		}
		try {
			 
			if (copybooks.size() == 1) {
				return copybooks.get(0).createExternalRecord();
			} else {
				ExternalRecord rec = ExternalRecord.getNullRecord(copybookname, Constants.rtGroupOfRecords, super.getFont());
				ExternalRecord r;
				
				for (ICreateExternal copybookdef : copybooks) {
					r = copybookdef.createExternalRecord();
					if (r.getNumberOfRecords() == 0) {
						rec.addRecord(r);
					} else {
						for (int i = 0; i < r.getNumberOfRecords(); i++) {
							rec.addRecord(r.getRecord(i));
						}
					}
				}
				for (ICreateExternal copybookdef : copybooks) {
					copybookdef.clearLastRecord();
				}
				
				return rec;
			}

		} catch (RecordException e) {
			throw e; 
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw new RecordException(e.getMessage(), e);
		} 
	}
	

	/**
	 * @see com.tech4box.JRecord.def.IO.ICobolIOBuilder#setSplitCopybook(int)
	 */
	public IOB setSplitCopybook(int splitCopybook) {
		getLast().setSplitCopybook(splitCopybook);
		super.setSplitCopybook(splitCopybook);
		clearLayout();
		
		return super.self;
	}
	
	/**
	 * @param keepFillers the keepFillers to set
	 */
	public final IOB setKeepFillers(boolean keepFillers) {
		this.keepFillers = keepFillers;
		
		return super.self;
	}
	
	public final IOB setStartingPosition(int position) {
		getLast().setStartPosition(new IntStartingPos(position));
		clearLayout();
		
		return super.self;
	}

	
	public final IOB setStartingPositionToField(String recordName, String fieldName) {
		getLast().setStartPosition(new FieldStartingPos(recordName, fieldName));
		clearLayout();
		
		return super.self;
	}
	public IOB setRecordSelectionCurrentCopybook(ExternalSelection recordSelection) {
		getLast().setRecordSelection(recordSelection);
		clearLayout();
		
		return super.self;
	}
	
	
	private ICreateExternal getLast() {
		if (copybooks.size() == 0) {
			throw new RuntimeException("You can only use setRecordSelectionCurrentCopybook after you have added a copybook !!!");
		}
		return copybooks.get(copybooks.size() - 1);
	}

	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.IO.builders.IGetLoader#getLoader()
	 */
	@Override
	public final ICopybookLoaderStream getLoader() {
		return loader;
	}

	
	/**
	 * @return the dialect
	 */
	@Override
	public final int getDialect() {
		return dialect;
	}


	/**
	 * @return the copybookFileFormat
	 */
	@Override
	public final int getCopybookFileFormat() {
		return copybookFileFormat;
	}


	/**
	 * @return the log
	 */
	@Override
	public final AbsSSLogger getLog() {
		return log;
	}
	
	private static class IntStartingPos implements IStartingPosition {
		int position;
		
		IntStartingPos(int pos) {
			position = pos;
		}
		
		public int calculateStartingPosition() {
			return position - 1;
		}
	}
	
	private class FieldStartingPos implements IStartingPosition {
		final String recordName, fieldName;
		final int index;
		

		public FieldStartingPos(String recordName, String fieldName) {
			super();
			this.recordName = recordName;
			this.fieldName = fieldName;
			for (int i = 0; i < copybooks.size() - 1; i++) {
				ICreateExternal c = copybooks.get(i);
				if (recordName.equals(c.getRecordName())) {
					index = i;
					return;
				}
			}
			throw new RecordException("Record: " + recordName + " was not found");
		}
		
		public int calculateStartingPosition() {
			ICreateExternal c = copybooks.get(index);
			ExternalRecord xr = c.getLastExternalRecord();
			ExternalField f = xr.getRecordField(fieldName);
			if (f == null) {
				throw new RecordException("Field: " + fieldName + " was not found in record " + recordName);
			}	
			
			return f.getPos() - 1;
		}
	}
}

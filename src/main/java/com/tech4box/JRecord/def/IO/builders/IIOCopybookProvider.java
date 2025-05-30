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

package com.tech4box.JRecord.def.IO.builders;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import com.tech4box.JRecord.External.ExternalRecord;

/**
 * Interface to create IOBuilders the use a RecordEditor-Xml file schema
 * <pre>
 *     IIOBuilder ioBldr = JRecordInterface1.SCHEMA_XML
 *                                          .newIOBuilder(xmlSchemaName);
 * </pre>                                          
 * @author Bruce Martin
 *
 */
public interface IIOCopybookProvider {

	/**
	 * Create a new RecordEditor-Xml IOBuilder for a file.
	 * 
	 * @param schemaFileName name of the RecordEditor-Xml file name (or schema file).
	 * 
	 * These are the default values (which can be overriden with the appropriate set* method
	 * @return requested IOBuilder
	 */
	public abstract IFileIOBuilder newIOBuilder(String schemaFileName);

	/**
	 * Create a new IOBulder for a RecordEditor-Xml stream.
	 * 
	 * @param schemaStream stream to read the file-Schema from.
	 * @param schemaName name of the File-Schema.
	 * 
	 * These are the default values (which can be overriden with the appropriate set* method
	 * @return requested IOBuilder
	 */
	public abstract IFileIOBuilder newIOBuilder(
			InputStream schemaStream, String schemaName);
	
	/**
	 * Create IO Builder with reader
	 * @param xmlReader RecordEditor-Xml Reader
	 * @param Name of the Schema
	 * @return IOBuilder
	 */
	public abstract IFileIOBuilder newIOBuilder(
			Reader xmlReader, String copybookName);

	/**
	 * Exporting a {@link ExternalRecord} to an Xml-File
	 * 
	 * @param fileName Output file name
	 * @param schema schema to export
	 * 
	 * @throws Exception any error that occurs
	 */
	public abstract void export(String fileName, ExternalRecord schema) throws Exception;

	/**
	 * Exporting a {@link ExternalRecord} to a Stream
	 * 
	 * @param outStream output stream where the schema is to be written
	 * @param schema
	 * 
	 * @throws Exception Any Error that occurs
	 */
	public abstract void export(OutputStream outStream, ExternalRecord schema) throws Exception;

}
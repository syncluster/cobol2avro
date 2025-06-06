/*
 * @Author Bruce Martin
 * Created on 29/08/2005
 *
 * Purpose:
 */
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

package com.tech4box.JRecord.Details;




/**
 * A <b>LineProvider</b> creates lines for the calling program.
 * By creating your own <b>LineProvider</b>, you can use your
 * own <b>Line's</b> rather than the System <b>Line</b> / XmlLine class.
 *
 * @author Bruce Martin
 *
 */
public interface LineProvider {

    /**
     * Create a null line
     *
     * @param recordDescription record description
     *
     * @return new line
     */
    public abstract AbstractLine getLine(LayoutDetail recordDescription);


    /**
     * Line Providers provide lines to the calling program
     *
     * @param recordDescription record layout details
     * @param linesText text to create the line from
     *
     * @return line
     */
    public abstract AbstractLine getLine(LayoutDetail recordDescription, String linesText);

    /**
     * Build a Line
     *
     * @param recordDescription record layout details
     * @param lineBytes bytes to create the line from
     *
     * @return line
     */
    public abstract AbstractLine getLine(LayoutDetail recordDescription, byte[] lineBytes);
}
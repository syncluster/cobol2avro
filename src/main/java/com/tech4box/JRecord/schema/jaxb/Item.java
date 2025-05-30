//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.02.08 at 09:26:41 AM EST 
//


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

package com.tech4box.JRecord.schema.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.tech4box.JRecord.Common.IFieldDetail;
import com.tech4box.JRecord.External.Def.DependingOnDefinition;
import com.tech4box.JRecord.cgen.def.IArrayAnyDimension;
import com.tech4box.JRecord.schema.IArrayItemCheck;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}condition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}item" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="assumed-digits" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="depending-on" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="display-length" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="editted-numeric" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="inherited-usage" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="insert-decimal-point" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="justified" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="level" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="numeric" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="occurs" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="occurs-min" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="picture" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="position" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="redefined" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="redefines" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="scale" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="sign-position" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="sign-separate" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="signed" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="storage-length" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="sync" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="usage" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "condition",
    "item"
})
@XmlRootElement(name = "item")
public class Item implements IItem {
	
	@XmlTransient
	public int itemType = TYPE_GROUP;
	@XmlTransient
	public IFieldDetail fieldDefinition = null;
	@XmlTransient
	public IArrayAnyDimension arrayDefinition = null;
	@XmlTransient
	public List<String> names = null;
	@XmlTransient
	public String nameToUse, fieldName;
	@XmlTransient
	public IArrayItemCheck arrayValidation = null;
	@XmlTransient
	public boolean fieldRedefined = false;
//	@XmlTransient
//	public IOccursDependingDetails occursDependingValue = NullOccursDependingDtls.INSTANCE;
	@XmlTransient
	public DependingOnDefinition.SizeField saveDtls, arraySizeField;
	

	public void initFields(String name, List<Item> childItems) {
    	fieldName = name;
    	nameToUse = name;
    	this.name = name;
    	fieldRedefined = false;
    	itemType = IItem.TYPE_GROUP;
    	
    	position = 1;
    	
    	storageLength = 0;
    	displayLength = 0;
    	for (Item ii : childItems) {
    		storageLength += ii.storageLength;
    		displayLength += ii.displayLength;
    	}
    	
    	item = childItems;
    	level = "00";

	}

    protected List<Condition> condition;
    protected List<Item> item;
    @XmlAttribute(name = "assumed-digits")
    protected Integer assumedDigits;
    @XmlAttribute(name = "depending-on")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String dependingOn;
    @XmlAttribute(name = "display-length", required = true)
    protected int displayLength;
    @XmlAttribute(name = "editted-numeric")
    protected Boolean edittedNumeric;
    @XmlAttribute(name = "inherited-usage")
    protected Boolean inheritedUsage;
    @XmlAttribute(name = "insert-decimal-point")
    protected Boolean insertDecimalPoint;
    @XmlAttribute(name = "justified")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String justified;
    @XmlAttribute(name = "level", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String level;
    @XmlAttribute(name = "name", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String name;
    @XmlAttribute(name = "numeric")
    protected Boolean numeric;
    @XmlAttribute(name = "occurs")
    protected Integer occurs;
    @XmlAttribute(name = "occurs-min")
    protected Integer occursMin;
    @XmlAttribute(name = "picture")
    @XmlSchemaType(name = "anySimpleType")
    protected String picture;
    @XmlAttribute(name = "position", required = true)
    protected int position;
    @XmlAttribute(name = "redefined")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String redefined;
    @XmlAttribute(name = "redefines")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String redefines;
    @XmlAttribute(name = "scale")
    protected Integer scale;
    @XmlAttribute(name = "sign-position")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String signPosition;
    @XmlAttribute(name = "sign-separate")
    protected Boolean signSeparate;
    @XmlAttribute(name = "signed")
    protected Boolean signed;
    @XmlAttribute(name = "storage-length", required = true)
    protected int storageLength;
    @XmlAttribute(name = "sync")
    protected Boolean sync;
    @XmlAttribute(name = "usage")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String usage;
    @XmlAttribute(name = "value")
    @XmlSchemaType(name = "anySimpleType")
    protected String value;

    /**
     * Gets the value of the condition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the condition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCondition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Condition }
     * 
     * 
     */
    public List<Condition> getCondition() {
        if (condition == null) {
            condition = new ArrayList<Condition>();
        }
        return this.condition;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getItem()
	 */
    @Override
	public List<Item> getChildItems() {
        if (item == null) {
            item = new ArrayList<Item>();
        }
        return this.item;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getAssumedDigits()
	 */
    @Override
	public Integer getAssumedDigits() {
        return assumedDigits;
    }

    /**
     * Sets the value of the assumedDigits property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAssumedDigits(Integer value) {
        this.assumedDigits = value;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getDependingOn()
	 */
    @Override
	public String getDependingOn() {
        return dependingOn;
    }

    @Override
    public int getSaveIndex() {
    	return saveDtls == null ? -1 : saveDtls.fieldNumber;
    }

    @Override
    public int getODArraySizeIdx() {
    	return arraySizeField != null /*&& arraySizeField.indexedOD*/ ? arraySizeField.fieldNumber : -1;
    }

	/**
     * Sets the value of the dependingOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDependingOn(String value) {
        this.dependingOn = value;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getDisplayLength()
	 */
    @Override
	public int getDisplayLength() {
        return displayLength;
    }

    /**
     * Sets the value of the displayLength property.
     * 
     */
    public void setDisplayLength(int value) {
        this.displayLength = value;
    }

    /**
     * Gets the value of the edittedNumeric property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEdittedNumeric() {
        return edittedNumeric;
    }
    
    /**
     * Gets the value of the insertDecimalPoint property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isInsertDecimalPoint() {
        return insertDecimalPoint;
    }

    /**
     * Sets the value of the insertDecimalPoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInsertDecimalPoint(Boolean value) {
        this.insertDecimalPoint = value;
    }

    /**
     * Gets the value of the justified property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJustified() {
        return justified;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getLevel()
	 */
    @Override
	public String getLevel() {
        return level;
    }

    /**
     * Sets the value of the level property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLevel(String value) {
        this.level = value;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getName()
	 */
    @Override
	public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#isNumeric()
	 */
    @Override
	public Boolean isNumeric() {
        return numeric;
    }

    /**
     * Sets the value of the numeric property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNumeric(Boolean value) {
        this.numeric = value;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getOccurs()
	 */
    @Override
	public Integer getOccurs() {
        return occurs;
    }

    /**
     * Sets the value of the occurs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOccurs(Integer value) {
        this.occurs = value;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getOccursMin()
	 */
    @Override
	public Integer getOccursMin() {
        return occursMin;
    }

    /**
     * Sets the value of the occursMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOccursMin(Integer value) {
        this.occursMin = value;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getPicture()
	 */
    @Override
	public String getPicture() {
        return picture;
    }

    /**
     * Sets the value of the picture property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPicture(String value) {
        this.picture = value;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getPosition()
	 */
    @Override
	public int getPosition() {
        return position;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getPosition(int[], int)
	 */
	@Override
	public int getPosition(int[] indexs) {
		int p = position;
		if (indexs != null && arrayDefinition != null) {
			for (int i = 0; i < indexs.length; i++) {
				p += arrayDefinition.getArrayElementSize(i) * indexs[i];
			}
		}

		return p;
	}

	/**
     * Sets the value of the position property.
     * 
     */
    public void setPosition(int value) {
        this.position = value;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getRedefined()
	 */
    @Override
	public String getRedefined() {
        return redefined;
    }

    /**
     * Sets the value of the redefined property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRedefined(String value) {
        this.redefined = value;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getRedefines()
	 */
    @Override
	public String getRedefines() {
        return redefines;
    }

    /**
     * Sets the value of the redefines property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRedefines(String value) {
        this.redefines = value;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getScale()
	 */
    @Override
	public Integer getScale() {
        return scale;
    }

    /**
     * Sets the value of the scale property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setScale(Integer value) {
        this.scale = value;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getSignPosition()
	 */
    @Override
	public String getSignPosition() {
        return signPosition;
    }

    /**
     * Sets the value of the signPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignPosition(String value) {
        this.signPosition = value;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#isSignSeparate()
	 */
    @Override
	public Boolean isSignSeparate() {
        return signSeparate;
    }

    /**
     * Sets the value of the signSeparate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSignSeparate(Boolean value) {
        this.signSeparate = value;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#isSigned()
	 */
    @Override
	public Boolean isSigned() {
        return signed;
    }

    /**
     * Sets the value of the signed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSigned(Boolean value) {
        this.signed = value;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getStorageLength()
	 */
    @Override
	public int getStorageLength() {
        return storageLength;
    }

    /**
     * Sets the value of the storageLength property.
     * 
     */
    public void setStorageLength(int value) {
        this.storageLength = value;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#isSync()
	 */
    @Override
	public Boolean isSync() {
        return sync;
    }

    /**
     * Sets the value of the sync property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSync(Boolean value) {
        this.sync = value;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getUsage()
	 */
    @Override
	public String getUsage() {
        return usage;
    }

    /**
     * Sets the value of the usage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsage(String value) {
        this.usage = value;
    }

    /* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getValue()
	 */
    @Override
	public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getItemType()
	 */
	@Override
	public final int getItemType() {
		return itemType;
	}

	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getFieldDefinition()
	 */
	@Override
	public final IFieldDetail getFieldDefinition() {
		return fieldDefinition;
	}

	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getArrayDefinition()
	 */
	@Override
	public final IArrayAnyDimension getArrayDefinition() {
		return arrayDefinition;
	}

	/**
	 * @return the arrayValidation
	 */
	@Override
	public final IArrayItemCheck getArrayValidation() {
		return arrayValidation;
	}

	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getNameToUse()
	 */
	@Override
	public final String getNameToUse() {
		return nameToUse;
	}

	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#getFieldName()
	 */
	@Override
	public final String getFieldName() {
		return fieldName;
	}

	/* (non-Javadoc)
	 * @see com.tech4box.JRecord.schema.jaxb.IItem#isFieldRedefined()
	 */
	@Override
	public final boolean isFieldRedefined() {
		return fieldRedefined;
	}

}

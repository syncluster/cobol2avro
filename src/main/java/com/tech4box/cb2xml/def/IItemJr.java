/**
 * 
 */
package com.tech4box.cb2xml.def;

import java.util.List;

/**
 * Item for use in JRecord
 * 
 * @author Bruce Martin
 */
public interface IItemJr extends IItem {
	

	public abstract int getType();

	/* (non-Javadoc)
	 * @see com.tech4box.cb2xml.def.IItem#getChildItems()
	 */
	@Override
	public abstract List<? extends IItemJr> getChildItems();
}

/*
 * Created on Jul 13, 2004
 */
package com.realpersist.gef.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a Schema in the model. Note that this class also includes
 * diagram specific information (layoutManualDesired and layoutManualAllowed fields)
 * although ideally these should be in a separate model hiearchy 
 * @author Phil Zoio
 */
public class Schema extends PropertyAwareObject
{

	private String name;
	private ArrayList tables = new ArrayList();
	private Map tablesMap = new HashMap();
	private boolean layoutManualDesired = true;
	private boolean layoutManualAllowed = false;

	/**
	 * @param name
	 */
	public Schema(String name)
	{
		super();
		if (name == null)
			throw new NullPointerException("Name cannot be null");
		this.name = name;
	}

	public void addTable(Table table)
	{
		tables.add(table);
		tablesMap.put(table.getName(), table);
		firePropertyChange(CHILD, null, table);
	}

	public void addTable(Table table, int i)
	{
		tables.add(i, table);
		tablesMap.put(table.getName(), table);
		firePropertyChange(CHILD, null, table);
	}

	public void removeTable(Table table)
	{
		tables.remove(table);
		tablesMap.remove(table.getName());
		firePropertyChange(CHILD, table, null);
	}

	/**
	 * returns an individual named table
	 */
	public Table getTable(String name)
	{
		return (Table) tablesMap.get(name);
	}

	/**
	 * @return the Tables for the current schema
	 */
	public List getTables()
	{
		return tables;
	}

	/**
	 * @return the name of the schema
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param layoutManualAllowed
	 *            The layoutManualAllowed to set.
	 */
	public void setLayoutManualAllowed(boolean layoutManualAllowed)
	{
		this.layoutManualAllowed = layoutManualAllowed;
	}

	/**
	 * @return Returns the layoutManualDesired.
	 */
	public boolean isLayoutManualDesired()
	{
		return layoutManualDesired;
	}

	/**
	 * @param layoutManualDesired
	 *            The layoutManualDesired to set.
	 */
	public void setLayoutManualDesired(boolean layoutManualDesired)
	{
		this.layoutManualDesired = layoutManualDesired;
		firePropertyChange(LAYOUT, null, new Boolean(layoutManualDesired));
	}

	/**
	 * @return Returns whether we can lay out individual tables manually using the XYLayout
	 */
	public boolean isLayoutManualAllowed()
	{
		return layoutManualAllowed;
	}

}
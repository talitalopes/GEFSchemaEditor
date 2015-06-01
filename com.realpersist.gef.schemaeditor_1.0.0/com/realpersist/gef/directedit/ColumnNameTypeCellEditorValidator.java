/*
 * Created on Jul 19, 2004
 */
package com.realpersist.gef.directedit;

import org.eclipse.jface.viewers.ICellEditorValidator;

import com.realpersist.gef.model.ColumnType;



/**
 * ICellValidator to validate direct edit values in the column label
 * Collaborates with an instance of ValidationMessageHandler
 * @author Phil Zoio
 */
public class ColumnNameTypeCellEditorValidator implements ICellEditorValidator
{

	private ValidationMessageHandler handler;
	
	/**
	 * @param validationMessageHandler the validation message handler to pass error information to
	 */
	public ColumnNameTypeCellEditorValidator(ValidationMessageHandler validationMessageHandler)
	{
		this.handler = validationMessageHandler;
	}

	/**
	 * @param validation of column type
	 * @return the error message if an error has occurred, otherwise null
	 */
	public String isValid(Object value)
	{
		String string = (String)value;
		String name = null;
		String type = null;
		int colonIndex = string.indexOf(':');
		if (colonIndex >= 0)
		{
			name = string.substring(0, colonIndex);
			if (string.length() > colonIndex+1)
			{
				type = string.substring(colonIndex+1);
			}
		}
		if (name != null && type!= null)
		{
			
			if (ColumnType.hasType(type))
			{
				return unsetMessageText();
			}
			if (name.indexOf(" ")!= -1)
			{
				String text = "Column name should not include the space character";
				return setMessageText(text);
			}
			else
			{
				String text = "Invalid type: permissible types are: " + ColumnType.getTypes();
				return setMessageText(text);
			}
		}
		else
		{
			String text = "Invalid format for text entry. Needs [name]:[type] format";
			return setMessageText(text);
		}
	}


	private String unsetMessageText()
	{
		handler.reset();
		return null;
	}

	


	private String setMessageText(String text)
	{
		handler.setMessageText(text);
		return text;
	}



}

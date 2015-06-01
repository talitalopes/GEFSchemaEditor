/*
 * Created on Jul 18, 2004
 */
package com.realpersist.gef.policy;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.jface.viewers.CellEditor;

import com.realpersist.gef.command.ResetNameTypeForColumnCommand;
import com.realpersist.gef.model.Column;
import com.realpersist.gef.part.ColumnPart;

/**
 * EditPolicy for the direct editing of Column names
 * 
 * @author Phil Zoio
 */
public class ColumnDirectEditPolicy extends DirectEditPolicy
{

	private String oldValue;

	/**
	 * @see DirectEditPolicy#getDirectEditCommand(org.eclipse.gef.requests.DirectEditRequest)
	 */
	protected Command getDirectEditCommand(DirectEditRequest request)
	{
		ResetNameTypeForColumnCommand cmd = new ResetNameTypeForColumnCommand();
		Column column = (Column) getHost().getModel();
		cmd.setSource(column);
		cmd.setOldName(column.getName());
		cmd.setOldType(column.getType());
		CellEditor cellEditor = request.getCellEditor();
		cmd.setNameType((String) cellEditor.getValue());
		return cmd;
	}

	/**
	 * @see DirectEditPolicy#showCurrentEditValue(org.eclipse.gef.requests.DirectEditRequest)
	 */
	protected void showCurrentEditValue(DirectEditRequest request)
	{
		String value = (String) request.getCellEditor().getValue();
		ColumnPart columnPart = (ColumnPart) getHost();
		columnPart.handleNameChange(value);
	}

	/**
	 * @param to
	 *            Revert request
	 */
	protected void storeOldEditValue(DirectEditRequest request)
	{
		CellEditor cellEditor = request.getCellEditor();
		oldValue = (String) cellEditor.getValue();
	}

	/**
	 * @param request
	 */
	protected void revertOldEditValue(DirectEditRequest request)
	{
		CellEditor cellEditor = request.getCellEditor();
		cellEditor.setValue(oldValue);
		ColumnPart columnPart = (ColumnPart) getHost();
		columnPart.revertNameChange(oldValue);
		
	}
}
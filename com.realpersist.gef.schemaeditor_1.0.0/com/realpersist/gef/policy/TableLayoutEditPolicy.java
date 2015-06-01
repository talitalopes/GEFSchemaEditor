/*
 * Created on Jul 15, 2004
 */
package com.realpersist.gef.policy;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import com.realpersist.gef.command.ColumnMoveCommand;
import com.realpersist.gef.command.ColumnTransferCommand;
import com.realpersist.gef.model.Column;
import com.realpersist.gef.model.Table;
import com.realpersist.gef.part.ColumnPart;
import com.realpersist.gef.part.TablePart;

/**
 * Handles moving of columns within and between tables
 * @author Phil Zoio
 */
public class TableLayoutEditPolicy extends FlowLayoutEditPolicy
{

	/**
	 * Creates command to transfer child column to after column (in another
	 * table)
	 */
	protected Command createAddCommand(EditPart child, EditPart after)
	{

		if (!(child instanceof ColumnPart))
			return null;
		if (!(after instanceof ColumnPart))
			return null;

		Column toMove = (Column) child.getModel();
		Column afterModel = (Column) after.getModel();

		TablePart originalTablePart = (TablePart) child.getParent();
		Table originalTable = (Table) originalTablePart.getModel();
		TablePart newTablePart = (TablePart) after.getParent();
		Table newTable = newTablePart.getTable();

		int oldIndex = originalTablePart.getChildren().indexOf(child);
		int newIndex = newTablePart.getChildren().indexOf(after);

		ColumnTransferCommand command = new ColumnTransferCommand(toMove, afterModel, originalTable, newTable,
				oldIndex, newIndex);
		return command;

	}

	/**
	 * Creates command to transfer child column to after specified column
	 * (within table)
	 */
	protected Command createMoveChildCommand(EditPart child, EditPart after)
	{
		if (after != null)
		{
			Column childModel = (Column) child.getModel();
			Column afterModel = (Column) after.getModel();

			Table parentTable = (Table) getHost().getModel();
			int oldIndex = getHost().getChildren().indexOf(child);
			int newIndex = getHost().getChildren().indexOf(after);

			ColumnMoveCommand command = new ColumnMoveCommand(childModel, parentTable, oldIndex, newIndex);
			return command;
		}
		return null;
	}

	/**
	 * @param request
	 * @return
	 */
	protected Command getCreateCommand(CreateRequest request)
	{
		return null;
	}

	/**
	 * @param request
	 * @return
	 */
	protected Command getDeleteDependantCommand(Request request)
	{
		return null;
	}

}
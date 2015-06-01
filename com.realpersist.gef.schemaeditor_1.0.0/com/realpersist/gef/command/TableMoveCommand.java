/*
 * Created on Jul 20, 2004
 */
package com.realpersist.gef.command;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import com.realpersist.gef.model.Table;

/**
 * Command to move the bounds of an existing table. Only used with
 * XYLayoutEditPolicy (manual layout)
 * 
 * @author Phil Zoio
 */
public class TableMoveCommand extends Command
{

	private Table table;
	private Rectangle oldBounds;
	private Rectangle newBounds;

	public TableMoveCommand(Table table, Rectangle oldBounds, Rectangle newBounds)
	{
		super();
		this.table = table;
		this.oldBounds = oldBounds;
		this.newBounds = newBounds;
	}

	public void execute()
	{
		table.modifyBounds(newBounds);
	}

	public void undo()
	{
		table.modifyBounds(oldBounds);
	}

}
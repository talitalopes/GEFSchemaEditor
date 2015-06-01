/*
 * Created on Jul 15, 2004
 */
package com.realpersist.gef.command;

import java.util.List;

import org.eclipse.gef.commands.Command;

import com.realpersist.gef.model.Relationship;
import com.realpersist.gef.model.Table;

/**
 * Command to change the foreign key we are connecting to a particular primary
 * key
 * 
 * @author Phil Zoio
 */
public class ReconnectForeignKeyCommand extends Command
{

	/** source Table * */
	protected Table sourceForeignKey;
	/** target Table * */
	protected Table targetPrimaryKey;
	/** Relationship between source and target * */
	protected Relationship relationship;
	/** previous source prior to command execution * */
	protected Table oldSourceForeignKey;

	/**
	 * Makes sure that primary key doesn't reconnect to itself or try to create
	 * a relationship which already exists
	 */
	public boolean canExecute()
	{

		boolean returnVal = true;

		Table primaryKeyTable = relationship.getPrimaryKeyTable();

		//cannot connect to itself
		if (primaryKeyTable.equals(sourceForeignKey))
		{
			returnVal = false;
		}
		else
		{

			List relationships = sourceForeignKey.getForeignKeyRelationships();
			for (int i = 0; i < relationships.size(); i++)
			{

				Relationship relationship = ((Relationship) (relationships.get(i)));
				if (relationship.getPrimaryKeyTable().equals(targetPrimaryKey)
						&& relationship.getForeignKeyTable().equals(sourceForeignKey))
				{
					returnVal = false;
					break;
				}
			}
		}

		return returnVal;

	}

	/**
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	public void execute()
	{
		if (sourceForeignKey != null)
		{
			oldSourceForeignKey.removeForeignKeyRelationship(relationship);
			relationship.setForeignKeyTable(sourceForeignKey);
			sourceForeignKey.addForeignKeyRelationship(relationship);
		}
	}

	/**
	 * @return Returns the sourceForeignKey.
	 */
	public Table getSourceForeignKey()
	{
		return sourceForeignKey;
	}

	/**
	 * @param sourceForeignKey
	 *            The sourceForeignKey to set.
	 */
	public void setSourceForeignKey(Table sourceForeignKey)
	{
		this.sourceForeignKey = sourceForeignKey;
	}

	/**
	 * @return Returns the targetPrimaryKey.
	 */
	public Table getTargetPrimaryKey()
	{
		return targetPrimaryKey;
	}

	/**
	 * @param targetPrimaryKey
	 *            The targetPrimaryKey to set.
	 */
	public void setTargetPrimaryKey(Table targetPrimaryKey)
	{
		this.targetPrimaryKey = targetPrimaryKey;
	}

	/**
	 * @return Returns the relationship.
	 */
	public Relationship getRelationship()
	{
		return relationship;
	}

	/**
	 * Sets the Relationship associated with this
	 * 
	 * @param relationship
	 *            the Relationship
	 */
	public void setRelationship(Relationship relationship)
	{
		this.relationship = relationship;
		targetPrimaryKey = relationship.getPrimaryKeyTable();
		oldSourceForeignKey = relationship.getForeignKeyTable();
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	public void undo()
	{
		sourceForeignKey.removeForeignKeyRelationship(relationship);
		relationship.setForeignKeyTable(oldSourceForeignKey);
		oldSourceForeignKey.addForeignKeyRelationship(relationship);
	}
}
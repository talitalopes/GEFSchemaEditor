package com.realpersist.gef.action;

import org.eclipse.gef.ui.actions.ActionBarContributor;
import org.eclipse.gef.ui.actions.DeleteRetargetAction;
import org.eclipse.gef.ui.actions.RedoRetargetAction;
import org.eclipse.gef.ui.actions.UndoRetargetAction;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import com.realpersist.gef.editor.SchemaDiagramEditor;

/**
 * Contributes actions to the Editor
 * 
 * @author Phil Zoio
 */
public class SchemaActionBarContributor extends ActionBarContributor
{

	FlyoutChangeLayoutAction changeLayoutAction;
	IEditorPart editor;
	
	protected void buildActions()
	{
		addRetargetAction(new UndoRetargetAction());
		addRetargetAction(new RedoRetargetAction());
		addRetargetAction(new DeleteRetargetAction());
		buildChangeLayoutAction();
		addAction(changeLayoutAction);
	}

	public void contributeToToolBar(IToolBarManager toolBarManager)
	{
		toolBarManager.add(getAction(IWorkbenchActionConstants.UNDO));
		toolBarManager.add(getAction(IWorkbenchActionConstants.REDO));
		toolBarManager.add(changeLayoutAction);
	}

	private void buildChangeLayoutAction()
	{
		changeLayoutAction = new FlyoutChangeLayoutAction(editor);
		changeLayoutAction.setToolTipText("Automatic Layout");
		changeLayoutAction.setId("com.realpersist.gef.action.ChangeLayoutAction");
		changeLayoutAction.setImageDescriptor(create("icons/", "layout.gif"));
		changeLayoutAction.setDisabledImageDescriptor(create("icons/", "layout_disabled.gif"));
	}

	public void setActiveEditor(IEditorPart editor)
	{
		this.editor = editor;
		SchemaDiagramEditor schemaEditor = (SchemaDiagramEditor) editor;
		changeLayoutAction.setActiveEditor(editor);
		super.setActiveEditor(editor);
		
	}

	protected void declareGlobalActionKeys()
	{
		//add support for printing
		addGlobalActionKey(IWorkbenchActionConstants.PRINT);
	}

	private static ImageDescriptor create(String iconPath, String name)
	{
		return AbstractUIPlugin.imageDescriptorFromPlugin("com.realpersist.gef.schemaeditor", iconPath + name);
	}



}
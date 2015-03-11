package com.ensoftcorp.open.tinkeratlas.actions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.tinkerpop.gremlin.structure.io.graphml.GraphMLWriter;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class ExportAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public ExportAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {
		// Get a save path from the user
		String file = chooseFile();
		// User cancelled
		if(file == null) return;
		
		FileWriter fWriter = null;
		try {
			fWriter = new FileWriter(file);
		}catch (IOException e) {
			return; // TODO log error
		}
		GraphMLWriter.Builder builder = GraphMLWriter.build();
		configureBuilder(builder);
		
		GraphMLWriter writer = builder.create();
		
	}

	private void configureBuilder(GraphMLWriter.Builder builder){
		// TODO
	}
	
	private String chooseFile(){
	    FileDialog dialog = new FileDialog(window.getShell(), SWT.SAVE);
	    dialog.setFilterNames(new String[] { "GraphML Files", "All Files (*.*)" });
	    dialog.setFilterExtensions(new String[] { "*.xml", "*.*" });
	    dialog.setFileName("atlas_"+System.currentTimeMillis()+".xml");
	    return dialog.open();
	}
	
	public void selectionChanged(IAction action, ISelection selection) {}
	public void dispose() {}
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}
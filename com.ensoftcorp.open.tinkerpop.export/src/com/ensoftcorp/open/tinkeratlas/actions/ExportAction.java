package com.ensoftcorp.open.tinkeratlas.actions;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.ensoftcorp.atlas.core.db.graph.GraphElement;
import com.ensoftcorp.atlas.core.db.set.AtlasSet;
import com.ensoftcorp.open.tinkeratlas.Log;
import com.ensoftcorp.open.tinkeratlas.tinkerpop.AtlasGraph;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
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
		
		try {
			FileOutputStream fStream = new FileOutputStream(file);
			GraphMLWriter.Builder builder = GraphMLWriter.build();
			configureBuilder(builder);
			GraphMLWriter writer = builder.create();
			writer.writeGraph(fStream, new AtlasGraph());
		}catch (IOException e) {
			Log.error("",e);
		}
	}

	private void configureBuilder(GraphMLWriter.Builder builder){
		builder.vertexLabelKey("labelV");
		builder.edgeLabelKey("labelE");
		builder.normalize(true);
		builder.edgeKeyTypes(attrTypes(com.ensoftcorp.atlas.core.db.graph.Graph.U.edges()));
		builder.vertexKeyTypes(attrTypes(com.ensoftcorp.atlas.core.db.graph.Graph.U.nodes()));
	}
	
	private Map<String, String> attrTypes(AtlasSet<GraphElement> elements){
		Map<String, String> attrTypes = new HashMap<String, String>();
		for(GraphElement ge : com.ensoftcorp.atlas.core.db.graph.Graph.U.nodes()){
			for(String s : ge.attr().keys()){
				if(attrTypes.containsKey(s)) continue;
				Class<?> vClass = ge.getAttr(s).getClass();
				
				if(String.class.equals(vClass)){
					attrTypes.put(s, "string");
				}else if(Boolean.class.equals(vClass)){
					attrTypes.put(s, "boolean");
				}else if(Integer.class.equals(vClass)){
					attrTypes.put(s, "int");
				}else if(Long.class.equals(vClass)){
					attrTypes.put(s, "long");
				}else if(Float.class.equals(vClass)){
					attrTypes.put(s, "float");
				}else if(Double.class.equals(vClass)){
					attrTypes.put(s, "double");
				}else if(List.class.isAssignableFrom(vClass)){
					attrTypes.put(s, "list");
				}else if(Map.class.isAssignableFrom(vClass)){
					attrTypes.put(s, "map");
				}
			}
		}
		return attrTypes;
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
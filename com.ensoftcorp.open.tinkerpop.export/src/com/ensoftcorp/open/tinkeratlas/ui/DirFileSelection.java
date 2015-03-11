package com.ensoftcorp.open.tinkeratlas.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class DirFileSelection {

  public static void main(String[] args) {
	    Display display = new Display();
	    FileDialog dialog = new FileDialog(new Shell(display), SWT.SAVE);
	    dialog.setFilterNames(new String[] { "GraphML Files", "All Files (*.*)" });
	    dialog.setFilterExtensions(new String[] { "*.ml", "*.*" });
	    dialog.setFileName("tinker_atlas"+System.currentTimeMillis()+".ml");
	    String file = dialog.open();
  }
}

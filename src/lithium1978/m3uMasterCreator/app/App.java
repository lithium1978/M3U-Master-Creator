package lithium1978.m3uMasterCreator.app;

import javax.swing.SwingUtilities;

import lithium1978.m3uMasterCreator.gui.GUICreator;

public class App {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUICreator();
			}
		});	
	}

}
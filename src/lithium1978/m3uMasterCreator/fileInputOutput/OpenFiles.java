package lithium1978.m3uMasterCreator.fileInputOutput;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

public class OpenFiles {
	
	public File populateOpenFile(JButton button, JTextField text) {
		
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new java.io.File("C:/Users"));
		fc.setDialogTitle("File Browser.");
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		if (fc.showOpenDialog(button) == JFileChooser.APPROVE_OPTION){
			text.setText(fc.getSelectedFile().getAbsolutePath());
		}
		return fc.getSelectedFile();
	}
}

package lithium1978.m3uMasterCreator.gui;


import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;


import lithium1978.m3uMasterCreator.backendData.*;
import lithium1978.m3uMasterCreator.controller.GroupTitleController;
import lithium1978.m3uMasterCreator.fileInputOutput.FileLogger;
import lithium1978.m3uMasterCreator.model.GroupTitleTableModel;

public class GroupTable extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2094247430837696261L;
	private JTable groupTable;
	private GroupTitleTableModel groupModel;
	private JPopupMenu popup;
	private JMenuItem copyItem;
	private JMenuItem pasteItem;
	private JFileChooser fileChooser;
	private GroupTitleController groupController;
	

	public GroupTable() {

		fileChooser = new JFileChooser();
		groupController = new GroupTitleController();
		groupModel = new GroupTitleTableModel();
		groupTable = new JTable(groupModel);
		popup = new JPopupMenu();
		groupTable.setCellSelectionEnabled(true);

		copyItem = new JMenuItem("copy");
		pasteItem = new JMenuItem("paste");

		copyItem.addActionListener(this);
		pasteItem.addActionListener(this);		

		popup.add(copyItem);
		popup.add(pasteItem);

		groupTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() ==  MouseEvent.BUTTON3) {
					popup.show(groupTable, e.getX(), e.getY());
				}
			}
		});
		
		JToolBar toolbar = new JToolBar();
		
		JButton btnSave = new JButton("Save");
		toolbar.add(btnSave);
		JButton btnLoad = new JButton("Load");
		toolbar.add(btnLoad);

		setLayout (new BorderLayout());
		add(toolbar, BorderLayout.PAGE_START);
		add(new JScrollPane(groupTable), BorderLayout.CENTER);
		groupTable.setAutoCreateRowSorter(true);
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent btnSave) {

				if (fileChooser.showSaveDialog(GroupTable.this) == JFileChooser.APPROVE_OPTION) {
					try {
						groupController.saveToFile(fileChooser.getSelectedFile());
						JOptionPane.showMessageDialog(GroupTable.this, "File save complete");
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(GroupTable.this,
								"Could not save data to file.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent btnLoad) {

				if (fileChooser.showOpenDialog(GroupTable.this) == JFileChooser.APPROVE_OPTION) {
					try {
						groupController.loadFromFile(fileChooser.getSelectedFile());
						refresh();
						System.out.println("test from importData Item " +  GroupTitleController.getGroupTitles());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(GroupTable.this,"Could not load data from file.", "Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}	




	public void setData(List<GroupTitle> groups) {
		Collections.sort(groups);
		groupModel.setData(groups);
	}

	public JTable getTable() {
		return groupTable;
	}

	public void refresh() {
		groupModel.fireTableDataChanged();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JMenuItem menu = (JMenuItem) event.getSource();
		if (menu == copyItem) {
			copyItem();
		} else if (menu == pasteItem) {
			pasteItem();
		}
	}

	public void copyItem() {

		//		System.out.println(table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
		String copiedString = (String) groupTable.getValueAt(groupTable.getSelectedRow(), groupTable.getSelectedColumn());
		StringSelection stringSelection = new StringSelection(copiedString);
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(stringSelection, null);
	}


	public void pasteItem() {

		Clipboard groupc = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable groupt = groupc.getContents(this);
		if (groupt == null)
			return;
		try {		
			String groupTest = (String) groupt.getTransferData(DataFlavor.stringFlavor);
			System.out.println(groupTest);
			groupTable.setValueAt(groupTest, groupTable.getSelectedRow(), groupTable.getSelectedColumn());
			String testing = (String) groupModel.getValueAt(0, 1);
			System.out.println("Testing value pasted " + testing);
			
		} catch (Exception e){
			FileLogger.logData(LocalDateTime.now() + " Error attempting to paste data to tablee " + e + System.lineSeparator()); 	
			e.printStackTrace();
		}//try
	}//onPaste
	
	
}


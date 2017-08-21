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
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import lithium1978.m3uMasterCreator.backendData.*;

public class GroupTable extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2094247430837696261L;
	private JTable groupTable;
	private GroupTableModel groupModel;
	private JPopupMenu popup;
	private JMenuItem copyItem;
	private JMenuItem pasteItem;

	public GroupTable() {

		groupModel = new GroupTableModel();
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

		setLayout (new BorderLayout());

		add(new JScrollPane(groupTable), BorderLayout.CENTER);
		groupTable.setAutoCreateRowSorter(true);
	}	




	public void setData(List<GroupTitle> groups) {
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


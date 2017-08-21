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

public class ChannelTable extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3197125641080529592L;
	private JTable table;
	private ChannelTableModel channelModel;
	private JPopupMenu popup;
	final String popupLocation = "table.popupLocation";
	private JMenuItem copyItem;
	private JMenuItem pasteItem;

	public ChannelTable() {
		channelModel = new ChannelTableModel();
		table = new JTable(channelModel);
		popup = new JPopupMenu();
		table.setCellSelectionEnabled(true);

		copyItem = new JMenuItem("copy");
		pasteItem = new JMenuItem("paste");

		copyItem.addActionListener(this);
		pasteItem.addActionListener(this);		

		popup.add(copyItem);
		popup.add(pasteItem);

		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if(e.getButton() ==  MouseEvent.BUTTON3) {
					popup.show(table, e.getX(), e.getY());
				}
			}

		});



		setLayout (new BorderLayout());

		add(new JScrollPane(table), BorderLayout.CENTER);
		table.setAutoCreateRowSorter(true);
	}
	public void setData(List<Channel> cd) {
		channelModel.setData(cd);
	}

	public JTable getTable() {
		return table;
	}

	public void refresh() {
		channelModel.fireTableDataChanged();
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
		String copiedString = (String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
		StringSelection stringSelection = new StringSelection(copiedString);
		Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
		clpbrd.setContents(stringSelection, null);
	}


	public void pasteItem() {

		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable t = c.getContents(this);
		if (t == null)
			return;
		try {
			String test = (String) t.getTransferData(DataFlavor.stringFlavor);
			table.setValueAt(test, table.getSelectedRow(), table.getSelectedColumn());
		} catch (Exception e){
			FileLogger.logData(LocalDateTime.now() + " Error attempting to paste data to tablee " + e + System.lineSeparator()); 	
			e.printStackTrace();
		}//try
	}//onPaste
}


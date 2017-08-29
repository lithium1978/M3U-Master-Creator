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
import java.util.ArrayList;
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
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import lithium1978.m3uMasterCreator.backendData.*;
import lithium1978.m3uMasterCreator.controller.ChannelController;
import lithium1978.m3uMasterCreator.fileInputOutput.FileLogger;
import lithium1978.m3uMasterCreator.model.ChannelTableModel;

public class ChannelTable extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3197125641080529592L;
	private JTable table;
	private ChannelTableModel channelModel;
	private JPopupMenu popup;
	private JMenuItem copyItem;
	private JMenuItem pasteItem;
	private JFileChooser fileChooser;
	private ChannelController channelController;
	

	public ChannelTable() {
		
		fileChooser = new JFileChooser();
		channelController = new ChannelController();
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

		JToolBar toolbar = new JToolBar();
		
		JButton btnChanSave = new JButton("Save");
		toolbar.add(btnChanSave);
//		JButton btnChanLoad = new JButton("Load");
//		toolbar.add(btnChanLoad);

		setLayout (new BorderLayout());
		add(toolbar, BorderLayout.PAGE_START);
		add(new JScrollPane(table), BorderLayout.CENTER);
		table.setAutoCreateRowSorter(true);
		
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
					
		int columnIndexForGroupTitle = 6;
		sortKeys.add(new RowSorter.SortKey(columnIndexForGroupTitle, SortOrder.ASCENDING));
		 
		int columnIndexForTvgName = 2;
		sortKeys.add(new RowSorter.SortKey(columnIndexForTvgName, SortOrder.ASCENDING));
		
		sorter.setSortKeys(sortKeys);
		sorter.sort();
		
		btnChanSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent btnChanSave) {
				
				channelController.updateDB();

//				if (fileChooser.showSaveDialog(ChannelTable.this) == JFileChooser.APPROVE_OPTION) {
//					try {
//						channelController.saveToFile(fileChooser.getSelectedFile());
//						JOptionPane.showMessageDialog(ChannelTable.this, "File save complete");
//					} catch (IOException e1) {
//						JOptionPane.showMessageDialog(ChannelTable.this,
//								"Could not save data to file.", "Error",
//								JOptionPane.ERROR_MESSAGE);
//					}
//				}
			}
		});
	}
//		btnChanLoad.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent btnChanLoad) {
//
//				if (fileChooser.showOpenDialog(ChannelTable.this) == JFileChooser.APPROVE_OPTION) {
//					try {
//						channelController.loadFromFile(fileChooser.getSelectedFile());
//						refresh();
//						System.out.println("test from importData Item " +  ChannelController.getChannels());
//					} catch (IOException e1) {
//						JOptionPane.showMessageDialog(ChannelTable.this,"Could not load data from file.", "Error",JOptionPane.ERROR_MESSAGE);
//					}
//				}
//			}
//		});
//		
//	}
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


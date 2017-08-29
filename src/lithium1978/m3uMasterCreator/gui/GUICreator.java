package lithium1978.m3uMasterCreator.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.DefaultEditorKit;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;


import lithium1978.m3uMasterCreator.backendData.*;
import lithium1978.m3uMasterCreator.controller.*;
import lithium1978.m3uMasterCreator.database.Database;
import lithium1978.m3uMasterCreator.fileInputOutput.FileLogger;
import lithium1978.m3uMasterCreator.fileInputOutput.OpenFiles;
import lithium1978.m3uMasterCreator.fileInputOutput.WriteTempFile;


@SuppressWarnings("serial")
public class GUICreator extends JFrame {
	

	JPanel checkPanel;
	JPanel contentPane;
	ChannelTable channelTable;
	JPanel resultPan;
	GroupTable groupTable;
	JTextField textOpen2;
	JTextField textOpen1;
	JTextField textOpen3;
	boolean appendData = false;
	int lineNum = 0;
	JFileChooser fileChooser;
	GroupTitleController groupController;
	ArrayList<String> checkBoxLabels = new ArrayList<>();
	ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

	File retFile;
	File openFile1;
	File openFile2;
	File openFile3;
	boolean hasRun;
	
	public GUICreator() {
	
//	
//	JFrame frame;

	


//	public void createGUI() {
		
	try {
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				UIManager.setLookAndFeel(info.getClassName());
				break;
			}
		}
	} catch (Exception e) {
		// If Nimbus is not available, you can set the GUI to another look and feel.
	}
		
		this.setTitle("M3U Master Creator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 700);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		JPanel filterBox = new JPanel();
		GridBagConstraints gbc_FilterBox = new GridBagConstraints();
		gbc_FilterBox.fill=GridBagConstraints.BOTH;
		gbc_FilterBox.gridx=0;
		gbc_FilterBox.gridy=0;
		filterBox.setLayout(new BorderLayout(0,0));
		contentPane.add(filterBox, gbc_FilterBox);

		JPanel ioPanel = new JPanel();
		ioPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Merge/Save Options", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0,0,0)));
		filterBox.add(ioPanel);		

		GridBagLayout gbl_ioPanel = new GridBagLayout();
		gbl_ioPanel.columnWidths = new int[]{89, 0};
		gbl_ioPanel.rowHeights = new int[]{23, 23, 0};
		gbl_ioPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_ioPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		ioPanel.setLayout(gbl_ioPanel);

		JPanel openPanel = new JPanel();
		openPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Open File(s)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		filterBox.add(openPanel, BorderLayout.WEST);
		GridBagLayout gbl_openPanel = new GridBagLayout();
		gbl_openPanel.columnWidths = new int[]{89, 0};
		gbl_openPanel.rowHeights = new int[]{23, 23, 0};
		gbl_openPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_openPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		openPanel.setLayout(gbl_openPanel);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridwidth = java.awt.GridBagConstraints.RELATIVE;
		gbc_tabbedPane.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.weightx = 1.0;
		gbc_tabbedPane.weighty = 5.0;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 1;
		contentPane.add(tabbedPane, gbc_tabbedPane);

		JScrollPane groupPane = new JScrollPane();
		tabbedPane.addTab("Group Filters", null, groupPane, null);
		
		fileChooser = new JFileChooser();
		groupController = new GroupTitleController();
		channelTable = new ChannelTable();

		//creates file one label
		JLabel lblFileOne = new JLabel("File One: ");
		GridBagConstraints gbc_lblFileOne = new GridBagConstraints();
		gbc_lblFileOne.ipadx = 3;
		gbc_lblFileOne.insets = new Insets(0, 0, 5, 0);
		gbc_lblFileOne.gridx = 0;
		gbc_lblFileOne.gridy = 0;
		openPanel.add(lblFileOne,gbc_lblFileOne);

		//creates file one text field
		textOpen1 = new JTextField();
		textOpen1.setToolTipText("Enter URL or click button to select file");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 2;
		gbc_textField.anchor = GridBagConstraints.EAST;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		openPanel.add(textOpen1, gbc_textField);
		textOpen1.setColumns(25);

		//creates file one open button
		JButton btnFileOne= new JButton("...");
		GridBagConstraints gbc_btnFileOne = new GridBagConstraints();
		gbc_btnFileOne.insets = new Insets(0, 0, 5, 0);
		gbc_btnFileOne.gridwidth = 0;
		gbc_btnFileOne.gridheight = 0;
		gbc_btnFileOne.anchor = GridBagConstraints.NORTH;
		gbc_btnFileOne.gridx = 3;
		gbc_btnFileOne.gridy = 0;
		openPanel.add(btnFileOne, gbc_btnFileOne);

		// creates file two label
		JLabel lblFileTwo = new JLabel("File Two: ");
		GridBagConstraints gbc_lbFileTwo = new GridBagConstraints();
		gbc_lbFileTwo.ipadx = 3;
		gbc_lbFileTwo.insets = new Insets(0, 0, 5, 0);
		gbc_lbFileTwo.gridx = 0;
		gbc_lbFileTwo.gridy = 1;
		openPanel.add(lblFileTwo,gbc_lbFileTwo);

		//creates file two textfield
		textOpen2 = new JTextField();
		textOpen2.setToolTipText("Enter URL or click button to select file");
		GridBagConstraints gbc_textField2 = new GridBagConstraints();
		gbc_textField2.gridwidth = 2;
		gbc_textField2.anchor = GridBagConstraints.EAST;
		gbc_textField2.insets = new Insets(0, 0, 5, 0);
		gbc_textField2.gridx = 1;
		gbc_textField2.gridy = 1;
		openPanel.add(textOpen2, gbc_textField2);
		textOpen2.setColumns(25);

		//creates file two button
		JButton btnFileTwo= new JButton("...");
		GridBagConstraints gbc_btnFileTwo = new GridBagConstraints();
		gbc_btnFileTwo.insets = new Insets(0, 0, 5, 0);
		gbc_btnFileTwo.gridwidth = 0;
		gbc_btnFileTwo.gridheight = 0;
		gbc_btnFileTwo.anchor = GridBagConstraints.NORTH;
		gbc_btnFileTwo.gridx = 3;
		gbc_btnFileTwo.gridy = 1;
		openPanel.add(btnFileTwo, gbc_btnFileTwo);

		//creats file three label
		JLabel lblFileThree = new JLabel("File Three: ");
		GridBagConstraints gbc_lblFileThree = new GridBagConstraints();
		gbc_lblFileThree.ipadx = 0;
		gbc_lblFileThree.anchor= GridBagConstraints.NORTH;
		gbc_lblFileThree.insets = new Insets(0, 0, 5, 0);
		gbc_lblFileThree.gridx = 0;
		gbc_lblFileThree.gridy = 2;
		openPanel.add(lblFileThree,gbc_lblFileThree);

		//creats file 3 textfield
		textOpen3 = new JTextField();
		textOpen3.setToolTipText("Enter URL or click button to select file");
		GridBagConstraints gbc_textField3 = new GridBagConstraints();
		gbc_textField3.gridwidth = 2;
		gbc_textField3.anchor = GridBagConstraints.NORTH;
		gbc_textField3.insets = new Insets(0, 0, 5, 0);
		gbc_textField3.gridx = 1;
		gbc_textField3.gridy = 2;
		openPanel.add(textOpen3, gbc_textField3);
		textOpen3.setColumns(25);

		//creates file three button
		JButton btnFileThree= new JButton("...");
		GridBagConstraints gbc_btnFileThree = new GridBagConstraints();
		gbc_btnFileThree.insets = new Insets(0, 0, 5, 0);
		gbc_btnFileThree.gridwidth = 0;
		gbc_btnFileThree.gridheight = 0;
		gbc_btnFileThree.anchor = GridBagConstraints.NORTH;
		gbc_btnFileThree.gridx = 3;
		gbc_btnFileThree.gridy = 2;
		openPanel.add(btnFileThree, gbc_btnFileThree);

		//creates open file button
		JButton btnAnalyze= new JButton("Open File(s)");
		btnAnalyze.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_btnAnalyze = new GridBagConstraints();
		gbc_btnAnalyze.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnAnalyze.insets = new Insets(0, 0, 5, 5);
		gbc_btnAnalyze.gridx = 0;
		gbc_btnAnalyze.gridy = 0;
		ioPanel.add(btnAnalyze, gbc_btnAnalyze);


		//creates apply group filter button
		JButton btnMerge = new JButton ("Apply Group Filter");
		btnMerge.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_btnMerge = new GridBagConstraints();
		gbc_btnMerge.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnMerge.insets = new Insets(0, 0, 5, 0);
		gbc_btnMerge.gridx = 0;
		gbc_btnMerge.gridy = 1;
		ioPanel.add(btnMerge, gbc_btnMerge);

		//creates apply channel filter button
		JButton btnSave= new JButton ("Apply Chan Filter and Save");
		btnMerge.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSave.insets = new Insets(0, 0, 0, 0);
		gbc_btnSave.gridx = 0;
		gbc_btnSave.gridy = 2;
		ioPanel.add(btnSave, gbc_btnSave);
		
		//creates donate button
		JButton btnDonate = new JButton("Donate");
		btnMerge.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_btnDonate = new GridBagConstraints();
		gbc_btnDonate.anchor = GridBagConstraints.SOUTHWEST;
		gbc_btnDonate.insets = new Insets(0,0,0,0);
		gbc_btnDonate.gridx=0;
		gbc_btnDonate.gridy=3;
		ioPanel.add(btnDonate, gbc_btnDonate);
		
	      JPopupMenu popup = new JPopupMenu();
	      JMenuItem item = new JMenuItem(new DefaultEditorKit.CutAction());
	      item.setText("Cut");
	      popup.add(item);
	      item = new JMenuItem(new DefaultEditorKit.CopyAction());
	      item.setText("Copy");
	      popup.add(item);
	      item = new JMenuItem(new DefaultEditorKit.PasteAction());
	      item.setText("Paste");
	      popup.add(item);
	      textOpen1.setComponentPopupMenu(popup);
	      textOpen2.setComponentPopupMenu(popup);
	      textOpen3.setComponentPopupMenu(popup);

		setJMenuBar(createMenuBar());
		

		groupTable = new GroupTable();
		tabbedPane.addTab("Group-Title Replacements", null, groupTable, null);
//		groupTable.setData(GroupTitleController.getGroupTitles());
		groupTable.setData(GroupTitleController.pullFromDB());
		System.out.println("testing from GUICreator" + GroupTitleController.getGroupTitles());
				
//		try {
//			File groupAutoSaveData = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"/AutoSave_GTData.gfd");
//			groupController.loadFromFile(groupAutoSaveData);
//				 
//		}catch (IOException e2) {
//			FileLogger.logData(LocalDateTime.now() + "GroupTitle autosave data not found" );
//		}	
//
		groupTable.refresh();
		
		tabbedPane.addTab("Channel Filters", null,channelTable, null);
		channelTable.setData(ChannelController.getChannels());
		channelTable.refresh();
		
		setVisible(true);
		
	

		//Listener for textOpen1 button
		btnFileOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent text1) {             

				OpenFiles of = new OpenFiles();
				retFile = of.populateOpenFile(btnFileOne, textOpen1);
				textOpen1.setText(retFile.getAbsolutePath());
				openFile1 = retFile;
			}
		});

		//Listener for textOpen2 button
		btnFileTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {             
				OpenFiles of = new OpenFiles();
				retFile = of.populateOpenFile(btnFileOne, textOpen1);
				textOpen2.setText(retFile.getAbsolutePath());
				openFile2 = retFile;
			}
		});

		//Listener for textOpen3 button
		btnFileThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {             
				OpenFiles of = new OpenFiles();
				retFile = of.populateOpenFile(btnFileOne, textOpen1);
				textOpen3.setText(retFile.getAbsolutePath());
				openFile3 = retFile;
			}
		});

		//Listener for Analyze button
		btnAnalyze.addActionListener(new ActionListener() {	

			@Override
			public void actionPerformed(ActionEvent text1) {
				JOptionPane.showMessageDialog(GUICreator.this, "This process may take a while, it depends on how quickly the file downloads and is processed from your host(s). " + System.lineSeparator() + " It is recommended that you load all files in the beginning and start a new instance of the application if additional files need to be edited.");

				
				String fileTest = textOpen1.getText().substring(0,4);
				if(fileTest.equals("http")){
					readFromURL(textOpen1.getText());
				} else {	
					mergeFiles(textOpen1.getText());
					}

				if(textOpen2.getText().length() > 0){
					if (textOpen2.getText().substring(0,4).equals("http")){
						readFromURL(textOpen2.getText());
					} else {
						mergeFiles(textOpen2.getText());
						}
				}

				if(textOpen3.getText().length() > 0){
					if(textOpen3.getText().substring(0,4).equals("http")){
						readFromURL(textOpen3.getText());
					} else {
						mergeFiles(textOpen3.getText());
						}
				}

				// displays message and then adds checkPanel to the groupPane view
				JOptionPane.showMessageDialog(GUICreator.this, "File Analysis complete. ");
				textOpen1.setText("");
				textOpen2.setText("");
				textOpen3.setText("");
				
				CheckBoxArray.analyzeFile();
				groupController.loadToDb();
				JPanel checkPanel = createCheckBoxes();
				groupPane.setViewportView(checkPanel);
				contentPane.validate();
				contentPane.repaint();
				groupTable.setData(GroupTitleController.pullFromDB());
				groupTable.refresh();
			}
		});

		//Listener for Merge button
		btnMerge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				WriteTempFile.checkBoxStatus(checkBoxes, checkBoxLabels);
//				GatherChannelData.getChannels();
				if(hasRun != true) {
					WriteTempFile.checkBoxStatus(checkBoxes, checkBoxLabels);
//					tabbedPane.addTab("Channel Filters", null,channelTable, null);
					ChannelController.loadToDb();
					channelTable.setData(ChannelController.pullFromDB());
					channelTable.refresh();
				}

				if(hasRun == true) {
				JOptionPane.showMessageDialog(GUICreator.this, "This application can currently only support a single application of filter(s) per run.");
				}
				tabbedPane.setSelectedIndex(2);
				hasRun = true;
			}
		});
		
		//Listener for Save button
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SelectedChannels sc = new SelectedChannels();
				sc.getData();
				try { 	
					File groupTitleSave =  new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"/AutoSave_GTData.gfd"); 
					groupController.saveToFile(groupTitleSave);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//Listener for Donate button
		btnDonate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				DonateButtonLogic.openBrowser();
			}
		});
	
	}
		
	private void readFromURL (String strVal) {
		boolean appendData=(false);
		int lineNum = 0;
		
		try 
		{
			URL url = new URL (strVal);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			FileWriter fw = new FileWriter(FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"/Temp_FileReader.txt",appendData);
			BufferedWriter bw = new BufferedWriter(fw); 
			String line;

			if (lineNum == 0) 
			{
				line = in.readLine();
				bw.write(line);
				bw.newLine();
				lineNum++;
			} else if (lineNum > 0)
			{
				line = in.readLine();
			}

			while((line=in.readLine()) != null)
			{
				System.out.println(line);
				bw.write(line);
				bw.newLine();
				lineNum++;	
				appendData = true;
			}
			in.close();
			bw.close();

		} catch (MalformedURLException e) 
		{
			System.out.println("Malformed URL: " + e.getMessage());
		}
		catch (IOException e) 
		{
			System.out.println("I/O Error: " + e.getMessage());
		}

	}

	private JPanel mergeFiles(String file) {

		String writeLine;

		if(file.length() == 0) {
			JOptionPane.showMessageDialog(this, "Error file passed to mergeFiles is invalid. ");
			return checkPanel;
		}

		try {	FileReader in = new FileReader(file);
				BufferedReader reader = new BufferedReader(in);
				FileWriter fw = new FileWriter(FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"/Temp_FileReader.txt",appendData);
				BufferedWriter bw = new BufferedWriter(fw); 

			if(file.length() > 0) {
				if (lineNum < 1) {
					writeLine = reader.readLine();
					bw.write(writeLine);
					bw.newLine();
					lineNum++;
				} else if (lineNum > 0)
				{
					reader.readLine();
				}

				while((writeLine=reader.readLine()) != null)
				{
					System.out.println(writeLine);
					bw.write(writeLine);
					bw.newLine();
					lineNum++;	
					appendData = true;
				}
				in.close();
				bw.close();
			} 

		}catch (IOException e) {
			JOptionPane.showMessageDialog(this, e);
			e.printStackTrace();	
		}
		return checkPanel;
	}
	
	private JPanel createCheckBoxes() {

		JPanel checkBoxP = new JPanel(new GridLayout(60,4));

		String labels[]=CheckBoxArray.getCurrentIDs().toArray(new String[CheckBoxArray.getGroupIDs().size()]);   


		for (int i = 0; i < CheckBoxArray.getCurrentIDs().size(); i++) {

			//creates Jcheckbox with title from getGroupIDs arraylist.  
			JCheckBox checkbox = new JCheckBox(labels[i],false);   
			JCheckBox chbx = checkbox;
			checkBoxP.add(chbx);
			String str = checkbox.getText();
			checkBoxLabels.add(str);
			checkBoxes.add(chbx);
			
		} //end for loop

		return checkBoxP;

	}  // end createCheckBoxes
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem exportDataItem = new JMenuItem("Save GroupTitle Data...");
		JMenuItem importDataItem = new JMenuItem("Load GroupTitle Data...");
		JMenuItem exitItem = new JMenuItem("Exit");

		fileMenu.add(exportDataItem);
		fileMenu.add(importDataItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		menuBar.add(fileMenu);

		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setMnemonic(KeyEvent.VK_X);

		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.CTRL_MASK));

		importDataItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showOpenDialog(GUICreator.this) == JFileChooser.APPROVE_OPTION) {
					try {
						groupController.loadFromFile(fileChooser.getSelectedFile());
						groupTable.refresh();
						System.out.println("test from importData Item " +  GroupTitleController.getGroupTitles());
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(GUICreator.this,"Could not load data from file.", "Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		exportDataItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fileChooser.showSaveDialog(GUICreator.this) == JFileChooser.APPROVE_OPTION) {
					try {
						groupController.saveToFile(fileChooser.getSelectedFile());
						JOptionPane.showMessageDialog(GUICreator.this, "File save complete");
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(GUICreator.this,
								"Could not save data to file.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int action = JOptionPane.showConfirmDialog(GUICreator.this,
						"Do you really want to exit the application?",
						"Confirm Exit", JOptionPane.OK_CANCEL_OPTION);

				if (action == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});

		return menuBar;
	}

//	public static void main(String[] args) 
//	{
//		EventQueue.invokeLater(new Runnable() 
//		{
//			public void run() 
//			{
//				try 
//				{
//					GUICreator test = new GUICreator();
//					test.createGUI();
//
//				} catch (Exception e) 
//				{
//					e.printStackTrace();
//				}
//			}
//		});
//	}
}

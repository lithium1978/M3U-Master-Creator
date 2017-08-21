package lithium1978.m3uMasterCreator.fileInputOutput;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import lithium1978.m3uMasterCreator.backendData.*;

public class WriteTempFile {

	static File saveFile;
	static JFrame frame = new JFrame();	
	static ArrayList<String> selectedCriteria = new ArrayList<>();
	static int totalLines = 0;
	static Boolean writeStat = false;
	static int writeIndex = 0;
	static int criteriaCount = 0;

	static void checkBoxStatus (ArrayList<JCheckBox> checkBoxes2, ArrayList<String> checkBoxLab) {

		for(int i = 0; i< checkBoxes2.size(); i++) {

			if (checkBoxes2.get(i).isSelected()){
				String filter = checkBoxLab.get(i);
				selectedCriteria.add(filter);
			} 
		}// end for loop
		writeTempFile();
		GatherChannelData chandat = new GatherChannelData();
		chandat.startReader();
	}  // end checkBoxStatus

	public void saveFile(ArrayList<String> selectedValues, ArrayList<String> updatedTVGids, ArrayList<String> updatedGroupTitles) {

		if(selectedValues.size()<1) {
			JOptionPane.showMessageDialog(frame, "No filter options selected.");
			return;		}

		JOptionPane.showMessageDialog(frame, "Select save directory and enter file name followed by .m3u" + System.lineSeparator() + "WARNING: Selecting an existing file will overwrite that file!");  //Display dialog with file save instructions
		JFileChooser fc = new JFileChooser();
		fc.showSaveDialog(frame);
		//sets variable saveFile to value selected by user
		saveFile = fc.getSelectedFile();  

		try{ 	
			//Determines if saveFile already exists
			if(!saveFile.exists()){    	
				//If file does not exist create
				saveFile.createNewFile();  				
			}
			FileLogger.logData(LocalDateTime.now() + " Data saved to file " + saveFile + System.lineSeparator()); 			 
			finalProcessing(fc.getSelectedFile(), selectedValues, updatedTVGids, updatedGroupTitles);   

		}catch(IOException ioe){
			JOptionPane.showMessageDialog(frame, ioe);	
			ioe.printStackTrace();
		}
	}  // end saveFile
	
	private static void writeTempFile() {
		try (FileReader in = new FileReader(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Temp_FileReader.txt");
				BufferedReader reader = new BufferedReader(in);
				FileWriter fw = new FileWriter(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Temp_FilteredFile.txt", false);
				BufferedWriter bw = new BufferedWriter(fw);)
		{
			String lineToWrite;
			int linesWritten = 0;
			Boolean writeBool = false;

			while ((lineToWrite = reader.readLine()) != null){
				if (linesWritten < 1) {
					bw.write(lineToWrite);
					bw.newLine();
					linesWritten++;
				} else {
					if ((writeBool == true)) {
						bw.write(lineToWrite);
						bw.newLine();
						writeBool = false;
						linesWritten ++;
					}
				}
				for(int c = 0; c < selectedCriteria.size(); c++){
					String filter = "group-title=\"" + selectedCriteria.get(c);
					if(filter.equals("group-title=\"")){
						filter = "group-title=\"\"";							
					}

					int loc = lineToWrite.indexOf(filter); 

					if(loc != -1) {
						bw.write(lineToWrite);
						bw.newLine();
						writeBool = true;
						writeIndex ++;
					}
				}
			}
		}	catch (IOException e) {
			JOptionPane.showMessageDialog(frame, e);
			e.printStackTrace();
		}
	}// end writeTempFile
	
	private static void finalProcessing(File file, ArrayList<String> selectedValues, ArrayList<String> updatedTVGids, ArrayList<String> updatedGroupTitles) {

		try (FileReader in = new FileReader(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Temp_FilteredFile.txt");
				BufferedReader reader = new BufferedReader(in);
				FileWriter fw = new FileWriter(saveFile,false);
				BufferedWriter bw = new BufferedWriter(fw);) 
		{
			String writeLine;

			while ((writeLine = reader.readLine()) != null)	{
				if (totalLines < 1) 
				{
					bw.write(writeLine);
					bw.newLine();
					totalLines++;
				} else { 
					if ((writeStat == true)){
						bw.write(writeLine);
						bw.newLine();
						writeStat = false;
						writeIndex ++;
					}else {
						String filter = selectedValues.get(criteriaCount);
//						String filter2 = updatedTVGids.get(criteriaCount);
//						String filter3 = updatedGroupTitles.get(criteriaCount);
						
						if(selectedValues.get(criteriaCount).equals("skip channel")){
							System.out.println("channel skipped");
							reader.readLine();
							criteriaCount++;

//						}else {
//							if(filter.equals("tvg-name=\"")) {
//								filter = "tvg-name=\"No Name";							
							} else {
								if(filter.equals("tvg-name=\"")) {
									filter = "No Name";}	
								int loc = writeLine.indexOf("tvg-name=\"");
								int loclength= "tvg-name=\"".length();
								int loc2 = writeLine.indexOf("tvg-logo=\"");

								loc2 = loc2-2;		
								loc = loc + loclength;

								String newVal = writeLine.substring(loc, loc2);		
								String updateWriteLine = writeLine.replaceFirst(newVal, filter);

								bw.write(updateWriteLine);
								bw.newLine();
								writeStat = true;
								writeIndex ++;
								criteriaCount ++;

							}  //end else loop
						}  //end else
					}	//end else
//				}//end else
			}// end while
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, e);
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(frame, "File write complete. " + (writeIndex/2) + " Lines written to new file.");
		totalLines = 0;
		writeIndex = 0;
		criteriaCount = 0;		
	}  // end finalProcessing
}

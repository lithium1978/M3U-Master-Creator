package lithium1978.m3uMasterCreator.fileInputOutput;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;


public class WriteFile {
	
	static File saveFile;
	static JFrame frame = new JFrame();	
	static ArrayList<String> selectedCriteria = new ArrayList<>();
	static int totalLines = 0;
	static Boolean writeStat = false;
	static int writeIndex = 0;
	static int criteriaCount = 0;
	
	
	
	public void saveFile(ArrayList<String> selectedValues, ArrayList<String> linesToWrite) {

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
			System.out.println("testing selectedValues " + selectedValues.size() + " testing linesToWrite " + linesToWrite.size());
			finalProcessing(fc.getSelectedFile(), selectedValues, linesToWrite);   

		}catch(IOException ioe){
			JOptionPane.showMessageDialog(frame, ioe);	
			ioe.printStackTrace();
		}
	}  // end saveFile
	
	private static void finalProcessing(File file, ArrayList<String> selectedValues, ArrayList<String> linesToWrite) {

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
//						String filter = selectedValues.get(criteriaCount);
						System.out.println("print criteria Count number" + criteriaCount);
						System.out.println("Lines To Write test " + linesToWrite);
						System.out.println("write index " + writeIndex);
						System.out.println("lineToWrite " + linesToWrite.get(criteriaCount));
						String lineToWrite = linesToWrite.get(criteriaCount);
						System.out.println("Line to Write values " + lineToWrite);
//						String filter3 = updatedGroupTitles.get(criteriaCount);
						
						if(selectedValues.get(criteriaCount).equals("skip channel")){
							System.out.println("channel skipped");
							reader.readLine();
							criteriaCount++;

//						}else {
//							if(filter.equals("tvg-name=\"")) {
//								filter = "tvg-name=\"No Name";							
							} else {
								writeLine = lineToWrite;
								
								bw.write(writeLine);
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
			JFrame newFrame = new JFrame();
			JOptionPane.showMessageDialog(frame, e);
			JOptionPane.showMessageDialog(newFrame, "File write complete. " + (writeIndex/2) + " Lines written to new file.");
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(frame, "File write complete. " + (writeIndex/2) + " Lines written to new file.");
		totalLines = 0;
		writeIndex = 0;
		criteriaCount = 0;		
	}  // end finalProcessing

}

package lithium1978.m3uMasterCreator.gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import lithium1978.m3uMasterCreator.backendData.*;
import lithium1978.m3uMasterCreator.controller.*;


public class CheckBoxArray {
	static int testIndex = 0;
	static ArrayList<String> groupIDs = new ArrayList<>();
	static ArrayList<String> importedIDs = new ArrayList<>();

	public static List<String> getGroupIDs() {
		return groupIDs;
	}
		
//	static GroupTitleController controller = new GroupTitleController();
	
	
	private static void processLine(String line) {
		
		

		String match = "group-title=";
		String match2 = "\",";

		if (line.toLowerCase().indexOf(match.toLowerCase()) > 0) {
			int loc = line.indexOf(match);   //checks string from reader to find location of match val 
			int loc2 = line.indexOf(match2);  //checks string from reader to find location of match2 val
			loc = loc + 13;

			if(loc2 < loc) {
				FileLogger.logData(LocalDateTime.now() + " data match out of range " + loc +"     "+ loc2 + " String analyzed " + line + " " + testIndex + System.lineSeparator()) ;
				return;	
			}else if (loc-loc2==1){
				String str = "";
				addLineToArray(str);				
			}

			String str = line.substring(loc, loc2);  //pulls value of the group-title field and assigns to str
			addLineToArray(str);  
		}
		testIndex++;
	} // end processLine

	private static void addLineToArray(String value) {
		
		

		if(groupIDs.contains(value)) {
			return;
		}
		GroupTitle gt = new GroupTitle(value, null);
		GroupTitleController.addGroupTitle(gt);
//		System.out.println("Testing from within CheckBoxArray " + GroupTitleController.getGroupTitles());
		groupIDs.add(value);
	}  // end addLineToArray

	public static ArrayList<String> analyzeFile() 
	{
//		System.out.println("test from ArrayList " + GroupTitleController.getGroupTitles());
		UpdateGroupTitleArray update = new UpdateGroupTitleArray();
		importedIDs = update.updateArray();
		
		if (importedIDs.size() > 0) {
			groupIDs = importedIDs;
		}
		
		try (FileReader in = new FileReader(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Temp_FileReader.txt");
				BufferedReader reader = new BufferedReader(in)) 
		{
			String line;
			while ((line = reader.readLine()) != null) 
			{
				processLine(line);  //passes line to processLine for analysis
			}  //end while loop
		} catch (IOException e) {
			e.printStackTrace();
		}
		//sorts groupIDs arrayList in alpha order
		Collections.sort(groupIDs);
		return groupIDs;



	}// end analyzeFile

}




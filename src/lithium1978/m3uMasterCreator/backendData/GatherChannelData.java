package lithium1978.m3uMasterCreator.backendData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.lang3.StringUtils;

import lithium1978.m3uMasterCreator.controller.*;
import lithium1978.m3uMasterCreator.fileInputOutput.*;

public class GatherChannelData  {

	static ChannelController cd = new ChannelController();	

	String tvgID = new String();
	String tvgName = new String();
	String tvgCustomName= new String();
	String tvgLogo = new String();
	String groupTitle = new String();
	boolean isIncluded = true;
	String lineOne = new String();
	String lineTwo = new String();
	String sourceURL = new String();
	LocalDateTime dateAdded = java.time.LocalDateTime.now();
	int channelsAdded = 0;

	int linesNeeded = 0;

//	public static List<Channel> getChannels(){
//		return ChannelController.getChannels();
//	}

	public void startReader(){


		try (FileReader in = new FileReader (FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/Temp_FilteredFile.txt");
				BufferedReader reader = new BufferedReader(in))
		{
			String line;
			String line2 = null;
			while((line = reader.readLine()) != null) {
				if(linesNeeded == 0) {	
					getData(line, line2);
				} else {
					line2 = reader.readLine();
					getData(line, line2);
				}
				//				getData(line, line2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getData(String line, String line2) {
		String [] matchCriteria = {"tvg-id=\"", "tvg-name=\"", "tvg-logo=\"", "group-title=\"","\","};
		int matchLookup = 0;

		String str = null;

		if (line.toLowerCase().indexOf(matchCriteria[matchLookup]) > 0) {

			linesNeeded = 0;

			while(matchLookup <= 3) {
				int loc = StringUtils.indexOfIgnoreCase(line, matchCriteria[matchLookup]);
				int locLength = matchCriteria[matchLookup].length();
				matchLookup++;
				int loc2 = StringUtils.indexOfIgnoreCase(line, matchCriteria[matchLookup]);

				if(matchLookup == 4) {
				} else {
					loc2 = loc2 -1;
				}
				loc = loc + locLength;

				if(loc2 < loc) {
					FileLogger.logData(LocalDateTime.now() + " data match out of range " + loc +"     "+ loc2 + " String analyzed " + line + " " + System.lineSeparator()) ;	
				}else if (loc-loc2==1){
					str = "";
				}

				str = line.substring(loc, loc2);  //pulls value of the group-title field and assigns to str
				String cleaned = str.replaceAll("\"", "");

				switch (matchLookup){
				case 1: 	tvgID=cleaned;
				break;
				case 2: 	tvgName =cleaned;
				tvgCustomName=cleaned;
				break;
				case 3:  	tvgLogo=cleaned;
				break;
				case 4: 	groupTitle=cleaned; 
				boolean isIncluded = true;
				LocalDateTime dateAdded = java.time.LocalDateTime.now();
				String lineOne = line;
				String lineTwo = line2;
				String sourceURL = line2;
				Channel channel = new Channel(tvgID, tvgName, tvgCustomName, tvgLogo, groupTitle, isIncluded, dateAdded, lineOne, lineTwo, sourceURL);
				cd.addChannel(channel);
				channelsAdded++;
				break;	
				}
			}	
		}

		linesNeeded = 1;

	} // end getData
}


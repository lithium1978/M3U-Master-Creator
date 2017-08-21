package lithium1978.m3uMasterCreator.backendData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lithium1978.m3uMasterCreator.controller.*;
import lithium1978.m3uMasterCreator.fileInputOutput.*;
import lithium1978.m3uMasterCreator.model.*;


public class SelectedChannels {

	private List<Channel> cd;
	private List <GroupTitle> gt;
	
//	private checkBoxArray cba;
	ArrayList <String> selectedChannels = new ArrayList<>();
//	ArrayList <String> updatedTvgIDs = new ArrayList<>();
//	ArrayList <String> updatedGroupTitles = new ArrayList<>();
	ArrayList <String> updatedWriteLines = new ArrayList<>();
	
	public void getData() {
		cd = ChannelController.getChannels();	
		gt = GroupTitleController.getGroupTitles();
		
		GroupTitleTableModel gtm = new GroupTitleTableModel();
		gtm.setData(gt);
		Map<String, String> groupTitleVals = new HashMap<>();
		String replacementVal = new String();
		String replacementVal2= new String();
		for(int z = 0; z < gt.size(); z++) {
			replacementVal = (String) gtm.getValueAt(z, 0);
			replacementVal2 = (String) gtm.getValueAt(z, 1);
			
			groupTitleVals.put(replacementVal, replacementVal2);
		}
		
		System.out.println(" map print out " + groupTitleVals.size());
		System.out.println("map print out " + groupTitleVals);
		
//		List<String> existingGroupList = new ArrayList<String>();
//		existingGroupList = checkBoxArray.getGroupIDs();
////		
////		System.out.println(existingGroupList.size());
////		System.out.println(existingGroupList);

		for(int i = 0; i< cd.size();i++) {
			Channel channel = cd.get(i);
			if(channel.getIsIncluded()== true) {
				String tvgID = (String) channel.getTvgID();
				String tvgCustomName = (String) channel.getTvgCustomName();
				String tvgLogo = (String) channel.getTvgLogo();
				String groupTitle = (String) channel.getGroupTitle();
				
				if(groupTitleVals.containsKey(groupTitle)) {
					String replacementTitle = groupTitleVals.get(groupTitle);
						if(replacementTitle != null) {
						groupTitle = replacementTitle;
						}	
				}	
				
				selectedChannels.add(tvgCustomName);
//				updatedTvgIDs.add(str2);
//				updatedGroupTitles.add(str3);
				//			System.out.println(selectedChannels);
				String writeUpdatedLine = "#EXTINF:-1 tvg-id=\"" +tvgID+ "\" tvg-name=\""+ tvgCustomName + "\" tvg-logo=\"" + tvgLogo +"\" group-title=\"" + groupTitle + "\"," + tvgCustomName;
				updatedWriteLines.add(writeUpdatedLine);
			}else {
				if(channel.getIsIncluded()== false) {
					selectedChannels.add("skip channel");
					updatedWriteLines.add("Skip Channel");
				}
			}

		}

//		WriteTempFile tempFile = new WriteTempFile();
		
		WriteFile write = new WriteFile();
		
		write.saveFile(selectedChannels, updatedWriteLines);

//		tempFile.saveFile(selectedChannels, updatedTvgIDs, updatedGroupTitles);
//
//		System.out.println(cd.size());


	}
}












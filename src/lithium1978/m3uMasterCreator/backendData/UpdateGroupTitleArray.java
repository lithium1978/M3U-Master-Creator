package lithium1978.m3uMasterCreator.backendData;

import java.util.ArrayList;
import java.util.List;

import lithium1978.m3uMasterCreator.controller.*;
import lithium1978.m3uMasterCreator.model.*;

public class UpdateGroupTitleArray {

	private GroupTitleTableModel groupModel;
	private ArrayList<String> groupTitlesImported = new ArrayList<>();

	public ArrayList<String> updateArray() {

		groupModel = new GroupTitleTableModel();
		
		List<GroupTitle> values = new ArrayList<>();
		values = GroupTitleController.getGroupTitles();
		groupModel.setData(values);
		
		int rowCount = GroupTitleController.getGroupTitles().size();
		System.out.println("from within update GroupTitle array" + rowCount);
		System.out.println("From within update Array " + GroupTitleController.getGroupTitles());
		
		for(int i = 0; i< rowCount; i++) {
			String value = (String) groupModel.getValueAt(i, 0);
			
			groupTitlesImported.add(value);
		}
		return groupTitlesImported;
		
	}
}

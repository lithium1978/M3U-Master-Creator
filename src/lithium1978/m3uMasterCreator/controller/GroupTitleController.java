package lithium1978.m3uMasterCreator.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lithium1978.m3uMasterCreator.backendData.*;

public class GroupTitleController {
	
	private static ArrayList<GroupTitle> groupTitles;

	public GroupTitleController() {
		groupTitles = new ArrayList<GroupTitle>();
	}

	public static void addGroupTitle(GroupTitle groupTitle) {
		groupTitles.add(groupTitle);
	}

	public static List<GroupTitle> getGroupTitles() {
		return groupTitles;
	}
	
	public static void clearGroupTitles() {
		groupTitles.clear();
	}

	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		GroupTitle[] groupList = groupTitles.toArray(new GroupTitle[groupTitles.size()]);

		oos.writeObject(groupList);

		oos.close();
	}

	public void loadFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);

		try {
			GroupTitle[] groupList = (GroupTitle[])ois.readObject();

			groupTitles.clear();

			groupTitles.addAll(Arrays.asList(groupList));
			System.out.println("test from controller load file " + getGroupTitles());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ois.close();
	}
}


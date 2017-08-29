package lithium1978.m3uMasterCreator.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lithium1978.m3uMasterCreator.backendData.*;
import lithium1978.m3uMasterCreator.database.Database;

public class GroupTitleController {
	
	private static List<GroupTitle> groupTitles;

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

	public void loadToDb(){
		Database db = new Database();
		db.insertGroups(GroupTitleController.getGroupTitles());
	}
	
	public void updateDB() {
		Database db = new Database();
		db.updateGroups(GroupTitleController.getGroupTitles());
	}
	
	public static List<GroupTitle> pullFromDB() {
		Database db = new Database();
		groupTitles = db.loadGroups();
		Collections.sort(groupTitles);
		return groupTitles;
	}
	
	public List<GroupTitle> getGroupData(){
		Database db = new Database();
		return db.loadGroups();
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


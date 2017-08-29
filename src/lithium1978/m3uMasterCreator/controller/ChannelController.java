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
import lithium1978.m3uMasterCreator.fileInputOutput.WriteTempFile;

public class ChannelController {

	private static List<Channel> channels;

	public ChannelController() {
		channels = new ArrayList<Channel>();
	}

	public static void addChannel(Channel channel) {
		channels.add(channel);
	}

	public static List<Channel> getChannels() {
		return channels;
	}
	
	public static void clearChannels() {
		channels.clear();
	}
	
	public static void loadToDb(){
		Database db = new Database();
		try {
			db.insertChannels(ChannelController.getChannels());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateDB() {
		Database db = new Database();
		db.updateChannels(ChannelController.getChannels());
	}
	
	public static List<Channel> pullFromDB() {
		Database db = new Database();
		channels = db.loadChannels(WriteTempFile.getSelectedCriteria(), ChannelController.getChannels());
		return channels;
	}

	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		Channel[] chanList = channels.toArray(new Channel[channels.size()]);

		oos.writeObject(chanList);

		oos.close();
	}

	public void loadFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);

		try {
			Channel[] chanList = (Channel[])ois.readObject();

			channels.clear();

			channels.addAll(Arrays.asList(chanList));

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ois.close();
	}
}




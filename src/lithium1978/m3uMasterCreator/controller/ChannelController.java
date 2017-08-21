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

public class ChannelController {

	private static ArrayList<Channel> channels;

	public ChannelController() {
		channels = new ArrayList<Channel>();
	}

	public void addChannel(Channel channel) {
		channels.add(channel);
	}

	public static List<Channel> getChannels() {
		return channels;
	}
	
	public static void clearChannels() {
		channels.clear();
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




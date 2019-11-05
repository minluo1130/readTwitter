package com.home.readTwitter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToTopicFile {
	
	private File destFile;
	private String topic;
	private FileWriter fileWriter;
	
	
	public WriteToTopicFile(String topic,String filePath) {
		this.destFile =new File(filePath + File.separator+ topic+".txt");
		this.topic =topic;
		try {
			this.fileWriter = new FileWriter(destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File getDestFile() {
		return destFile;
	}
	
	public void writeToFile(String content) {
		if(isBelongToTopic(content)) {
			try {
				fileWriter.write(content);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
	}

	public boolean isBelongToTopic(String content) {
		if(content.contains(topic)) {
			return true;
		}
		
		return false;	
	}
	
	public void terminate() {
		try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

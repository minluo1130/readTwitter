package com.home.readTwitter;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class ReadFromTwitter {

	static String[] topicList = null;
	static final int MAX_THREAD_NUMBER = 5;
	static ArrayList<ReadFromTwitterTopic> readFromTwitterTopicList = new ArrayList<>();
	private static Scanner scan;

	private static boolean validateUserEnter(String topics) {
		topicList = topics.split(",");
		if (topicList.length > 5) {
			return false;
		}

		for (String topic : topicList) {
			if (StringUtils.isBlank(topic)) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws Exception {

		System.out.println(
				"Please enter up to 5 topics tweets you like to read, use \",\" to seperate them. enter quit to exit program");

		scan = new Scanner(System.in);

		String userEnter = scan.next();

		while (!validateUserEnter(userEnter)) {
			System.out.println("Either enter more than five topic or one of topci is empty");
			userEnter = scan.next();
		}

		TwitterConfiguration.loadProperties();

		Path currentRelativePath = Paths.get("");
		String relativePath = TwitterConfiguration.getProperties().getProperty("FILE.PATH");
		String filePath = currentRelativePath.toAbsolutePath().toString() + File.separator + relativePath;

		File directory = new File(filePath);
		if (!directory.exists()) {
			directory.mkdir();
		}

		ReadFromTwitterTopic readFromTwitterTopic = new ReadFromTwitterTopic(topicList, filePath);

		Thread threadTopic = new Thread(readFromTwitterTopic);
 
		threadTopic.start();

		System.out.println("If at anytime you want to leave, type \"quit\"");

		String userCommond = scan.next();

		if (userCommond.equals("quit")) {
			System.out.println("quit reading from twitter");

			readFromTwitterTopic.terminate();

		}

	}

}
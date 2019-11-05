package com.home.readTwitter;

import java.util.ArrayList;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;

public class ReadFromTwitterTopic implements Runnable {

	TwitterStream twitterStream;
	private String[] topicList;
	private volatile boolean running = true;
	private String filePath;
	private ArrayList<WriteToTopicFile> writeToTopicFileList;

	public ReadFromTwitterTopic() {
		twitterStream = TwitterBuilder.getTwitterStreamInstance();
	}

	public ReadFromTwitterTopic(String[] topicList, String filePath) {
		this();
		this.filePath = filePath;
		this.topicList = topicList;
		this.writeToTopicFileList = new ArrayList<WriteToTopicFile>();
	}

	private StatusListener createStatusListener() {
		StatusListener listener = new StatusListener() {

			@Override
			public void onStatus(Status status) {
				// System.out.println("@" + status.getUser().getScreenName() + " - " +
				// status.getText());
				if (running) {
					writeToFile("@" + status.getUser().getScreenName() + " - " + status.getText());
				}
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				// System.out.println("Got a status deletion notice id:" +
				// statusDeletionNotice.getStatusId());
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				// System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			}

			public void onScrubGeo(long userId, long upToStatusId) {
				// System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:"
				// + upToStatusId);
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}

			@Override
			public void onStallWarning(StallWarning warning) {
				// TODO Auto-generated method stub

			}
		};

		return listener;
	}

	public void terminate() {
		running = false;
	}

	private void buildFiles() {
		for (String topic : topicList) {
			System.out.println("Start to write "+ topic + ".txt file");
			WriteToTopicFile writeToTopicFile = new WriteToTopicFile(topic, filePath);
			writeToTopicFileList.add(writeToTopicFile);
		}
	}

	private void writeToFile(String content) {
		for (WriteToTopicFile writeToTopicFile : writeToTopicFileList) {
			writeToTopicFile.writeToFile(content);
		}
	}
	
	private void terminateProcess() {
		System.out.println("shutdown twitter strem");
		twitterStream.cleanUp();
		twitterStream.shutdown();
		for (WriteToTopicFile writeToTopicFile : writeToTopicFileList) {
			System.out.println("Close file " + writeToTopicFile.getDestFile().getName());
			writeToTopicFile.terminate();
		}
	}

	@Override
	public void run() {
		StatusListener listener = createStatusListener();
		buildFiles();
		
		twitterStream.addListener(listener);
		// twitterStream.sample();

		FilterQuery filter = new FilterQuery();
		filter.track(topicList);
		filter.language(new String[] { "en" });

		System.out.println("------Start process-------");

		twitterStream.filter(filter);

		while (true) {
			if (!running) {
				terminateProcess();
				break;
			}
			try {
				Thread.sleep(5000);
				System.out.println("Writting file....");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		System.out.println("User terminate the process,waitting....");

	}
	
	

}

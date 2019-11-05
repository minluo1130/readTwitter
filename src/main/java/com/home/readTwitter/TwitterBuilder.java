package com.home.readTwitter;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterBuilder {

	private static Configuration config;
	private static TwitterStream twitterStream;
	
	public static Configuration getConfiguration() {
		if(config==null) {
			ConfigurationBuilder configBuilder = new ConfigurationBuilder();
		    configBuilder.setDebugEnabled(true);
		    configBuilder.setOAuthConsumerKey(TwitterConfiguration.getProperties().getProperty("TWITTER.CONSUMER_KEY"));
		    configBuilder.setOAuthConsumerSecret(TwitterConfiguration.getProperties().getProperty("TWITTER.CONSUMER_KEY_SECRET"));
		    configBuilder.setOAuthAccessToken(TwitterConfiguration.getProperties().getProperty("TWITTER.ACCESS_TOKEN"));
		    configBuilder.setOAuthAccessTokenSecret(TwitterConfiguration.getProperties().getProperty("TWITTER.ACCESS_TOKEN_SECRET"));
		    
		    config = configBuilder.build();
		}
		
		return config;	
	}
	
	public static TwitterStream getTwitterStreamInstance() {
		
		if(config==null) {
			config =getConfiguration();
		}
		
		if(twitterStream ==null) {
			twitterStream =new TwitterStreamFactory(config).getInstance();
		}
		
		return twitterStream;
		
	}

}

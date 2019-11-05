package com.home.readTwitter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TwitterConfiguration {

	private static Properties properties;

	public static void loadProperties() {
		if (properties == null) {
			InputStream input = TwitterConfiguration.class.getClassLoader()
					.getResourceAsStream("twitterConfig.properties");

			properties = new Properties();

			if (input == null) {
				System.out.println("Sorry, unable to find config.properties");
				return;
			}

			try {
				properties.load(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Properties getProperties() {
		return properties;
	}

}

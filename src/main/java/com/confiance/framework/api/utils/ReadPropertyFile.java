package com.confiance.framework.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.testng.annotations.Test;

public class ReadPropertyFile {

	public static String getValueFromPropertyFile(String strFilePath, String strKey) throws IOException {
		InputStream input = ReadPropertyFile.class.getClassLoader().getResourceAsStream(strFilePath);
		Properties prop = new Properties();
		prop.load(input);
		return prop.getProperty(strKey);
	}
}

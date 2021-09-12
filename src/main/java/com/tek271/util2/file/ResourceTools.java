package com.tek271.util2.file;

import com.google.common.io.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

public class ResourceTools {
	public InputStream readAsInputStream(String resourceName) {
		URL url = Resources.getResource(resourceName);
		try {
			return Resources.asByteSource(url).openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String readAsString(String resourceName) {
		URL url = Resources.getResource(resourceName);
		try {
			return Resources.toString(url, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Properties readAsProperties(String resourceName) {
		Properties properties = new Properties();
		try (InputStream is = readAsInputStream(resourceName)) {
			properties.load(is);
		} catch (IOException e) {
			throw new RuntimeException("Could not load resource " + resourceName + " contents into properties.", e);
		}
		return properties;
	}

	public List<String> readLines(String resourceName) {
		URL url = Resources.getResource(resourceName);
		try {
			return Resources.readLines(url, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}

package com.tek271.util2.files;

import com.google.common.io.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ResourceTools {
	public InputStream readAsInputStream(String fileName) {
		URL url = Resources.getResource(fileName);
		try {
			return Resources.asByteSource(url).openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String readAsString(String fileName) {
		URL url = Resources.getResource(fileName);
		try {
			return Resources.toString(url, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}

package com.tek271.util2.file;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileTools {

	public void remove(String filePath) {
		File file = new File(resolveTildeAtStart(filePath));
		FileUtils.deleteQuietly(file);
	}

	public void write(String filePath, String text) {
		File file = new File(resolveTildeAtStart(filePath));
		try {
			FileUtils.write(file, text, UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String resolveTildeAtStart(String path) {
		if (path.startsWith("~/") || path.startsWith("~\\")) {
			return System.getProperty("user.home") + File.separator + path.substring(2);
		}
		return path;
	}

	public void append(String filePath, String text) {
		File file = new File(resolveTildeAtStart(filePath));
		try {
			FileUtils.writeStringToFile(file, text, UTF_8, true);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String readAsString(String filePath) {
		File file = new File(resolveTildeAtStart(filePath));
		try {
			return FileUtils.readFileToString(file, UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> readLines(String filePath) {
		File file = new File(resolveTildeAtStart(filePath));
		try {
			return FileUtils.readLines(file, UTF_8);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}

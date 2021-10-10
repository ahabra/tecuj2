package com.tek271.util2.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileToolsTest {
	private static final String WORK_DIR = System.getProperty("user.dir");
	private static final String TEST_FILE = "/src/test/resources/FileToolsTest.txt";
	private FileTools sut;

	@BeforeEach
	public void setUp() {
		sut = new FileTools();
	}

	@Test
	public void testWrite() throws IOException {
		String path = tempPath();
		String text = randomText();
		sut.write(path, text);
		assertEquals(text, sut.readAsString(path));
		sut.remove(path);
	}

	@Test
	public void testAppend() throws IOException {
		String path = tempPath();
		String text = randomText();
		sut.write(path, text);
		sut.append(path, "-added");

		assertEquals(text+"-added", sut.readAsString(path));
		sut.remove(path);
	}

	private static String randomText() {
		return "tecuj2-FileToolsTest-" + (int) (Math.random() * 1000);
	}

	private String tempPath() throws IOException {
		File file = File.createTempFile("tecuj2-", ".txt");
		String path = file.getAbsolutePath();
		sut.remove(path);
		return path;
	}

	@Test
	public void testReadAsString() {
		String text = sut.readAsString(WORK_DIR + TEST_FILE);
		assertEquals("a\nb\nc", text);
	}

	@Test @Disabled
	public void readAsStringHandlesPathWithTilde() {
		String path = "~/_src/java/tecuj2/tecuj2";
		String text = sut.readAsString(path + TEST_FILE);
		assertEquals("a\nb\nc", text);
	}

	@Test
	public void testReadLines() {
		List<String> lines = sut.readLines(WORK_DIR + TEST_FILE);
		assertEquals(newArrayList("a", "b", "c"), lines);
	}

}
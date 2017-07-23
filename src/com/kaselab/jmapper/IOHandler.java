package com.kaselab.jmapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rick on 7/21/2017.
 */
public class IOHandler {

	public static List<Character> readFileCharList(File file) {
		List<Character> charList = new ArrayList<>();
		BufferedReader reader;
		int curChar;
		try {
			reader = new BufferedReader(new FileReader(file));
			while ((curChar = reader.read()) != -1)
				charList.add((char) curChar);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return charList;
	}

	public static Iterator<Character> readFileCharIterator(File file) {
		return readFileCharList(file).iterator();
	}

	public static List<String> readFileLineList(File file) {
		BufferedReader reader;
		List<String> lineList = new ArrayList<>();
		String curLine;
		try {
			reader = new BufferedReader(new FileReader(file));
			while ((curLine = reader.readLine()) != null)
				lineList.add(curLine);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lineList;
	}

	public static Iterator<String> readFileLineIterator(File file) {
		return readFileLineList(file).iterator();
	}
}
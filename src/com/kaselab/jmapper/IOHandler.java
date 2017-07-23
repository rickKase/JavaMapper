package com.kaselab.jmapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rick on 7/21/2017.
 */
public class IOHandler {

	public static Iterator<Character> readFile(File file) {
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
		return charList.iterator();
	}
}
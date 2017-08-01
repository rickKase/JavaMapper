package com.kaselabs.jmapper;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rick on 7/21/2017.
 */
public class Dao {

	private DocumentBuilderFactory dFactory;
	private DocumentBuilder builder;

	private RecognizerParser recognizerParser;

	public Dao() {
		dFactory = DocumentBuilderFactory.newInstance();
		initializeConfiguration();

		recognizerParser = new RecognizerParser();
	}

	/**
	 * Initializes the many configurations required for the
	 * factory objects.
	 */
	private void initializeConfiguration() {
		dFactory.setIgnoringComments(true);
		dFactory.setIgnoringElementContentWhitespace(true);
		dFactory.setValidating(true);
		dFactory.setNamespaceAware(true);
		try {
			builder = dFactory.newDocumentBuilder();
			builder.setErrorHandler(new Dao.ParserErrorHandler());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public List<Character> readFileCharList(File file) {
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

	public Iterator<Character> readFileCharIterator(File file) {
		return readFileCharList(file).iterator();
	}

	public List<String> readFileLineList(File file) {
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

	public Iterator<String> readFileLineIterator(File file) {
		return readFileLineList(file).iterator();
	}

	public Recognizer readRecognizer(File file) {
		return recognizerParser.createRecognizer(readDocument(file));
	}



	/*
	* Internal methods for performing basic, nonspecific versions
	* of the public methods
	*/
	/**
	 * Provides the functionality for reading any xml file into
	 * the format of a DOM object regardless of what that xml file
	 * represents.
	 * @param file
	 * @return
	 */
	private Document readDocument(File file) {
		try {
			if (!file.exists())
				throw new FileNotFoundException("File does not exist");
			return builder.parse(file);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Separate inner class used to handle all the parser exceptions that
	 * could be thrown.
	 * TODO handle exceptions likely to be encountered in these methods
	 * TODO add functionality to handle transformer exceptions as well.
	 */
	private class ParserErrorHandler implements ErrorHandler {

		@Override
		public void warning(SAXParseException exception) throws SAXException {
			exception.printStackTrace();
		}

		@Override
		public void error(SAXParseException exception) throws SAXException {
			exception.printStackTrace();
		}

		@Override
		public void fatalError(SAXParseException exception) throws SAXException {
			exception.printStackTrace();
		}
	}

}
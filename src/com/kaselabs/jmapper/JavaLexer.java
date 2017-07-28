package com.kaselabs.jmapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Should create a list of all the DFAs
 * Should use them to take raw text data and turn it into a
 * format that is more easily parsable.
 */
public class JavaLexer {

	private List<Recognizer> recognizers;
	private List<Character> input;

	public JavaLexer(File file) {
		recognizers = new ArrayList<>();
		input = IOHandler.readFileCharList(file);
	}

	public Token evaluate() {
		for (int i = 0; i < input.size(); i++) {
			for (Recognizer recognizer : recognizers) {
				if (recognizer.isChecking())
					recognizer.checkNext(input.get(i));

			}
		}

		return null;
	}

}

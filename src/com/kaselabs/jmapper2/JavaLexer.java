package com.kaselabs.jmapper2;

import java.util.List;

/**
 * Created by Rick on 7/28/2017.
 *
 *
 */
public class JavaLexer {

	/* will be a "file" recognizer and recognize the entire input */
	private Recognizer recognizer;
	private List<Character> input;

	public JavaLexer() {
		loadRecognizer();
	}

	public void loadRecognizer() {

	}

	public Token interpretInput(){
		Token token;
		for (int i = 0; i < input.size(); i++) {
			recognizer.checkNext(input.get(i));
			if (!recognizer.isChecking())
				if (recognizer.isSucceeded() && i == input.size() - 1)
					return recognizer.getOutput();
				else
					break;
		}
		throw new IllegalArgumentException();
	}
}

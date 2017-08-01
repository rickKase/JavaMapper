package com.kaselabs.jmapper;

import java.util.List;

/**
 * Created by Rick on 7/28/2017.
 *
 *
 */
public class JavaLexer {

	/* will be a "file" tokenizer and recognize the entire input */
	private Tokenizer tokenizer;
	private List<Character> input;

	public JavaLexer() {
		loadRecognizer();
	}

	public void loadRecognizer() {

	}

	public Token interpretInput(){
		Token token;
		for (int i = 0; i < input.size(); i++) {
			tokenizer.checkNext(input.get(i));
			if (!tokenizer.isChecking())
				if (tokenizer.isSucceeded() && i == input.size() - 1)
					return tokenizer.getOutput();
				else
					break;
		}
		throw new IllegalArgumentException();
	}
}

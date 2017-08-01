package com.kaselabs.jmapper;

/**
 * Created by Rick on 8/1/2017.
 */
public class TextToken extends Token {

	private String text;

	public TextToken(TokenType tokenType, String text) {
		super(tokenType);
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}
}

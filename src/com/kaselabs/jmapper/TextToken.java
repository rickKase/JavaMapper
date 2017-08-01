package com.kaselabs.jmapper;

/**
 * Created by Rick on 8/1/2017.
 */
public class TextToken extends Token {

	private String text;

	public TextToken( String text) {
		super(TokenType.TEXT);
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}
}

package com.kaselabs.jmapper.token;

/**
 * Created by Rick on 8/1/2017.
 *
 * A token that represents a String of Text. All Tokens
 * are at root comprised of Text Tokens.
 */
public class TextToken extends Token {

	private String text;

	/**
	 * Creates a Text token that represents the text passed
	 * as input.
	 * @param text to be repesented.
	 */
	public TextToken( String text) {
		super(TokenType.TEXT);
		this.text = text;
	}

	/**
	 * returns the text represented by this token.
	 * @return the text represented by this token.
	 */
	@Override
	public String getText() {
		return text;
	}
}

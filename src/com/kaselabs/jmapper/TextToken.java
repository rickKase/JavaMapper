package com.kaselabs.jmapper;

/**
 * Created by Rick on 8/1/2017.
 *
 * A Token that represents a String of Text. All Tokens
 * are at root comprised of Text Tokens.
 */
public class TextToken extends Token {

	private String text;

	/**
	 * Creates a Text Token that represents the text passed
	 * as input.
	 * @param text to be repesented.
	 */
	public TextToken( String text) {
		super(TokenType.TEXT);
		this.text = text;
	}

	/**
	 * returns the text represented by this Token.
	 * @return the text represented by this Token.
	 */
	@Override
	public String getText() {
		return text;
	}
}

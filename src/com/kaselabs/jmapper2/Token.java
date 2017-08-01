package com.kaselabs.jmapper2;

/**
 * Created by Rick on 7/28/2017.
 *
 * Tokens are bits of syntactic information that are more meaningful
 * representations of the data they represent. They are created
 * through a Recognizer that is designed to find that particular
 * Token in text input and outputted as the result of the Recognizer.
 *
 * TODO Create an enforcement mechanism for incorrect Tokens
 * TODO Create factory class for creating Tokens of a certain type.
 */
public abstract class Token {

	protected TokenType tokenType;
	protected String content;

	/**
	 * Tokens must be created with their tokenType and value
	 * on Construction.
	 * @param tokenType type of Token
	 * @param content value of Token
	 */
	public Token(TokenType tokenType, String content) {
		this.tokenType = tokenType;
		this.content = content;
	}

	/**
	 * Returns the type of value that this Token represents.
	 * @return the TokenType of this token
	 */
	public TokenType getTokenType() {
		return tokenType;
	}

	/**
	 * Returns the string content of this Token.
	 * @return the string content of this token
	 */
	public String getContent() {
		return content;
	}
}
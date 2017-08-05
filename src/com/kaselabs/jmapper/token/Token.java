package com.kaselabs.jmapper.token;

/**
 * Created by Rick on 7/28/2017.
 *
 * Tokens are bits of syntactic information that are more meaningful
 * representations of the data they represent. They are created
 * through a Tokenizer that is designed to find that particular
 * token in text input and outputted as the result of the Tokenizer.
 *
 * TODO Create an enforcement mechanism for incorrect Tokens
 * TODO Create factory class for creating Tokens of a certain type.
 */
public abstract class Token {

	protected TokenType tokenType;


	public Token(TokenType tokenType) {
		this.tokenType = tokenType;
	}

	/**
	 * Returns the type of value that this token represents.
	 * @return the TokenType of this token
	 */
	public TokenType getTokenType() {
		return tokenType;
	}

	/**
	 * Returns the string content of this token.
	 * @return the string content of this token
	 */
	public abstract String getText();
}
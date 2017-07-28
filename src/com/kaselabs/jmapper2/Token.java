package com.kaselabs.jmapper2;

/**
 * Created by Rick on 7/28/2017.
 *
 * For now, there will be no enforcement mechanism for ensuring
 * that Token Types are created as the Proper type, either
 * terminal or nonterminal, or ensure that nonterminal tokens
 * have a valid amount of children.
 *
 * This could be done in the future by creating a factory class
 * for "getting" tokens of a particular type.
 */
public abstract class Token {

	protected TokenType tokenType;

	public Token(TokenType tokenType) {
		this.tokenType = tokenType;
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public abstract String getContent();

}

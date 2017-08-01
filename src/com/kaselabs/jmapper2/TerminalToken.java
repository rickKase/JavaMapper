package com.kaselabs.jmapper2;

/**
 * Created by Rick on 7/28/2017.
 *
 * A Terminal token is a token that does not have any constituent
 * Token parts. It has a type value for the Token and String value
 * for the content of this Token.
 */
public class TerminalToken extends Token {

	/**
	 * Terminal Tokens need to be initialized with all the data
	 * upon construction.
	 * @param tokenType the type of token
	 * @param value the value of this token
	 */
	public TerminalToken(TokenType tokenType, String value) {
		super(tokenType, value);
	}
}
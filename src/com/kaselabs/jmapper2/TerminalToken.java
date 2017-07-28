package com.kaselabs.jmapper2;

/**
 * Created by Rick on 7/28/2017.
 */
public class TerminalToken extends Token {

	private String value;

	public TerminalToken(TokenType tokenType, String value) {
		super(tokenType);
		this.value = value;
	}

	@Override
	public String getContent() {
		return value;
	}
}

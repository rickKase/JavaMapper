package com.kaselabs.jmapper;

/**
 * Created by Rick on 7/28/2017.
 */
public class Token {

	private String tokenType;
	private String value;

	public Token(String tokenType, String value) {
		this.tokenType = tokenType;
		this.value = value;
	}

	public String getTokenType() {
		return tokenType;
	}

	public String getValue() {
		return value;
	}
}

package com.kaselabs.jmapper2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rick on 7/28/2017.
 */
public class NonterminalToken extends Token {

	private List<Token> subTokens;

	public NonterminalToken(TokenType tokenType) {
		super(tokenType);
		subTokens = new ArrayList<>();
	}

	public void addSubToken(Token token) {
		subTokens.add(token);
	}

	public Token[] getSubTokens() {
		return subTokens.toArray(new Token[0]);
	}

	@Override
	public String getContent() {
		StringBuilder build = new StringBuilder();
		for (Token token : subTokens)
			build.append(token.getContent());
		return build.toString();
	}
}

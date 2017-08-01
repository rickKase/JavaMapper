package com.kaselabs.jmapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rick on 8/1/2017.
 *
 */
public class CompoundToken extends Token {

	private List<Token> tokens;

	public CompoundToken(TokenType tokenType) {
		super(tokenType);
		tokens = new ArrayList<>();
	}

	public void addToken(Token token) {
		tokens.add(token);
	}

	public Token[] getTokens() {
		return tokens.toArray(new Token[0]);
	}

	@Override
	public String getText() {
		StringBuilder build = new StringBuilder();
		for (int i = 0; i < tokens.size(); i++) {
			build.append(tokens.get(i).getText());
			if (i < tokens.size() - 1)
				build.append(" ");
		}
		return build.toString();
	}
}
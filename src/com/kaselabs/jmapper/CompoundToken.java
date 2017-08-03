package com.kaselabs.jmapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rick on 8/1/2017.
 *
 * A Token that is made up of smaller composite Tokens.
 */
public class CompoundToken extends Token {

	private List<Token> tokens;

	/**
	 * Creates a Token that is comprised of other smaller
	 * tokens.
	 * @param tokenType Type of token
	 */
	public CompoundToken(TokenType tokenType) {
		super(tokenType);
		tokens = new ArrayList<>();
	}

	/**
	 * adds a sub token to this token.
	 * @param token
	 */
	public void addToken(Token token) {
		tokens.add(token);
	}

	/**
	 * Returns an array of the subTokens
	 * @return an array of the subTokens
	 */
	public Token[] getTokens() {
		return tokens.toArray(new Token[0]);
	}

	/**
	 * Returns a String representation of this token.
	 * @return a String representation of this token
	 */
	@Override
	public String getText() {
		StringBuilder build = new StringBuilder();
		for (int i = 0; i < tokens.size(); i++) {
			build.append(tokens.get(i).getText());
		}
		return build.toString();
	}
}
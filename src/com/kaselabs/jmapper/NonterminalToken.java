package com.kaselabs.jmapper;

import java.util.List;

/**
 * Created by Rick on 7/28/2017.
 *
 * A Token that is made up of constituent Token parts. It has
 * a Token type, raw content that includes remnants of formatting,
 * and Token values that make up this current Token.
 */
public class NonterminalToken extends Token {

	private List<Token> subTokens;

	/**
	 * Nonterminal Tokens need to be initialized with all the data
	 * upon construction.
	 * @param tokenType the type of token
	 * @param value the value of this token
	 * @param subTokens the list of subTokens
	 */
	public NonterminalToken(TokenType tokenType, String value,
							List<Token> subTokens) {
		super(tokenType, value);
		this.subTokens = subTokens;
	}

	/**
	 * Returns an array of the sub Tokens that compose this Token.
	 * @return an array of the sub Tokens that compose this Token
	 */
	public Token[] getSubTokens() {
		return subTokens.toArray(new Token[0]);
	}

	/**
	 * Gets the string value of this Token by building it from all
	 * of its children down the tree structure. This should have the
	 * effect of removing whitespace and formatting.
	 * @return string representation of the Token without formatting
	 */
	@Override
	public String getContent() {
		StringBuilder build = new StringBuilder();
		for (Token token : subTokens)
			build.append(token.getContent());
		return build.toString();
	}

	/**
	 * Gets the string value of this Token by returning the string
	 * value that was used to create it. This string value will
	 * contain all the formatting that was present in the original
	 * string.
	 * @return string representation of the Token with formatting
	 */
	public String getFormattedContent() {
		return content;
	}
}

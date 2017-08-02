package com.kaselabs.jmapper;

/**
 * Created by Rick on 7/28/2017.
 *
 * List of Types that a token can be of. Tokens are also
 * associated with a particular recognizer because a
 * recognizer will only produce a single TokenType as
 * output.
 */
public enum TokenType {

	TEXT,
	SINGLELINE_COMMENT,
	MULTILINE_COMMENT,
	JAVADOC_COMMENT,
	STRING_LITERAL,
	CHARACTER_LITERAL,
	WORD,
	SYMBOL,
	UNICODE_CHARACTER,
	JAVA_SPECIAL_CHARACTER

}

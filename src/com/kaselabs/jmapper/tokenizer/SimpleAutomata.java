package com.kaselabs.jmapper.tokenizer;

import com.kaselabs.jmapper.token.TokenType;

/**
 * Created by Rick on 8/4/2017.
 */
public class SimpleAutomata extends Automata {

	/**
	 * Constructs a Finite Automata that can be used to identify
	 * regular expressions in a string.
	 *
	 * @param tokenType
	 * @param numOfStates number of states used in this Automata
	 */
	public SimpleAutomata(TokenType tokenType, int numOfStates) {
		super(tokenType, numOfStates);
	}
}

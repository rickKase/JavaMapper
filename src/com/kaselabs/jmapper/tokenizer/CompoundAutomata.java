package com.kaselabs.jmapper.tokenizer;

import com.kaselabs.jmapper.token.TextToken;
import com.kaselabs.jmapper.token.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rick on 8/1/2017.
 *
 * An Automata that can branch downwards into other Automata.
 * Acts much like an ordinary automata except that each state
 * can lead to other automata capable of recognizing other
 * inputs.
 *
 * TODO add ability to leave a subAutomata at a different state then started.
 */
public class CompoundAutomata extends SimpleAutomata {

	public List<List<Tokenizer>> subAutomata;

	public List<Tokenizer> activeAutomata;

	/**
	 * Constructs a Compound Automata with a set number of states
	 * that is designed to identify and Tokenize the particular
	 * token Type.
	 * @param tokenType to be identified
	 * @param numOfStates in the Automata
	 */
	public CompoundAutomata(TokenType tokenType, int numOfStates) {
		super(tokenType, numOfStates);
		subAutomata = new ArrayList<>();
		for (int i = 0; i < numOfStates; i++)
			subAutomata.add(new ArrayList<>());
		activeAutomata = new ArrayList<>();
	}

	/**
	 * Adds a sub automata that is meant to identify tokens that exist
	 * within the context of this Compound Automata.
	 * @param state the state at which this Automata can be activated
	 * @param automata the automata to be checked for
	 */
	public void addAutomata(int state, Automata automata) {
			subAutomata.get(state).add(automata);
	}

	/**
	 * TODO could remove automata from active list when isChecking = false
	 *
	 * Checks the current input character against the list of active
	 * automata. If one succeeds then it adds a token representing it
	 * to this Compound Automata's token. If they all fail then it
	 * terminates the Compound Automata's search by setting isChecking
	 * to false.
	 * @param c character to be checked
	 */
	private void checkActiveList(char c) {
		boolean stillChecking = false;
		for (Tokenizer automata : activeAutomata) {
			if (automata.isChecking())
				automata.checkNext(c);
			if (automata.isSucceeded()) {
				token.addToken(automata.getOutput());
				activeAutomata.clear();
				return;
			}
			stillChecking = stillChecking || automata.isChecking();
		}
		if (!stillChecking)
			checking = false;
	}

	/**
	 * Checks all the sub automata that can be accessed from the current
	 * state against the input character. Any that are still checking after
	 * the input are added to activeAutomata list.
	 * @param c character to be checked
	 */
	private void checkSubAutomata(char c) {
		for (Tokenizer automata : subAutomata.get(currentState)) {
			automata.reset();
			automata.checkNext(c);
			if (automata.isChecking())
				activeAutomata.add(automata);

		}
		if (!activeAutomata.isEmpty()) {
			token.addToken(new TextToken(charLog.toString()));
			charLog = new StringBuilder();
		}
	}

	/**
	 * Checks the activeAutomata list against the character input
	 * if the list is not empty. If the list is empty then the
	 * automata checks any possible sub automata that are accessible
	 * from the current state. If none succeed then the input is
	 * checked against the transitions available at the current
	 * state within this compound automata.
	 * @param c character to be checked
	 */
	@Override
	public void checkNext(char c) {
		if (!activeAutomata.isEmpty())
			checkActiveList(c);
		else {
			checkSubAutomata(c);
			if (activeAutomata.isEmpty())
				super.checkNext(c);
		}
	}

	/**
	 * Resets this Automata to an initial state for a new input
	 * String.
	 */
	@Override
	public void reset() {
		super.reset();
		activeAutomata = new ArrayList<>();
	}
}

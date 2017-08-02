package com.kaselabs.jmapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rick on 8/1/2017.
 *
 */
public class CompoundAutomata extends FiniteAutomata {

	public List<List<FiniteAutomata>> subAutomata;

	public List<FiniteAutomata> activeAutomata;

	public CompoundAutomata(TokenType tokenType, int numOfStates) {
		super(tokenType, numOfStates);
		subAutomata = new ArrayList<>();
		for (int i = 0; i < numOfStates; i++)
			subAutomata.add(new ArrayList<>());
		activeAutomata = new ArrayList<>();
	}

	public void addAutomata(int state, FiniteAutomata automata) {
			subAutomata.get(state).add(automata);
	}

	/**
	 * TODO could remove automata from active list when isChecking = false
	 * @param c
	 */
	private void checkActiveList(char c) {
		boolean stillChecking = false;
		for (FiniteAutomata automata : activeAutomata) {
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

	private void checkSubAutomata(char c) {
		for (FiniteAutomata automata : subAutomata.get(currentState)) {
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

	public void reset() {
		super.reset();
		activeAutomata = new ArrayList<>();
	}
}

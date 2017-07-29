package com.kaselabs.jmapper2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * TODO when in current state = -1, throw custom exception for all attempts to evaluate
 *
 *
 * Created by Rick on 7/28/2017.
 */
public class FiniteAutomata extends Recognizer {

	private int numOfStates;
	private List<Integer> finalStates;
	private int[] defaultStateMaps;
	private int currentState;
	private List<List<Transition>> transitionFunction;

	////////////////////////////////
	///// Creating an Automata /////
	////////////////////////////////

	public FiniteAutomata(int numOfStates) {
		this.numOfStates = numOfStates;
		this.finalStates = new ArrayList<>();
		transitionFunction = new ArrayList<>();
		defaultStateMaps = new int[numOfStates];
		for (int i = 0; i < numOfStates; i++) {
			defaultStateMaps[i] = -1;
			transitionFunction.add(new ArrayList<>());
		}
	}

	public void addFinalState(int finalState) {
		finalStates.add(finalState);
	}

	public void addDefaultStateFor(int startState, int defaultState) {
			defaultStateMaps[startState] = defaultState;
	}

	public void addTransition(int start, int end, List<Character> triggers) {
		transitionFunction.get(start).add(new Transition(start, end, triggers));
	}

	public void addTransition(int start, int end, char trigger) {
		transitionFunction.get(start).add(new Transition(start, end, trigger));
	}

	/////////////////////////////////
	///// Operating an Automata /////
	/////////////////////////////////

	@Override
	public void reset() {
		super.reset();
		currentState = 0;
	}

	@Override
	public void checkNext(char c) {
		super.checkNext(c);
		updateState(c);

		succeeded = finalStates.contains(currentState);

		if (finalStates.contains(currentState) || currentState == -1)
			checking = false;
	}

	private void updateState(char c) {
		boolean stateChanged = false;
		for (Transition transition : transitionFunction.get(currentState))
			if (transition.isTriggered(c)) {
				currentState = transition.getEnd();
				stateChanged = true;
			}
		if (!stateChanged)
			currentState = defaultStateMaps[currentState];
	}

	@Override
	public Token getOutput() {
		if (isChecking() || !isSucceeded())
			return null;
		return new TerminalToken(tokenType, charLog.toString());
	}

	private class Transition {

		private int start;
		private int end;
		private List<Character> triggers;

		private Transition(int start, int end, List<Character> triggers) {
			this.start = start;
			this.end = end;
			this.triggers = triggers;
		}

		private Transition(int start, int end, char trigger) {
			this.start = start;
			this.end = end;
			this.triggers = new ArrayList<>();
			triggers.add(trigger);
		}

		private int getStart() {
			return start;
		}

		private int getEnd() {
			return end;
		}

		private boolean isTriggered(char c) {
			return triggers.contains(c);
		}
	}
}
package com.kaselab.jmapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rick on 7/22/2017.
 */
public class DFA {

	private List<State> states = new ArrayList<State>();
	private State initialState;
	private State currentState;
	private boolean isChecking;
	private boolean isSucceeded;

	/**
	 * Creates a DFA from the data provided in the file.
	 * @param file
	 */
	public DFA(File file) {
		readStates(file);
		for (State state : states)
			if (state.isInitial()) {
				initialState = state;
				break;
			}
		currentState = initialState;
		isChecking = true;
		isSucceeded = false;
	}

	public void reset() {
		currentState = initialState;
		isChecking = true;
		isSucceeded = false;
	}

	public void nextState(char chr) {
		currentState = currentState.findNextState(chr);
		if (currentState == null)
			isChecking = false;
		else
			isSucceeded = currentState.isFinal();
	}

	public boolean checkString(String str) {
		reset();
		char[] chrs = str.toCharArray();
		boolean succeeded;
		for (char chr : chrs) {
			nextState(chr);
			if (!isChecking)
				break;
		}
		succeeded = isSucceeded;
		reset();
		return succeeded;
	}

	public boolean isChecking() {
		return isChecking;
	}

	public boolean isSucceeded() {
		return isSucceeded;
	}

	/**
	 * fills the states array with a list of all the states
	 * saved in the file passed as an argument.
	 * @param file source of the DFA data
	 */
	private void readStates(File file) {

	}



	private class State {
		private boolean isFinal;
		private boolean isInitial;
		private Map<Character, State> stateMap = new HashMap<>();

		/**
		 * Creates a state for to be used in a DFA. whether or not
		 * this is an initial state or final state is determined at
		 * creation and cannot be changed.
		 * @param isInitial whether or not this is the initial state
		 * @param isFinal whether or not this is a final state
		 */
		public State(boolean isInitial, boolean isFinal) {
			this.isInitial = isInitial;
			this.isFinal = isFinal;
		}

		/**
		 * adds the character as valid transitions to the indicated
		 * state.
		 * @param chr to direct to the given state
		 * @param state to be directed to by the given char
		 */
		public void addStateTransition(char chr, State state) {
			stateMap.put(chr, state);
		}

		/**
		 * adds all the characters in the char[] as valid transitions
		 * to the identified state.
		 * @param chrs to direct to the given state
		 * @param state to be directed to by the given chars
		 */
		public void addStateTransition(char[] chrs, State state) {
			for (char chr : chrs)
				addStateTransition(chr, state);
		}

		/**
		 * reutrns whether or not this state is set to be a valid
		 * final state.
		 * @return boolean valued indicating that this state is or
		 * is not a final state.
		 */
		public boolean isFinal() {
			return isFinal;
		}

		/**
		 * returns whether or not this state is set to be initial.
		 * @return boolean valued indicating that this state is or
		 * is not the initial state.
		 */
		public boolean isInitial() {
			return isInitial;
		}

		/**
		 * Returns the next state given a certain char as
		 * input.
		 * @param chr character to determine the next state
		 *            of the DFA.
		 * @return the next state specified.
		 */
		public State findNextState(char chr) {
			return stateMap.get(chr);
		}
	}
}

package com.kaselab.jmapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Rick on 7/22/2017.
 */
public class DFA {

	private List<State> states = new ArrayList<>();
	private List<State> finalStates = new ArrayList<>();
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

	/**
	 * resets the dfa back to its initial state and sets
	 * isChecking back to true.
	 */
	public void reset() {
		currentState = initialState;
		isChecking = true;
		isSucceeded = false;
	}

	/**
	 * decides the next state of the dfa determined by the char
	 * passed in the parameter.
	 * @param chr used to determine the next state of the dfa
	 */
	public void nextState(char chr) {
		currentState = currentState.findNextState(chr);
		if (currentState == null)
			isChecking = false;
		else
			isSucceeded = currentState.isFinal();
	}

	/**
	 * runs this dfa on the String passed and returns a boolean
	 * value indicating whether or not the string is defined by
	 * the DFA.
	 * @param str string to be tested by the dfa
	 * @return boolean representing whether or not the dfa
	 * describes the string passed
	 */
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

	/**
	 * Returns a boolean value representing whether or the dfa is
	 * still in a valid state to continue checking. If the dfa is
	 * no longer in a valid state then the char array being tested
	 * should be evaluated to determine if it is described by this
	 * dfa.
	 * @return a boolean value representing whether or the dfa is
	 * still in a valid state to continue checking
	 */
	public boolean isChecking() {
		return isChecking;
	}

	/**
	 * Returns a boolean value representing whether or not the dfa
	 * is currently in a final state. This value should be ignored
	 * until isChecking returns false so that it can be known that
	 * the dfa has analyzed as much as possible.
	 * @return a boolean value representing whether or not the dfa
	 * is currently in a final state.
	 */
	public boolean isSucceeded() {
		return isSucceeded;
	}


	///// DFA parsing code /////

	/**
	 * Fills the states array with a list of all the states saved in
	 * the file passed as an argument.
	 * @param file source of the DFA data
	 */
	private void readStates(File file) {
		Iterator<String> i = IOHandler.readFileLineIterator(file);
		String interpreterType = "";
		String curString;
		while (i.hasNext()) {
			curString = i.next();
			if (curString.equals("STATES") || curString.equals("TRANSITIONS")) {
				interpreterType = curString;
				continue;
			}
			if (interpreterType.equals("STATES"))
				interpretAsState(curString);
			else if (interpreterType.equals("TRANSITIONS"))
				interpretAsTransition(curString);
		}
	}

	/**
	 * Interprets a string as a code that describes a State which
	 * the DFA can be in adds that state to the list of possible
	 * states.
	 * @param code to be interpreted
	 */
	private void interpretAsState(String code) {
		String[] values = code.split(",");
		int index = Integer.valueOf(values[0].split(":")[1].trim());
		String defaultState = values[1].split(":")[1].trim();
		boolean isInitial = Boolean.valueOf(values[2].split(":")[1].trim());
		boolean isFinal = Boolean.valueOf(values[3].split(":")[1].trim());
		states.add(new State(index, isInitial, isFinal));
		if (isFinal)
			finalStates.add(states.get(index));
		if (!defaultState.equals("null"))
			states.get(index).setDefaultState(
					states.get(Integer.valueOf(defaultState)));
	}

	/**
	 * Interprets a string as a code that describes a transition between
	 * two states that the DFA can be in adds the transition to the
	 * appropriate state.
	 *
	 * ERROR may arise with the interpretation of special characters like
	 * \n \t and so on.
	 * @param code to be interpreted
	 */
	private void interpretAsTransition(String code) {
		String[] values = code.split(",");
		// prepare indices
		int firstStateIndex = Integer.valueOf(values[0].split(":")[1].trim());
		int secondStateIndex = Integer.valueOf(values[1].split(":")[1].trim());
		// prepare triggers
		String triggerString = values[2].split(":")[1].trim();
		triggerString = triggerString.substring(1, triggerString.length() - 1);
		String[] triggers = triggerString.split(" ");
		// enter state transitions
		for (String trigger : triggers)
			states.get(firstStateIndex).addStateTransition(toChar(trigger),
					states.get(secondStateIndex));
	}

	/**
	 * Simple parser for the characters that will be encountered in the
	 * DFA files to the characters that they actually represents.
	 * @param str representing a single character
	 * @return the character the string is meant to represent
	 */
	private char toChar(String str) {
		switch (str) {
			case "\\n":
				return System.getProperty("line.separator").charAt(0);
			default:
				return str.charAt(0);
		}
	}

	//////////////////////////////////////
	///// Debugging and clarity Code /////
	//////////////////////////////////////

	/**
	 * Generates a string that details the structure of the
	 * dfa in a human readable way.
	 * @return string describing the dfa
	 */
	@Override
	public String toString() {
		if (states.size() == 0)
			return "empty DFA";
		StringBuilder build = new StringBuilder();
		for (State state : states) {
			build.append(state);
			build.append("\n");
			build.append(getConnectionString(state));
		}
		return build.toString();
	}

	/**
	 * Returns a string that presents, in a human readable way,
	 * what a particular transitions are attached to a particular
	 * state.
	 * @param state to print a list of transitions for
	 * @return human readable list of transitions
	 */
	public String getConnectionString(State state) {
		StringBuilder build = new StringBuilder();
		List<Character> triggers;
		for (Map.Entry<State, List<Character>> connection
				: state.getStateMapping().entrySet()) {
			triggers = connection.getValue();
			build.append("\tConnected to: ");
			build.append(connection.getKey().getId());
			build.append(", Triggered by: ");
			for (int i = 0; i < triggers.size(); i++) {
				build.append(triggers.get(i));
				if (i < triggers.size() - 1)
					build.append(", ");
			}
		}
		build.append("\n");
		return build.toString();
	}
}

package com.kaselab.jmapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
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


	///// DFA parsing code /////

	/**
	 * fills the states array with a list of all the states
	 * saved in the file passed as an argument.
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
	 * simple parser for the characters that will be encountered in the
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

	///// Debugging and clarity Code /////

	@Override
	public String toString() {
//		if (states.size() == 0)
//			return "empty DFA";
		StringBuilder build = new StringBuilder();
		List<Character> triggers;
		for (State state : states) {
			build.append(state);
			build.append("\n");
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
		}
		return build.toString();
	}


	/**
	 * Represents an individual state that the DFA can be in as
	 * well as all the transitions possible within the state.
	 */
	private class State {
		private int id;
		private boolean isFinal;
		private boolean isInitial;
		private Map<Character, State> stateMap = new HashMap<>();
		private State defaultState = null;

		/**
		 * Creates a state for to be used in a DFA. whether or not
		 * this is an initial state or final state is determined at
		 * creation and cannot be changed.
		 * @param id identifier for human debugging and understanding
		 * @param isInitial whether or not this is the initial state
		 * @param isFinal whether or not this is a final state
		 */
		private State(int id, boolean isInitial, boolean isFinal) {
			this.id = id;
			this.isInitial = isInitial;
			this.isFinal = isFinal;
		}

		/**
		 * adds the character as valid transitions to the indicated
		 * state.
		 * @param chr to direct to the given state
		 * @param state to be directed to by the given char
		 */
		private void addStateTransition(char chr, State state) {
			stateMap.put(chr, state);
		}

		/**
		 * adds all the characters in the char[] as valid transitions
		 * to the identified state.
		 * @param chrs to direct to the given state
		 * @param state to be directed to by the given chars
		 */
		private void addStateTransition(char[] chrs, State state) {
			for (char chr : chrs)
				addStateTransition(chr, state);
		}

		/**
		 * sets the state that should be pointed too if the an
		 * unmapped char is passed. this value is null unless
		 * otherwise set.
		 * @param defaultState to be pointed to when unmapped keys are
		 *                     processed.
		 */
		private void setDefaultState(State defaultState) {
			this.defaultState = defaultState;
		}

		/**
		 * reutrns whether or not this state is set to be a valid
		 * final state.
		 * @return boolean valued indicating that this state is or
		 * is not a final state.
		 */
		private boolean isFinal() {
			return isFinal;
		}

		/**
		 * returns whether or not this state is set to be initial.
		 * @return boolean valued indicating that this state is or
		 * is not the initial state.
		 */
		private boolean isInitial() {
			return isInitial;
		}

		/**
		 * returns the ID of this state for human inspection.
		 * @return the id of this state
		 */
		private int getId() {
			return id;
		}

		/**
		 * Returns the next state given a certain char as input. If
		 * the specified char is not valid then returns the state
		 * specified as default. In no state was specified as default
		 * then returns null.
		 * @param chr character to determine the next state
		 *            of the DFA.
		 * @return the next state specified.
		 */
		private State findNextState(char chr) {
			State state = stateMap.get(chr);
			return (state != null) ? state : defaultState;
		}

		/**
		 * Takes the one to one mapping of characters to States and
		 * reverses it to become a one to many mapping of States to
		 * characters.
		 * @return Map that maps all the States this state can
		 * transition to with a list of all the characters that will
		 * cause that transition
		 */
		private Map<State, List<Character>> getStateMapping() {
			Map<State, List<Character>> reverseMap = new HashMap<>();
			State curState;
			for (Map.Entry<Character, State> entry : stateMap.entrySet()) {
				curState = entry.getValue();
				if (!reverseMap.containsKey(curState))
					reverseMap.put(curState, new LinkedList<>());
				reverseMap.get(curState).add(entry.getKey());
			}
			return reverseMap;
		}

		/**
		 * returns a String representation of the State.
		 * @return String representation of the State
		 */
		@Override
		public String toString() {
			StringBuilder build = new StringBuilder("State[");
			build.append("ID: " + id);
			build.append(", Initial: " + isInitial);
			build.append(", Final: " + isFinal);
			build.append(", Default transition: ");
			build.append((defaultState != null) ? defaultState.id : null);
			build.append("]");
			return build.toString();
		}
	}
}

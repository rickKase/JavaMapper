package com.kaselabs.jmapper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Represents an individual state that the DFA can be in as
 * well as all the transitions possible within the state.
 */
public class State {

	private int id;
	private boolean isInitial;
	private boolean isFinal;
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
	public State(int id, boolean isInitial, boolean isFinal) {
		this.id = id;
		this.isInitial = isInitial;
		this.isFinal = isFinal;
	}

	/**
	 * Adds the character as valid transitions to the indicated
	 * state.
	 * @param chr to direct to the given state
	 * @param state to be directed to by the given char
	 */
	public void addStateTransition(char chr, State state) {
		stateMap.put(chr, state);
	}

	/**
	 * Adds all the characters in the char[] as valid transitions
	 * to the identified state.
	 * @param chrs to direct to the given state
	 * @param state to be directed to by the given chars
	 */
	public void addStateTransition(char[] chrs, State state) {
		for (char chr : chrs)
			addStateTransition(chr, state);
	}

	/**
	 * Sets the state that should be pointed too if the an
	 * unmapped char is passed. this value is null unless
	 * otherwise set.
	 * @param defaultState to be pointed to when unmapped keys are
	 *                     processed.
	 */
	public void setDefaultState(State defaultState) {
		this.defaultState = defaultState;
	}

	/**
	 * Returns whether or not this state is set to be a valid
	 * final state.
	 * @return boolean valued indicating that this state is or
	 * is not a final state.
	 */
	public boolean isFinal() {
		return isFinal;
	}

	/**
	 * Returns whether or not this state is set to be initial.
	 * @return boolean valued indicating that this state is or
	 * is not the initial state.
	 */
	public boolean isInitial() {
		return isInitial;
	}

	/**
	 * Returns the ID of this state for human inspection.
	 * @return the id of this state
	 */
	public int getId() {
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
	public State findNextState(char chr) {
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
	public Map<State, List<Character>> getStateMapping() {
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
	 * Returns a String representation of the State.
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
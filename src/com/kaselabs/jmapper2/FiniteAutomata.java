package com.kaselabs.jmapper2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by Rick on 7/28/2017.
 *
 * Creates an automata that evaluates whether or not a string
 * fits a regular expression by evaluating it one character at a
 * time.
 *
 * TODO when current state = -1, throw custom exception for all attempts to evaluate
 */
public class FiniteAutomata extends Recognizer {

	private int numOfStates;
	private List<Integer> finalStates;
	private int[] defaultStateMaps;
	private List<List<Transition>> transitionFunction;

	private int currentState;

	////////////////////////////////
	///// Creating an Automata /////
	////////////////////////////////

	/**
	 * Constructs a Finite Automata that can be used to identify
	 * regular expressions in a string.
	 * @param numOfStates number of states used in this Automata
	 */
	public FiniteAutomata(TokenType tokenType, int numOfStates) {
		super(tokenType);
		this.numOfStates = numOfStates;
		this.finalStates = new ArrayList<>();
		transitionFunction = new ArrayList<>();
		defaultStateMaps = new int[numOfStates];
		for (int i = 0; i < numOfStates; i++) {
			defaultStateMaps[i] = -1;
			transitionFunction.add(new ArrayList<>());
		}
	}

	/**
	 * Adds the state represented by the argument passed as a valid
	 * final state for the current FiniteAutomata.
	 * @param finalState state that if ended on will confirm the input.
	 */
	public void addFinalState(int finalState) {
		finalStates.add(finalState);
	}

	/**
	 * Adds a default transition to a state. If a state with a default
	 * transition receives an input that does not trigger any transition
	 * then the state will transition to its default state. If no default
	 * state is set and the state receives an input that does not trigger
	 * any of its transitions than the Automata will transition to an
	 * invalid state.
	 * @param startState state to set the default to
	 * @param defaultState default state to be set
	 */
	public void addDefaultStateFor(int startState, int defaultState) {
			defaultStateMaps[startState] = defaultState;
	}

	/**
	 * Adds a transition from one state to another given a set of trigger
	 * characters to initiate the transition.
	 * @param start state to adopt the transitions
	 * @param end state this transition leads to
	 * @param triggers characters that trigger this transition
	 */
	public void addTransition(int start, int end, List<Character> triggers) {
		transitionFunction.get(start).add(new Transition(start, end, triggers));
	}

	/**
	 * Adds a transition from one state to another given a trigger characters
	 * to initiate the transition.
	 * @param start state to adopt the transitions
	 * @param end state this transition leads to
	 * @param trigger character that triggers this transition
	 */
	public void addTransition(int start, int end, char trigger) {
		transitionFunction.get(start).add(new Transition(start, end, trigger));
	}

	/////////////////////////////////
	///// Operating an Automata /////
	/////////////////////////////////

	/**
	 * Resets the automata to its initial state so that it can
	 * begin evaluating a new input.
	 */
	@Override
	public void reset() {
		super.reset();
		currentState = 0;
	}

	/**
	 * Runs the automata with the given input character advancing the
	 * evaulation of the input. The input is evaluated 1 character at
	 * a time to allow for the program to find several recognizable
	 * strings within the a given input.
	 *
	 * If the next character of input renders the automata invalidated
	 * then the current state is set to an invalid one and the checking
	 * value is set to false.
	 *
	 * If the next character of input renders the automata in a final
	 * state then the checking value is set ot false, the succeeded
	 * value is set to true, and the getOutput will return a non-null
	 * value.
	 * @param c character to be checked
	 */
	@Override
	public void checkNext(char c) {
		super.checkNext(c);
		updateState(c);

		succeeded = finalStates.contains(currentState);
		if (succeeded || isStateValid(currentState))
			checking = false;
	}

	/**
	 * Updates the state of the automata by finding the state
	 * that would be triggered by the input on the current state.
	 * That state is then set as the input.
	 * @param c character of input
	 */
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

	/**
	 * Returns null if the automata is either failed or still checking,
	 * otherwise will return a Token representing the value of found
	 * Token.
	 * @return Token that was recognized
	 */
	@Override
	public Token getOutput() {
		if (isChecking() || !isSucceeded())
			return null;
		return new TerminalToken(tokenType, charLog.toString());
	}

	/**
	 * convenience method for determining whether or not a state
	 * is a valid state under the definition of this Automata.
	 * @param state to be checked for validity
	 * @return boolean representing whether or not the state is
	 * valid
	 */
	private boolean isStateValid(int state) {
		return state > -1 && state < numOfStates;
	}

	/**
	 * Transition from one state to another state given a particular
	 * trigger or set of triggers.
	 */
	private class Transition {

		private int start;
		private int end;
		private List<Character> triggers;

		/**
		 * Creates a trigger on one state leading to another state given
		 * a particular set of input characters.
		 * @param start state to start the transition
		 * @param end state to end the transition
		 * @param triggers characters that trigger the transition
		 */
		private Transition(int start, int end, List<Character> triggers) {
			this.start = start;
			this.end = end;
			this.triggers = triggers;
		}

		/**
		 * Creates a trigger on one state leading to another state given
		 * a particular input character.
		 * @param start state to start the transition
		 * @param end state to end the transition
		 * @param trigger character that triggers the transition
		 */
		private Transition(int start, int end, char trigger) {
			this.start = start;
			this.end = end;
			this.triggers = new ArrayList<>();
			triggers.add(trigger);
		}

		/**
		 * Returns the state that this transition will originate in.
		 * @return the starting state for this transition
		 */
		private int getStart() {
			return start;
		}

		/**
		 * Returns the state that this transition leads to.
		 * @return the ending state of this transition
		 */
		private int getEnd() {
			return end;
		}

		/**
		 * Returns a boolean value indicating whether or not this
		 * transition is triggered by the character passed as an
		 * argument.
		 * @param c character to be checked
		 * @return true if triggered, false if otherwise
		 */
		private boolean isTriggered(char c) {
			return triggers.contains(c);
		}
	}
}
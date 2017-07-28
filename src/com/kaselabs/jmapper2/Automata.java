package com.kaselabs.jmapper2;

import java.util.List;

/**
 * Created by Rick on 7/27/2017.
 */
public class Automata {

	private int states;
	private int initialState;
	private int[] finalStates;
	/**
	 * stores the possible transitions of the Automata in a 2D List
	 * so only the current state of the automata needs to be checked.
	 */
	private List<List<Transition>> transitionFunction;
	private int defaultState;

	public Automata(int states, int initialState, int[] finalStates) {

	}

	public void addTransition(Transition transition) {
		int startState = transition.getStart();
		transitionFunction.get(startState).add(transition);
	}



}

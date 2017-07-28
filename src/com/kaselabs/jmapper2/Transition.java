package com.kaselabs.jmapper2;

import java.util.List;

/**
 * Created by Rick on 7/27/2017.
 */
public class Transition {

	private int start;
	private int end;
	private List<Character> triggers;

	public Transition(int start, int end, List<Character> triggers) {
		this.start = start;
		this.end = end;
		this.triggers = triggers;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	public boolean isTriggered(char trigger) {
		return triggers.contains(trigger);
	}
}

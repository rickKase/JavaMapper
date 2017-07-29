package com.kaselabs.jmapper2;

import java.util.List;

/**
 * Created by Rick on 7/28/2017.
 *
 * Designed specifically to work in a context where there is
 * a string of characters identifiable by index stored apart
 * from this class. The external class with knowledge of that
 * string of characters will pass that string to this class,
 * one by one, and monitor the checking and succeeded values
 * in order to determine if the string is recognized.
 *
 * Each Recognizer is intended to work in tandem with other
 * several other recognizers, all at different levels of a
 * tree hierarchy.
 *
 */
public abstract class Recognizer {

	private boolean checking;
	private boolean succeeded;
	private StringBuilder charLog;

	public Recognizer() {
		reset();
	}

	public boolean isChecking() {
		return checking;
	}

	public boolean isSucceeded() {
		return succeeded;
	}

	public void reset() {
		checking = true;
		succeeded = false;
		charLog = new StringBuilder();
	}

	public void checkNext(char c) {
		charLog.append(c);
	}

	public abstract Token getOutput();

	public String getText() {
		return charLog.toString();
	}

	public abstract void throwException() throws IllegalArgumentException;

}

package com.kaselabs.jmapper;

/**
 * Created by Rick on 7/28/2017.
 *
 * Designed specifically to work in a context where there is
 * a string of characters identifiable by index stored apart
 * from this class. The external class with knowledge of that
 * string of characters will pass that string to this class
 * one character at a time, and monitor the checking and
 * succeeded values in order to determine if the string is
 * recognized.
 *
 * Each Tokenizer is intended to work in tandem with several
 * other recognizers, all at different levels of a tree
 * hierarchy.
 *
 */
public abstract class Tokenizer {


	protected boolean checking;
	protected boolean succeeded;
	/** Internal buffer of characters checked for output */
	protected StringBuilder charLog;
	protected TokenType tokenType;

	protected CompoundToken token;

	/**
	 * Creates a new recognizer and properly sets all values
	 */
	public Tokenizer(TokenType tokenType) {
		this.tokenType = tokenType;
		reset();
	}

	/**
	 * Returns the TokenType that this recognizer is designed to
	 * detect.
	 * @return the TokenType this recognizer is designed to detect
	 */
	public TokenType getTokenType() {
		return tokenType;
	}

	/**
	 * Will return true if the recognizer is still checking whether
	 * or not the String is identified and false if it finishes. The
	 * success state of the recognizer and the output of it should
	 * not be checked or obtained until this value returns false.
	 * @return boolean value indicating whether or not this
	 * recognizer is still checking or not.
	 */
	public boolean isChecking() {
		return checking;
	}

	/**
	 * Will return true if the recognizer has finished checking
	 * valid input and return false if that input input is either
	 * not finished or invalid. Because false is returned when the
	 * input is still not done being processed, this value is not
	 * meaningful until isChecking returns false.
	 * @return boolean value representing whether or not the input
	 * is valid.
	 */
	public boolean isSucceeded() {
		return succeeded;
	}

	/**
	 * Resets the recognizer for a new set of input in the off state.
	 * For the next set of input to begin being processed, startChecking()
	 * must also be called.
	 */
	public void reset() {
		checking = true;
		succeeded = false;
		charLog = new StringBuilder();
		token = new CompoundToken(tokenType);
	}

	/**
	 * Runs the recognizer on the next character in the input.
	 * @param c character to be checked
	 */
	public void checkNext(char c) {
		charLog.append(c);
	}

	/**
	 * Returns the output Token if the Tokenizer is both not
	 * checking and has succeeded. Otherwise it will return null.
	 * @return the output Token of the string.
	 */
	public abstract Token getOutput();

}

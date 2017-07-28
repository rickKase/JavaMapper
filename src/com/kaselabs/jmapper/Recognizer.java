package com.kaselabs.jmapper;

import java.util.List;

/**
 * Created by Rick on 7/28/2017.
 */
public interface Recognizer {

	public boolean isChecking();
	public boolean isSucceeded();

	public void reset();
	public void checkNext(char c);

	public void setStartIndex(int i);
	public Token getOutput(int i, List<Character> input);

}

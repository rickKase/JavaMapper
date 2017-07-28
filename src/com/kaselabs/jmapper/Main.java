package com.kaselabs.jmapper;

import java.io.File;

/**
 * Created by Rick on 7/21/2017.
 */
public class Main {

	public static void main(String[] args) {
		DFA dfa = new DFA(new File("data\\dfas\\multiline-comment.txt"));

		System.out.println(dfa);
	}

}

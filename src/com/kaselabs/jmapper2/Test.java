package com.kaselabs.jmapper2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rick on 7/29/2017.
 */
public class Test {

	public static void main(String[] args) {
		/* this is a comment */
		FiniteAutomata multilineCommentAutomata = new FiniteAutomata(5);

		multilineCommentAutomata.addFinalState(4);
		multilineCommentAutomata.addDefaultStateFor(2, 2);
		multilineCommentAutomata.addDefaultStateFor(3, 2);
		multilineCommentAutomata.addTransition(0, 1, '/');
		multilineCommentAutomata.addTransition(1, 2, '*');
		multilineCommentAutomata.addTransition(2, 3, '*');
		multilineCommentAutomata.addTransition(3, 3, '*');
		multilineCommentAutomata.addTransition(3, 4, '/');


		FiniteAutomata stringAutomata = new FiniteAutomata(4);


		stringAutomata.addFinalState(3);
		stringAutomata.addDefaultStateFor(1, 1);
		stringAutomata.addTransition(0, 1, '"');
		stringAutomata.addTransition(1, 3, '"');
		stringAutomata.addTransition(1, 2, '\\');

		List<Character> chars = new ArrayList<>();
		chars.add('\'');
		chars.add('\"');
		chars.add('\\');
		chars.add('\t');
		chars.add('\n');
		chars.add('\b');
		chars.add('\f');
		chars.add('\r');
		stringAutomata.addTransition(2, 1, chars);

		Scanner scanner = new Scanner(System.in);


		String string = scanner.nextLine();


		for (char chr : string.toCharArray()) {
			if (stringAutomata.isChecking())
				stringAutomata.checkNext(chr);
			if (multilineCommentAutomata.isChecking())
				multilineCommentAutomata.checkNext(chr);

			if (stringAutomata.isSucceeded())
				System.out.println(stringAutomata.getOutput().getContent());
			if (multilineCommentAutomata.isSucceeded())
				System.out.println(multilineCommentAutomata.getOutput().getContent());

			if (!stringAutomata.isChecking()
					&& !multilineCommentAutomata.isChecking()) {
				stringAutomata.reset();
				multilineCommentAutomata.reset();
			}

		}
	}

}

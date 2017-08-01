package com.kaselabs.jmapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Rick on 7/29/2017.
 *
 */
public class Test {

	public static void main(String[] args) {
		test2();
	}




	public static void test2() {
		Dao dao = new Dao();
		FiniteAutomata fa = (FiniteAutomata) dao.readRecognizer(
				new File("data\\recognizers\\multiline-comment.xml"));

		Scanner scanner = new Scanner(System.in);
		String string = scanner.nextLine();

		for (char chr : string.toCharArray()) {
			fa.checkNext(chr);
			if (fa.isSucceeded())
				System.out.println(fa.getOutput().getText());
			if (!fa.isChecking())
				fa.reset();

		}

	}






	public static void test1() {
		/* Create a multiline Automata */
		FiniteAutomata multilineCommentAutomata
				= new FiniteAutomata(TokenType.MULTILINE_COMMENT ,5);

		multilineCommentAutomata.addFinalState(4);
		multilineCommentAutomata.addDefaultStateFor(2, 2);
		multilineCommentAutomata.addDefaultStateFor(3, 2);
		multilineCommentAutomata.addTransition(0, 1, '/');
		multilineCommentAutomata.addTransition(1, 2, '*');
		multilineCommentAutomata.addTransition(2, 3, '*');
		multilineCommentAutomata.addTransition(3, 3, '*');
		multilineCommentAutomata.addTransition(3, 4, '/');


		/* Create a String Automata */
		FiniteAutomata stringAutomata
				= new FiniteAutomata(TokenType.STRING_LITERAL, 4);

		stringAutomata.addFinalState(3);
		stringAutomata.addDefaultStateFor(1, 1);
		stringAutomata.addTransition(0, 1, '"');
		stringAutomata.addTransition(1, 3, '"');
		stringAutomata.addTransition(1, 2, '\\');
		List<Character> chars = new ArrayList<>();
		chars.add('\'');
		chars.add('\"');
		chars.add('\\');
		chars.add('t');
		chars.add('n');
		chars.add('b');
		chars.add('f');
		chars.add('r');
		stringAutomata.addTransition(2, 1, chars);

		Scanner scanner = new Scanner(System.in);

		String string = scanner.nextLine();


		for (char chr : string.toCharArray()) {
			if (stringAutomata.isChecking())
				stringAutomata.checkNext(chr);
			if (multilineCommentAutomata.isChecking())
				multilineCommentAutomata.checkNext(chr);

			if (stringAutomata.isSucceeded())
				System.out.println(stringAutomata.getOutput().getText());
			if (multilineCommentAutomata.isSucceeded())
				System.out.println(multilineCommentAutomata.getOutput().getText());

			if (!stringAutomata.isChecking()
					&& !multilineCommentAutomata.isChecking()) {
				stringAutomata.reset();
				multilineCommentAutomata.reset();
			}
		}
	}

}

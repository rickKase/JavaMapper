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
		test3();
	}




	public static void test3() {
		FiniteAutomata javaCharAutomata = new FiniteAutomata(
				TokenType.JAVA_SPECIAL_CHARACTER, 3);
		javaCharAutomata.addFinalState(2);
		javaCharAutomata.addTransition(0, 1, '\\');
		List<Character> javaCharTriggers = new ArrayList<>();
		javaCharTriggers.add('\'');
		javaCharTriggers.add('\"');
		javaCharTriggers.add('\\');
		javaCharTriggers.add('t');
		javaCharTriggers.add('n');
		javaCharTriggers.add('b');
		javaCharTriggers.add('f');
		javaCharTriggers.add('r');
		javaCharAutomata.addTransition(1, 2, javaCharTriggers);

		FiniteAutomata uniCharAutomata = new FiniteAutomata(
				TokenType.UNICODE_CHARACTER, 7);
		uniCharAutomata.addFinalState(6);
		uniCharAutomata.addTransition(0, 1, '\\');
		uniCharAutomata.addTransition(1, 2, 'u');
		List<Character> uniCharTriggers = new ArrayList<>();
		uniCharTriggers.add('0');
		uniCharTriggers.add('1');
		uniCharTriggers.add('2');
		uniCharTriggers.add('3');
		uniCharTriggers.add('4');
		uniCharTriggers.add('5');
		uniCharTriggers.add('6');
		uniCharTriggers.add('7');
		uniCharTriggers.add('8');
		uniCharTriggers.add('9');
		uniCharTriggers.add('a');
		uniCharTriggers.add('b');
		uniCharTriggers.add('c');
		uniCharTriggers.add('d');
		uniCharTriggers.add('e');
		uniCharTriggers.add('f');
		uniCharTriggers.add('A');
		uniCharTriggers.add('B');
		uniCharTriggers.add('C');
		uniCharTriggers.add('D');
		uniCharTriggers.add('E');
		uniCharTriggers.add('F');
		uniCharAutomata.addTransition(2, 3, uniCharTriggers);
		uniCharAutomata.addTransition(3, 4, uniCharTriggers);
		uniCharAutomata.addTransition(4, 5, uniCharTriggers);
		uniCharAutomata.addTransition(5, 6, uniCharTriggers);

		CompoundAutomata stringAutomata = new CompoundAutomata(
				TokenType.STRING_LITERAL, 3);
		stringAutomata.addFinalState(2);
		stringAutomata.addDefaultStateFor(1,1);
		stringAutomata.addTransition(0, 1, '\"');
		stringAutomata.addTransition(1, 2, '\"');
		stringAutomata.addAutomata(1, uniCharAutomata);
		stringAutomata.addAutomata(1, javaCharAutomata);

		Scanner scanner = new Scanner(System.in);
		String testString = scanner.nextLine();

//		String testString = "\"\\n this test worked! \\u4554!!!\"";

		for (char chr : testString.toCharArray()) {
			stringAutomata.checkNext(chr);




			if (stringAutomata.isSucceeded())
				printToken(stringAutomata.getOutput());

			if (!stringAutomata.isChecking()) {
				stringAutomata.reset();
			}
		}
	}


	public static void printToken(Token token) {
		CompoundToken t = (CompoundToken) token;
		for (Token st : t.getTokens())
			System.out.print(st.getText());
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
			if (!fa.isChecking()) {
				fa.reset();
			}

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

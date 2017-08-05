package com.kaselabs.jmapper;

import com.kaselabs.jmapper.io.Dao;
import com.kaselabs.jmapper.token.CompoundToken;
import com.kaselabs.jmapper.token.Token;
import com.kaselabs.jmapper.token.TokenType;
import com.kaselabs.jmapper.tokenizer.Automata;
import com.kaselabs.jmapper.tokenizer.CompoundAutomata;
import com.kaselabs.jmapper.tokenizer.SimpleAutomata;

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
		test4();
	}


	public static void test4() {
		Dao dao = new Dao();
		Automata stringAutomata = (Automata) dao.readRecognizer(
				new File("data\\recognizers\\string.xml"));

		System.out.println(stringAutomata);

		Scanner scanner = new Scanner(System.in);
		String string = scanner.nextLine();

		for (char chr : string.toCharArray()) {
				stringAutomata.checkNext(chr);

			if (stringAutomata.isSucceeded())
				printToken(stringAutomata.getOutput());

			if (!stringAutomata.isChecking()) {
				stringAutomata.reset();
			}
		}
	}

	public static void test3() {
		Automata javaCharAutomata = new SimpleAutomata(
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

		Automata uniCharAutomata = new SimpleAutomata(
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
		Automata fa = (Automata) dao.readRecognizer(
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
		Automata multilineCommentAutomata
				= new SimpleAutomata(TokenType.MULTILINE_COMMENT ,5);

		multilineCommentAutomata.addFinalState(4);
		multilineCommentAutomata.addDefaultStateFor(2, 2);
		multilineCommentAutomata.addDefaultStateFor(3, 2);
		multilineCommentAutomata.addTransition(0, 1, '/');
		multilineCommentAutomata.addTransition(1, 2, '*');
		multilineCommentAutomata.addTransition(2, 3, '*');
		multilineCommentAutomata.addTransition(3, 3, '*');
		multilineCommentAutomata.addTransition(3, 4, '/');


		/* Create a String Automata */
		Automata stringAutomata
				= new SimpleAutomata(TokenType.STRING_LITERAL, 4);

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

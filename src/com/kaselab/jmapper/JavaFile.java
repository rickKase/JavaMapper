package com.kaselab.jmapper;

/**
 * Created by Rick on 7/21/2017.
 */
public class JavaFile {

	int x = 1_234;
	double y = 234___________23.234_23;


	public JavaFile(char[] chars) {
		StringBuilder build = new StringBuilder();
		for (char chr : chars) {
			if (!build.toString().equals("")) {

			} else
				System.out.println(build.toString());
		}
	}






//	public JavaFile(char[] chars) {
//		StringBuilder builder = new StringBuilder();
//		for (char chr : chars) {
//			if (Character.isWhitespace(chr)) {
//				if (!builder.toString().equals(""))
//					System.out.println(builder.toString());
//				builder = new StringBuilder();
//			} else if (isPunctuation(chr)) {
//				if (!builder.toString().equals(""))
//					System.out.println(builder.toString());
//				builder = new StringBuilder();
//				System.out.println(chr);
//			} else if (isBracket(chr)) {
//				if (!builder.toString().equals(""))
//					System.out.println(builder.toString());
//				builder = new StringBuilder();
//				System.out.println(chr);
//			 } else
//				builder.append(chr);
//		}
//	}
//
//	private boolean isPunctuation(char chr) {
//		return ",.;:\"'?".indexOf(chr) >= 0;
//	}
//
//	private boolean isBracket(char chr) {
//		return ",.;:\"'?".indexOf(chr) >= 0;
//	}

}
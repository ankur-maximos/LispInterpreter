/*
 * maximos: 03/03/2015
 */

package com.maximos.sexp;

public class Constants {

	/* Error message constants */
	public static String NEGATIVE_PARENTHESIS = "Closing parenthesis greater than Opening parenthesis";
	public static String POSITIVE_PARENTHESIS = "Opening parenthesis greater than Closing Parenthesis";
	public static String INVALID_ATOM = "Invalid Atom";
	public static String INVALID_IDENTIFIER = "Invalid Identifier";
	public static String INVALID_SEXP = "Invalid S-Expression";
	public static String INVALID_LIST = "Invalid list";
	public static String MULTIPLE_DOTS = "Multiple dots in S-expression";
	public static String EMPTY_DOT_EXP = "Empty atom not allowed in dot expression";
	public static String EMPTY_INPUT_STRING = "> empty input string";
	public static String OUTPUT_GREATER_THAN = "> ";
	public static String EXTRA_DOTS = " :Extra dots in S-Expression";

	/* Constants used in the interpreter */
	public static String OPENING_BRACES = "(";
	public static String EMPTY_ATOM = "NIL";

}

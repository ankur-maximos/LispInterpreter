/*
 * maximos: 03/03/2015
 */

package com.maximos.sexp;

public class Constants {

	/* Error message constants */
	public static String NEGATIVE_PARENTHESIS = " Closing parenthesis greater than Opening parenthesis";
	public static String POSITIVE_PARENTHESIS = " Opening parenthesis greater than Closing Parenthesis";
	public static String INVALID_ATOM = " Invalid Atom";
	public static String INVALID_IDENTIFIER = " Invalid Identifier";
	public static String INVALID_SEXP = " Invalid S-Expression";
	public static String INVALID_LIST = " Invalid list";
	public static String MULTIPLE_DOTS = " Multiple dots in S-expression";
	public static String EMPTY_DOT_EXP = " Empty atom not allowed in dot expression";
	public static String EMPTY_INPUT_STRING = "> empty input string";
	public static String OUTPUT_GREATER_THAN = "> ";
	public static String EXTRA_DOTS = " .. Extra dots in S-Expression";
	public static String ERROR = "ERROR";

	/* Constants used in the interpreter */
	public static String OPENING_BRACES = "(";
	public static String CLOSING_BRACES = ")";
	public static String EMPTY_ATOM = "NIL";
	public static String TRUE_ATOM = "T";

	/* Error messages for Evaluator */
	public static String INVALID_LISP_EXPRESSION = "Invalid Lisp-Expression";
	public static String ATOMIC_ONLY_ALLOWED = "Only Atomic S-Expression are allowed";
	public static String ATOMIC_ONLY_INTEGER_ALLOWED = "Only Integer atoms are allowed";
	public static String PRIMITIVE_NOT_FOUND = "Primitive not found";
	public static String INVALID_FUNCTION = "Function not found";
	public static String EMPTY_CONDITION = "No Condition evaluates to true";
	public static String PARAMETER_NOT_FOUND = "function or Parameter not found/error in alist";
	public static String PARAMETER_COUNT_NOT_MATCHING = "Parameter count is not matching in function call";
	public static String INTERNAL_ERROR_FUNC = "Primitive function call error";
	public static String INVALID_FUNCTION_NAME = "Invalid Function Name";
	public static String UNEXPEXTED_INPUT = "Unexpected Input";
	public static String METHOD_ALREADY_DEFINED = "Method is already defined";
	public static String ALIST_EMPTY = "ALIST is EMPTY";
	public static String TOO_MANY_ARGUMENTS = "Too many arguments";
	public static String LESS_ARGUMENTS = "Too less arguments";
	public static String NO_ARGUMENTS = "No Arguments Defined";

	/* Messages for Evaluator */
	public static String SUCESSFUL_DEFINED_FUNCTION = "Custom Function is sucessfullly added";

	/* Primitive Functions */
	public static String FUNC_T = "T";
	public static String FUNC_CAR = "CAR";
	public static String FUNC_CDR = "CDR";
	public static String FUNC_CONS = "CONS";
	public static String FUNC_ATOM = "ATOM";
	public static String FUNC_EQ = "EQ";
	public static String FUNC_NULL = "NULL";
	public static String FUNC_INT = "INT";
	public static String FUNC_PLUS = "PLUS";
	public static String FUNC_MINUS = "MINUS";
	public static String FUNC_TIMES = "TIMES";
	public static String FUNC_QUOTIENT = "QUOTIENT";
	public static String FUNC_REMAINDER = "REMAINDER";
	public static String FUNC_LESS = "LESS";
	public static String FUNC_GREATER = "GREATER";
	public static String FUNC_COND = "COND";
	public static String FUNC_QUOTE = "QUOTE";
	public static String FUNC_DEFUN = "DEFUN";

}

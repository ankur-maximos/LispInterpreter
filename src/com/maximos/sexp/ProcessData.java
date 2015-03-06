/*
 * Creator : maximos 
 * Date : 02/26/2015
 */

package com.maximos.sexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessData {

	private static Pattern pattern;
	public static StringBuilder message = new StringBuilder()
			.append("**Error: ");
	public static boolean errorFlag = false;

	/**
	 * Method to create a tree data structure for a list representation {heart
	 * of this file}
	 * 
	 * @param str
	 *            String containing the contents of a list
	 * @return
	 */
	public static SExp constructTreeList(String str) {
		SExp sexp = new SExp();
		if (str.matches("^(\\()+ ?(\\w)+ ?\\..*\\)$")) {
			if (!checkSExpression(str)) {
				return sexp;
			}
			String temp[] = str.substring(1, str.length() - 1).trim()
					.split("\\.", 2);
			if (temp[0].isEmpty() || temp[1].isEmpty()) {
				errorFlag = true;
				message.append(Constants.EMPTY_DOT_EXP);
				return sexp;
			} else {
				sexp.type = 3;
				sexp.left = constructTreeList(temp[0].trim());
				sexp.right = constructTreeList(temp[1].trim());
			}
		} else if (str.matches("^\\(.*\\)")) {
			sexp.type = 3;
			sexp.right = atomicNode("");
			sexp.left = constructTreeList(str.substring(1, str.length() - 1)
					.trim());
		} else if (str.isEmpty()) {
			sexp.type = 3;
			sexp.right = atomicNode("");
			sexp.left = atomicNode("");
		} else if (!str.contains(" ")) {
			if (testAtomic(str)) {
				sexp = atomicNode(str);
			} else {
				return sexp;
			}
		} else {
			String temp[] = str.split(" ", 2);
			if (testAtomic(temp[0])) {
				sexp.type = 3;
				sexp.left = atomicNode(temp[0]);
			} else {
				return sexp;
			}
			if (temp.length == 2) {
				sexp.right = constructTreeList(temp[1].trim());
			} else {
				sexp.right = atomicNode("");
			}
		}
		return sexp;
	}

	public static SExp buildNode(String left, String right, String type) {
		SExp sexp = new SExp();
		return null;
	}

	/**
	 * This method checks if the non-atomic s-expression is valid or not
	 * 
	 * @param String
	 *            input non-atomic S-Expression of the form (s1.s2)
	 * @return true - if the s-expression is fine,false - if s-expression is
	 *         illegal
	 */
	public static boolean checkSExpression(String str) {
		boolean result = true;
		int loc = str.indexOf(".");
		str = str.substring(loc + 1, str.length() - 1).trim();
		int dotCount = 0;
		if (str.isEmpty()) {
			result = false;
		} else {
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == ' ') {
					continue;
				}
				if (str.charAt(i) == '.') {
					result = false;
					break;
				}
				if (str.charAt(i) == '(') {
					int temp = 1;
					for (; i < str.length(); i++) {
						if (str.charAt(i) == ')') {
							temp--;
						} else if (str.charAt(i) == '(') {
							temp++;
						}
						if (temp == 0) {
							break;
						}
					}
					if (temp != 0) {
						result = false;
					} else {
						if (i < str.length() - 1 && str.charAt(i + 1) == '.') {
							dotCount++;
							i += 2;
						} else if (i < str.length() - 2
								&& str.charAt(i + 2) == '.') {
							dotCount++;
							i += 3;
						}
						if (dotCount > 1) {
							result = false;
							break;
						}
					}
				} else {
					String token = "";
					while (str.charAt(i) != '.' || i < str.length()) {
						token += str.charAt(i);
						i++;
					}
					if (!testAtomic(str.trim())) {
						result = false;
						break;
					} else {
						if (str.charAt(i) == '.')
							dotCount++;
						if (dotCount > 1) {
							result = false;
							break;
						}
						i++;
					}
				}
			}
		}
		if (!result) {
			errorFlag = true;
			message.delete(0, message.length());
			if (dotCount > 1) {
				message.append(Constants.INVALID_SEXP + Constants.EMPTY_DOT_EXP);
			} else
				message.append(Constants.INVALID_SEXP);
		}
		return result;
	}

	/**
	 * Method to check whether its a valid List or not (All Errors are checked)
	 * 
	 * @param str
	 *            input string
	 * @return true - if list is valid,false - if list is not valid
	 */
	public static boolean checkList(String str) {
		boolean result = true;
		str = str.substring(1, str.length() - 1);
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '(') {
				int temp = 1;
				int j = i + 1;
				for (; j < str.length(); j++) {
					if (str.charAt(j) == ')') {
						temp--;
					} else if (str.charAt(j) == '(') {
						temp++;
					}
					if (temp == 0) {
						break;
					}
				}
				if (j != str.length() - 1 && str.charAt(j + 1) != ' ') {
					result = false;
					break;
				}
				if (j != str.length() - 1) {
					j++;
				}
			} else {
				String token = "";
				while (str.charAt(i) != ' ') {
					token += str.charAt(i);
					i++;
				}
				if (testAtomic(token)) {
					i++;
				} else {
					result = false;
					break;
				}
			}
		}
		if (!result) {
			errorFlag = true;
			message.delete(9, message.length());
			message.append(Constants.INVALID_LIST);
		}
		return result;
	}

	/**
	 * This method takes an expression and returns if the string is list or
	 * non-atomic expression or atomic expression
	 * 
	 * @param str
	 *            input non-atomic S-Expression
	 * @return -1 if illegal string,0 if atom,1 if non-atomic s-expression,2 if
	 *         list
	 */
	public static int checkExpression(String str) {
		int result = -1;
		if (!matchParentheses(str))
			return result;
		if (str.charAt(0) == '(') {
			String tempStr = str.substring(1, str.length() - 1);
			if (tempStr.charAt(0) == '(') {
				int temp = 1;
				int i = 1;
				for (; i < str.length(); i++) {
					if (str.charAt(i) == ')') {
						temp--;
					} else if (str.charAt(i) == '(') {
						temp++;
					}
					if (temp == 0) {
						break;
					}
				}
				if (str.charAt(i + 1) == '.' || str.charAt(i + 2) == '.') {
					if (checkList(str)) {
						result = 1;
					} else
						result = -1;
				} else if (str.charAt(i + 1) == ' ') {

				}
			} else if (testAtomic(str)) {

			}
		} else if (testAtomic(str)) {
			result = 0;
		}
		if() {
			
		}
		return result;
	}

	/**
	 * Method to match string with appropriate dot or list expression
	 * 
	 * @param str
	 * @return
	 */
	/*
	 * public static String matchString(String str) { return null; }
	 */

	/**
	 * Method which will take string and convert it into
	 * 
	 * @param str
	 * @return
	 */

	public static SExp buildNodeTree(String str) {
		SExp sexp = new SExp();
		if (!str.matches("^\\( ?(\\w)+ ?\\..*\\)$")
				&& str.matches("^\\(.*\\)$")) {
			str = str.substring(1, str.length() - 1).trim();
		}
		sexp = constructTreeList(str);
		return sexp;
	}

	/**
	 * Method to test integer
	 * 
	 * @param str
	 *            input integer string
	 * @return true - if matches false - if doesn't match
	 */
	public static Boolean testInteger(String str) {
		return str.matches("-?[0-9]+");
	}

	/**
	 * Method to replace all the Whitespaces with single space
	 * 
	 * @param str
	 *            input String
	 * @return output string with single whitespaces
	 */
	public static String replaceWhiteSpace(String str) {
		pattern = Pattern.compile("[\\s]+");
		Matcher matcher = pattern.matcher(str);
		str = matcher.replaceAll(" ");
		if (str.matches("^\\( .*")) { // str.charAt(1) == ' '
			str = "(" + str.substring(2, str.length());
		}
		if (str.matches(".* \\)$")) { // str.charAt(str.length()-2) == ' '
			str = str.substring(0, str.length() - 2) + ")";
		}
		return str;
	}

	/**
	 * Method to test whether the String is a valid atom (Error is appended)
	 * 
	 * @param str
	 *            String to be tested
	 * @return true - if valid false - if not valid
	 */
	public static Boolean testAtomic(String str) {
		Boolean result = true;
		if (str.charAt(0) >= 65 && str.charAt(0) <= 90) {
			result = str.matches("[A-Z][A-Z|0-9]+");
		} else {
			if (!testInteger(str)) {
				result = false;
			}
		}
		if (!result) {
			errorFlag = true;
			message.append(Constants.INVALID_ATOM + " :" + str);
		}
		return result;
	}

	/**
	 * Matching the counts of opening and closing brackets
	 * 
	 * @param str
	 *            String on which method will work
	 * @return true - if match false - if doesn't match
	 */
	public static Boolean matchParentheses(String str) {
		Boolean result = true;
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '(') {
				count++;
			} else if (str.charAt(i) == ')') {
				count--;
			}
		}
		if (count != 0) {
			result = false;
			errorFlag = true;
		}
		if (count < 0) {
			message.append(Constants.NEGATIVE_PARENTHESIS);
		} else if (count > 0) {
			message.append(Constants.POSITIVE_PARENTHESIS);
		}
		return result;
	}

	/**
	 * Method which returns atomic nodes
	 * 
	 * @param str
	 *            input string
	 * @return atomic S-Expression node
	 */
	public static SExp atomicNode(String str) {
		SExp sexp = new SExp();
		if (str.isEmpty()) {
			str = "NIL";
		}
		if (str.charAt(0) >= 65 && str.charAt(0) <= 90) {
			sexp.type = 2;
			sexp.name = str;
		} else {
			sexp.type = 1;
			sexp.val = Integer.parseInt(str);
		}
		return sexp;
	}

	/**
	 * Main Method which processes input string and converts to S-Expression
	 * 
	 * @param str
	 *            input string
	 * @return S-Expression Object
	 */
	public static SExp process(String str) {
		SExp sexp = new SExp();
		if (!matchParentheses(str)) {
			return sexp;
		} else {
			str = replaceWhiteSpace(str.trim());
			sexp = buildNodeTree(str);
		}
		return sexp;
	}
}

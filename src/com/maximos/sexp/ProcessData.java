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
	public static SExp constructTreeList(String str, boolean level) {
		SExp sexp = new SExp();
		str = str.trim();
		int i = checkExpression(str.trim());
		if (i == 1) {
			int loc = findLoc(str);
			str = str.substring(1, str.length() - 1).trim();
			sexp.type = 3;
			String temp = str.substring(0, loc).trim();
			if (checkExpression(temp) == 0) {
				sexp.left = atomicNode(temp);
			} else {
				sexp.left = constructTreeList(temp, false);
			}
			String temp1 = str.substring(loc + 1).trim();
			if (checkExpression(temp1) == 0) {
				sexp.right = atomicNode(temp1);
			} else {
				sexp.right = constructTreeList(temp1, false);
			}
		} else if (i == 0) {
			if (testAtomic(str)) {
				sexp = atomicNode(str);
			} else {
				return sexp;
			}
		} else if (i == 2 && str.charAt(0) == '(') {
			int loc = findLoc(str);
			str = str.substring(1, str.length() - 1).trim();
			if (loc == -1) {
				sexp.type = 3;
				sexp.right = atomicNode("");
				if (checkExpression(str) == 0) {
					sexp.left = atomicNode(str);
				} else {
					sexp.left = constructTreeList(str, false);
				}
			} else {
				sexp.type = 3;
				String temp = str.substring(0, loc).trim();
				if (checkExpression(temp) == 0) {
					sexp.left = atomicNode(temp);
				} else {
					sexp.left = constructTreeList(temp, false);
				}
				String temp1 = "(" + str.substring(loc + 1) + ")";
				sexp.right = constructTreeList(temp1, false);
			}
			if (str.isEmpty()) {
				sexp.type = 3;
				sexp.right = atomicNode("");
				sexp.left = atomicNode("");
				return sexp;
			}
		} else {
			errorFlag = true;
			message.delete(9, message.length());
			message.append(Constants.INVALID_SEXP);
		}
		return sexp;
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
		if (str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')')
			str = str.substring(1, str.length() - 1).trim();
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
					i++;
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
							i += 1;
						} else if (i < str.length() - 2
								&& str.charAt(i + 2) == '.') {
							dotCount++;
							i += 2;
						}
						if (dotCount > 1) {
							result = false;
							break;
						}
					}
				} else {
					String token = "";
					while (i < str.length() && str.charAt(i) != '.') {
						token += str.charAt(i);
						i++;
					}
					if (!testAtomic(token.trim())) {
						result = false;
						break;
					} else {
						if (i < str.length() && str.charAt(i) == '.') {
							dotCount++;
						}
						if (dotCount > 1) {
							result = false;
							break;
						}
					}
				}
			}
		}
		if (!result) {
			errorFlag = true;
			message.delete(9, message.length());
			if (dotCount > 1) {
				message.append(Constants.INVALID_SEXP + Constants.EXTRA_DOTS);
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
		if (str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')')
			str = str.substring(1, str.length() - 1).trim();

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ' ') {
				continue;
			}
			if (str.charAt(i) == '(') {
				int temp = 1;
				i++;
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
					break;
				} else {
					if (i < str.length() - 1 && str.charAt(i + 1) != ' ') {
						result = false;
						break;
					} else
						i++;
				}
			} else {
				String token = "";
				while (i < str.length() && str.charAt(i) != ' ') {
					token += str.charAt(i);
					i++;
				}
				if (!testAtomic(token.trim())) {
					result = false;
					break;
				} else {
					if (i < str.length() && str.charAt(i) != ' ') {
						result = false;
						break;
					}
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
		if (str.isEmpty()) {
			return result;
		} else if (str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')') {
			String tempStr = str.substring(1, str.length() - 1).trim();
			if (tempStr.isEmpty()) {
				result = 0;
				return result;
			}
			if (tempStr.charAt(0) == '(') {
				int temp = 1;
				int i = 1;
				for (; i < tempStr.length(); i++) {
					if (tempStr.charAt(i) == ')') {
						temp--;
					} else if (tempStr.charAt(i) == '(') {
						temp++;
					}
					if (temp == 0) {
						break;
					}
				}
				if (i < tempStr.length() - 1 && tempStr.charAt(i + 1) == '.'
						|| i < tempStr.length() - 2
						&& tempStr.charAt(i + 2) == '.') {
					if (checkSExpression(str)) {
						result = 1;
					} else
						result = -1;
				} else if (i < tempStr.length() - 1
						&& tempStr.charAt(i + 1) == ' ') {
					if (checkList(str)) {
						result = 2;
					} else
						result = -1;
				} else if (i > 0) {
					result = 2;
				}
			} else {
				String token = "";
				int i = 0;
				while (i < tempStr.length() && tempStr.charAt(i) != ' '
						&& tempStr.charAt(i) != '.') {
					token += tempStr.charAt(i);
					i++;
				}
				if (!testAtomic(token)) {
					result = -1;
				} else {
					if (i < tempStr.length() && tempStr.charAt(i) == '.'
							|| i < tempStr.length() - 1
							&& tempStr.charAt(i + 1) == '.') {
						if (checkSExpression(str)) {
							result = 1;
						} else
							result = -1;
					} else if (i < tempStr.length() && tempStr.charAt(i) == ' ') {
						if (checkList(str)) {
							result = 2;
						} else
							result = -1;
					} else if (i > 0) {
						result = 2;
					}
				}
			}
		} else if (testAtomic(str)) {
			result = 0;
		}
		return result;
	}

	/**
	 * This method finds the first location of '.' or ' ' in the string passed
	 * 
	 * @param str
	 *            input string
	 * @return location of the whitespace or '.' -2 illegal,-1 last element in
	 *         S-Expression
	 */
	public static int findLoc(String str) {
		int result = -2;
		if (str.isEmpty()) {
			return result;
		}
		if (str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')') {
			String tempStr = str.substring(1, str.length() - 1).trim();
			if (tempStr.isEmpty()) {
				result = 0;
				return result;
			}
			if (tempStr.charAt(0) == '(') {
				int temp = 1;
				int i = 1;
				for (; i < tempStr.length(); i++) {
					if (tempStr.charAt(i) == ')') {
						temp--;
					} else if (tempStr.charAt(i) == '(') {
						temp++;
					}
					if (temp == 0) {
						break;
					}
				}
				if (i < tempStr.length() - 1 && tempStr.charAt(i + 1) == '.') {
					result = i + 1;
				} else if (i < tempStr.length() - 2
						&& tempStr.charAt(i + 2) == '.') {
					result = i + 2;
				} else if (i < tempStr.length() - 1
						&& tempStr.charAt(i + 1) == ' ') {
					result = i + 1;
				} else if (i > 0) {
					result = -1;
				}
			} else {
				String token = "";
				int i = 0;
				while (i < tempStr.length() && tempStr.charAt(i) != ' '
						&& tempStr.charAt(i) != '.') {
					token += tempStr.charAt(i);
					i++;
				}
				if (testAtomic(token)) {
					if (i < tempStr.length() && tempStr.charAt(i) == '.') {
						result = i;
					} else if (i < tempStr.length() - 1
							&& tempStr.charAt(i + 1) == '.') {
						result = i + 1;
					} else if (i < tempStr.length() - 1
							&& tempStr.charAt(i) == ' ') {
						result = i;
					} else if (i > 0) {
						result = -1;
					}
				}
			}
		}
		return result;
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
		if (str.isEmpty()) {
			result = false;
		} else if (str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')') {
			str = str.substring(1, str.length() - 1).trim();
			if (str.isEmpty()) {
				result = true;
			}
		} else if (str.charAt(0) >= 65 && str.charAt(0) <= 90) {
			result = str.matches("[A-Z][A-Z|0-9]*");
		} else {
			if (!testInteger(str)) {
				result = false;
			}
		}
		if (!result) {
			errorFlag = true;
			message.append(Constants.INVALID_ATOM);
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
			sexp.type = 2;
			sexp.name = "NIL";
		} else if (str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')') {
			str = str.substring(1, str.length() - 1).trim();
			if (str.isEmpty()) {
				sexp.type = 2;
				sexp.name = "NIL";
			}
		} else if (str.charAt(0) >= 65 && str.charAt(0) <= 90) {
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
			sexp = constructTreeList(str, true);
		}
		return sexp;
	}
}

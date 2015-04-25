/*
 * Evaluator 
 * maximos: 19/03/2015
 */

package com.maximos.sexp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Evaluator {

	public static SExp dList = null;
	static String[] arr = { Constants.FUNC_CAR, Constants.FUNC_CDR,
			Constants.FUNC_ATOM, Constants.FUNC_NULL, Constants.FUNC_INT,
			Constants.FUNC_COND, Constants.FUNC_QUOTE, Constants.FUNC_DEFUN,
			Constants.FUNC_CONS, Constants.FUNC_EQ, Constants.FUNC_PLUS,
			Constants.FUNC_MINUS, Constants.FUNC_TIMES,
			Constants.FUNC_QUOTIENT, Constants.FUNC_REMAINDER,
			Constants.FUNC_LESS, Constants.FUNC_GREATER };
	public static List<String> primitivesMethods = new ArrayList<String>(
			Arrays.asList(arr));
	static String[] arr1 = { Constants.EMPTY_ATOM, Constants.TRUE_ATOM };
	public static List<String> primitives = new ArrayList<String>(
			Arrays.asList(arr1));

	/**
	 * Method to return "T/NIL" SExp for corresponding boolean
	 * 
	 * @param b
	 *            input boolean
	 * @return output SExp
	 */
	public static SExp funcT(boolean b) {
		SExp sexp = new SExp();
		sexp.type = 2;
		if (b) {
			sexp.name = Constants.TRUE_ATOM;
		} else {
			sexp.name = Constants.EMPTY_ATOM;
		}
		return sexp;
	}

	/**
	 * Method to return boolean value of "T/NIL" atom
	 * 
	 * @param str
	 *            T/NIL atom
	 * @return
	 * 
	 * @throws LispException
	 */
	public static boolean funcBool(String str) throws LispException {
		if (str.equals(Constants.EMPTY_ATOM)) {
			return false;
		} else if (str.equals(Constants.TRUE_ATOM)) {
			return true;
		} else {
			throw new LispException(Constants.PRIMITIVE_NOT_FOUND);
		}
	}

	/**
	 * CAR primitive function
	 * 
	 * @param sexp
	 *            input lisp expression
	 * @return output lisp expression
	 * @throws LispException
	 */
	public static SExp funcCAR(SExp sexp) throws LispException {
		if (sexp.type != 3) {
			throw new LispException(Constants.LESS_ARGUMENTS);
		}
		return sexp.left;
	}

	/**
	 * CDR primitive function
	 * 
	 * @param sexp
	 *            input lisp expression
	 * @return output cdr of lisp expression
	 * @throws LispException
	 */
	public static SExp funcCDR(SExp sexp) throws LispException {
		if (sexp.type != 3) {
			throw new LispException(Constants.LESS_ARGUMENTS);
		}
		return sexp.right;
	}

	/**
	 * Input cdr primitive function
	 * 
	 * @param sexp
	 *            input Lisp expression
	 * @return output cons lisp expression
	 * @throws LispException
	 */
	public static SExp funcCONS(SExp sexp) throws LispException {
		if (sexp.type != 3) {
			throw new LispException(Constants.LESS_ARGUMENTS);
		}
		if (sexp.right.type == 3 && sexp.right.right.type == 3) {
			throw new LispException(Constants.TOO_MANY_ARGUMENTS);
		}
		SExp sexp1 = new SExp();
		sexp1.type = 3;
		sexp1.left = sexp.left;
		sexp1.right = sexp.right.left;
		return sexp1;
	}

	/**
	 * Cons for two input lisp expressions
	 * 
	 * @param sexp1
	 *            input lisp expression1
	 * @param sexp2
	 *            input Lisp expression2
	 * @return output Lisp expression
	 */
	public static SExp cons(SExp sexp1, SExp sexp2) {
		SExp sexp = new SExp();
		sexp.type = 3;
		sexp.left = sexp1;
		if (sexp2 == null) {
			sexp.right = emptyNode();
		} else {
			sexp.right = sexp2;
		}
		return sexp;
	}

	/**
	 * Method to check whether lisp expression is atom or not
	 * 
	 * @param sexp
	 *            input lisp expression
	 * @return output T/NIL S-exp
	 * @throws LispException
	 */
	public static SExp funcATOM(SExp sexp) throws LispException {
		/*
		 * if (sexp.type == 3) { if (sexp.right.type == 3) { throw new
		 * LispException(Constants.TOO_MANY_ARGUMENTS); } throw new
		 * LispException(Constants.INVALID_ATOM); }
		 */
		if (sexp.type == 1 || sexp.type == 2) {
			return funcT(true);
		}
		return funcT(false);
	}

	/**
	 * Method to check whether 2 lisp expressions are atomic and same
	 * 
	 * @param sexp
	 *            input lisp expression
	 * @return output T/NIL SExp
	 * @throws LispException
	 */
	public static SExp funcEQ(SExp sexp) throws LispException {
		if (sexp.type != 3) {
			throw new LispException(Constants.LESS_ARGUMENTS);
		}
		if (sexp.right.type == 3 && sexp.right.right.type == 3) {
			throw new LispException(Constants.TOO_MANY_ARGUMENTS);
		}
		SExp sexp1 = sexp.left;
		SExp sexp2 = sexp.right.left;
		if (sexp1.type == 3 || sexp2.type == 3) {
			throw new LispException(Constants.INVALID_ATOM);
		}
		if (sexp1.type != sexp2.type) {
			return funcT(false);
		} else if (sexp1.type == 1) {
			if (sexp1.val == sexp2.val)
				return funcT(true);
			else
				return funcT(false);
		} else {
			return funcT(sexp1.name.equals(sexp2.name));
		}
	}

	/**
	 * Method to check whether the input lisp expression is NIL or not
	 * 
	 * @param sexp
	 *            input Lisp Expression
	 * @return output T/NIL SExp
	 * @throws LispException
	 */
	public static SExp funcNULL(SExp sexp) throws LispException {
		if (sexp.type == 3) {
			if (sexp.right.type == 3) {
				throw new LispException(Constants.TOO_MANY_ARGUMENTS);
			}
			throw new LispException(Constants.INVALID_ATOM);
		}
		if (sexp.type == 2 && sexp.name.equals(Constants.EMPTY_ATOM)) {
			return funcT(true);
		} else
			return funcT(false);
	}

	/**
	 * Method to check whether lisp expression is integer or not
	 * 
	 * @param sexp
	 *            input lisp expression
	 * @return output T/NIL
	 * @throws LispException
	 */
	public static SExp funcINT(SExp sexp) throws LispException {
		if (sexp.type == 3) {
			if (sexp.right.type == 3) {
				throw new LispException(Constants.TOO_MANY_ARGUMENTS);
			}
			throw new LispException(Constants.INVALID_ATOM);
		}
		if (sexp.type == 1) {
			return funcT(true);
		} else
			return funcT(false);
	}

	/**
	 * Method to compute sum of two integer Lisp expressions
	 * 
	 * @param sexp
	 *            input lisp expression
	 * @return output lisp expression
	 * @throws LispException
	 */
	public static SExp funcPLUS(SExp sexp) throws LispException {
		if (sexp.type != 3) {
			throw new LispException(Constants.LESS_ARGUMENTS);
		}
		if (sexp.right.type == 3 && sexp.right.right.type == 3) {
			throw new LispException(Constants.TOO_MANY_ARGUMENTS);
		}
		SExp sexpOut = new SExp();
		SExp sexp1 = sexp.left;
		SExp sexp2 = sexp.right.left;
		if (sexp1.type != 1 || sexp2.type != 1) {
			throw new LispException(Constants.ATOMIC_ONLY_INTEGER_ALLOWED);
		}
		sexpOut.type = 1;
		sexpOut.val = sexp1.val + sexp2.val;
		return sexpOut;
	}

	/**
	 * MINUS primitive function
	 * 
	 * @param sexp1
	 *            input lisp expression1
	 * @param sexp2
	 *            input lisp expression2
	 * @return output lisp expression
	 * @throws LispException
	 */
	public static SExp funcMINUS(SExp sexp) throws LispException {
		if (sexp.type != 3) {
			throw new LispException(Constants.LESS_ARGUMENTS);
		}
		if (sexp.right.type == 3 && sexp.right.right.type == 3) {
			throw new LispException(Constants.TOO_MANY_ARGUMENTS);
		}
		SExp sexpOut = new SExp();
		SExp sexp1 = sexp.left;
		SExp sexp2 = sexp.right.left;
		if (sexp1.type != 1 || sexp2.type != 1) {
			throw new LispException(Constants.ATOMIC_ONLY_INTEGER_ALLOWED);
		}
		sexpOut.type = 1;
		sexpOut.val = sexp1.val - sexp2.val;
		return sexpOut;
	}

	/**
	 * TIMES primitive function
	 * 
	 * @param sexp1
	 *            input lisp expression1
	 * @param sexp2
	 *            input lisp expression2
	 * @return output Lisp expression
	 * @throws LispException
	 */
	public static SExp funcTIMES(SExp sexp) throws LispException {
		if (sexp.type != 3) {
			throw new LispException(Constants.LESS_ARGUMENTS);
		}
		if (sexp.right.type == 3 && sexp.right.right.type == 3) {
			throw new LispException(Constants.TOO_MANY_ARGUMENTS);
		}
		SExp sexpOut = new SExp();
		SExp sexp1 = sexp.left;
		SExp sexp2 = sexp.right.left;
		if (sexp1.type != 1 || sexp2.type != 1) {
			throw new LispException(Constants.ATOMIC_ONLY_INTEGER_ALLOWED);
		}
		sexpOut.type = 1;
		sexpOut.val = sexp1.val * sexp2.val;
		return sexpOut;
	}

	/**
	 * QUOTIENT primitive function
	 * 
	 * @param sexp1
	 *            input lisp expression1
	 * @param sexp2
	 *            input lisp expression2
	 * @return output lisp expression
	 * @throws LispException
	 */
	public static SExp funcQUOTIENT(SExp sexp) throws LispException {
		if (sexp.type != 3) {
			throw new LispException(Constants.LESS_ARGUMENTS);
		}
		if (sexp.right.type == 3 && sexp.right.right.type == 3) {
			throw new LispException(Constants.TOO_MANY_ARGUMENTS);
		}
		SExp sexpOut = new SExp();
		SExp sexp1 = sexp.left;
		SExp sexp2 = sexp.right.left;
		if (sexp1.type != 1 || sexp2.type != 1) {
			throw new LispException(Constants.ATOMIC_ONLY_INTEGER_ALLOWED);
		}
		sexpOut.type = 1;
		sexpOut.val = sexp1.val / sexp2.val;
		return sexpOut;
	}

	/**
	 * REMAINDER primitive function
	 * 
	 * @param sexp1
	 *            input Lisp expression1
	 * @param sexp2
	 *            input Lisp expression2
	 * @return output lisp expression
	 * @throws LispException
	 */
	public static SExp funcREMAINDER(SExp sexp) throws LispException {
		if (sexp.type != 3) {
			throw new LispException(Constants.LESS_ARGUMENTS);
		}
		if (sexp.right.type == 3 && sexp.right.right.type == 3) {
			throw new LispException(Constants.TOO_MANY_ARGUMENTS);
		}
		SExp sexpOut = new SExp();
		SExp sexp1 = sexp.left;
		SExp sexp2 = sexp.right.left;
		if (sexp1.type != 1 || sexp2.type != 1) {
			throw new LispException(Constants.ATOMIC_ONLY_INTEGER_ALLOWED);
		}
		sexpOut.type = 1;
		sexpOut.val = sexp1.val % sexp2.val;
		return sexpOut;
	}

	/**
	 * LESS primitive function
	 * 
	 * @param sexp1
	 *            input lisp expression1
	 * @param sexp2
	 *            input lisp expression2
	 * @return output lisp expression
	 * @throws LispException
	 */
	public static SExp funcLESS(SExp sexp) throws LispException {
		if (sexp.type != 3) {
			throw new LispException(Constants.LESS_ARGUMENTS);
		}
		if (sexp.right.type == 3 && sexp.right.right.type == 3) {
			throw new LispException(Constants.TOO_MANY_ARGUMENTS);
		}
		SExp sexp1 = sexp.left;
		SExp sexp2 = sexp.right.left;
		if (sexp1.type != 1 || sexp2.type != 1) {
			throw new LispException(Constants.ATOMIC_ONLY_INTEGER_ALLOWED);
		}
		return funcT(sexp1.val < sexp2.val);
	}

	/**
	 * Greater primitive Function
	 * 
	 * @param sexp1
	 *            input lisp expression1
	 * @param sexp2
	 *            input lisp expression2
	 * @return output lisp expression
	 * @throws LispException
	 */
	public static SExp funcGREATER(SExp sexp) throws LispException {
		if (sexp.type != 3) {
			throw new LispException(Constants.LESS_ARGUMENTS);
		}
		if (sexp.right.type == 3 && sexp.right.right.type == 3) {
			throw new LispException(Constants.TOO_MANY_ARGUMENTS);
		}
		SExp sexp1 = sexp.left;
		SExp sexp2 = sexp.right.left;
		if (sexp1.type != 1 || sexp2.type != 1) {
			throw new LispException(Constants.ATOMIC_ONLY_INTEGER_ALLOWED);
		}
		return funcT(sexp1.val > sexp2.val);
	}

	/**
	 * QUOTE Function
	 * 
	 * @param sexp1
	 *            input Lisp Expression
	 * @return returns the inner S-Expression
	 * @throws LispException
	 */
	public static SExp funcQUOTE(SExp sexp1) throws LispException {
		return sexp1.left;
	}

	/**
	 * evcon method to evaluate conditional Lisp Expression
	 * 
	 * @param sexp1
	 *            input S-Expression
	 * @return evaluted Lisp Expression
	 * @throws LispException
	 */
	public static SExp funcCOND(SExp sexp1, SExp alist) throws LispException {
		if (sexp1 == null || sexp1.type == 2) {
			throw new LispException(Constants.EMPTY_CONDITION);
		} else {
			SExp temp = eval(sexp1.left.left, alist);
			if (temp.type == 2 && funcBool(temp.name)) {
				return eval(sexp1.left.right.left, alist);
			} else {
				return funcCOND(sexp1.right, alist);
			}
		}
	}

	/**
	 * This method returns NIL node
	 * 
	 * @return NIL S-Expression
	 */
	public static SExp emptyNode() {
		SExp sexp = new SExp();
		sexp.name = Constants.EMPTY_ATOM;
		sexp.type = 2;
		return sexp;
	}

	/**
	 * Takes a String function name and checks whether there is a duplicate
	 * value present in the dList
	 * 
	 * @param func
	 * @return true/false
	 */
	public static boolean searchFunction(String func) {
		boolean flag = false;
		SExp temp = dList;
		if (func != null) {
			while (temp.type == 3) {
				if (temp.left.left.name.equals(func)) {
					flag = true;
					break;
				}
				temp = temp.right;
			}
		}
		return flag;
	}

	/**
	 * Method to add custom functions into d-list
	 * 
	 * @param sexp1
	 *            input S-Expression
	 * @return success result
	 * @throws LispException
	 */
	public static String funcDEFUN(SExp sexp1) throws LispException {
		if (dList == null) {
			dList = new SExp();
			dList.type = 3;
			dList.right = emptyNode();
			dList.left = sexp1;
		} else {
			if (!searchFunction(sexp1.left.name)) {
				SExp newNode = new SExp();
				newNode.left = sexp1;
				newNode.type = 3;
				newNode.right = emptyNode();
				reachBottomNode(dList).right = newNode;
			} else {
				throw new LispException(Constants.METHOD_ALREADY_DEFINED);
			}
		}
		return sexp1.left.name;
	}

	/**
	 * This method applies function to the parameters and returns the evaluation
	 * 
	 * @param func
	 *            the left atomic node
	 * @param x
	 *            the right list of parameters
	 * @param alist
	 *            current alist
	 * @return final Lisp Expression after evaluation
	 * @throws LispException
	 */
	public static SExp apply(SExp func, SExp x, SExp alist)
			throws LispException {
		if (x == null) {
			throw new LispException(Constants.NO_ARGUMENTS);
		}
		if (func.type == 1 || func.type == 2) {
			String funcCall = "func" + func.name;
			if (primitivesMethods.contains(func.name)) {
				try {
					if (x.right.type == 2) {
						x = x.left;
					}
					if (x == null) {
						throw new LispException(Constants.NO_ARGUMENTS);
					}
					return (SExp) Evaluator.class.getDeclaredMethod(funcCall,
							SExp.class).invoke(null, x);
				} catch (Exception e) {
					throw new LispException(e.getCause().getMessage());
				}
			} else {
				SExp temp;
				if (alist == null) {
					alist = new SExp();
					alist.type = 3;
					alist.left = emptyNode();
					alist.right = emptyNode();
					temp = alist;
				} else {
					temp = reachBottomNode(alist);
				}
				addPairs(funcCAR(getVal(func, dList, false)), x, temp);
				SExp temp1 = eval(funcCAR(funcCDR(getVal(func, dList, false))),
						alist);
				temp.right = emptyNode();
				return temp1;
			}
		} else {
			throw new LispException(Constants.INVALID_FUNCTION_NAME);
		}
	}

	/**
	 * Method to return a new node of (p1.val)
	 * 
	 * @param pList
	 *            function parameter list
	 * @param x
	 *            function parameter values
	 * @return new node having (p1.val)
	 * @throws LispException
	 */
	public static SExp getParamMappingNode(SExp pList, SExp x)
			throws LispException {
		SExp sexp = new SExp();
		SExp sexp1 = new SExp();
		sexp1.type = 3;
		sexp1.right = x.left;
		sexp1.left = pList.left;
		sexp.right = emptyNode();
		sexp.left = sexp1;
		return sexp1;
	}

	/**
	 * To update the aList to include parameter mapping
	 * 
	 * @param pList
	 *            function Parameter List
	 * @param x
	 *            New parameter values
	 * @param aList
	 *            Final aList after updating
	 * @throws LispException
	 */
	public static void addPairs(SExp pList, SExp x, SExp alist)
			throws LispException {
		if (pList.type == 3 && x.type == 3) {
			if (alist.left.type != 3) {
				alist.left = getParamMappingNode(pList, x);
			} else {
				alist.right = new SExp();
				alist = alist.right;
				alist.type = 3;
				alist.left = getParamMappingNode(pList, x);
				alist.right = emptyNode();
			}
			addPairs(pList.right, x.right, alist);
		} else if (pList.type == 3 || x.type == 3) {
			throw new LispException(Constants.PARAMETER_COUNT_NOT_MATCHING);
		}
	}

	/**
	 * To reach the last node in list
	 * 
	 * @param aList
	 *            input S-Expression
	 * @return last S-expression
	 */
	public static SExp reachBottomNode(SExp aList) {
		if (aList == null) {
			return null;
		}
		SExp temp = aList;
		while (temp.right.type == 3) {
			temp = temp.right;
		}
		return temp;
	}

	/**
	 * Return the corresponding right node for a left node if found
	 * 
	 * @param param
	 *            Atomic S-Expression
	 * @param alist
	 *            current alist/dlist
	 * @return right node for left match
	 * @throws LispException
	 */
	public static SExp getVal(SExp param, SExp alist) throws LispException {
		if (alist == null || alist.type != 3 || param.type == 3) {
			throw new LispException(Constants.PARAMETER_NOT_FOUND);
		} else if ((alist.left.left.type == 2 && alist.left.left.name
				.equals(param.name))
				|| (alist.left.left.type == 1 && alist.left.left.val == param.val)) {
			return alist.left.right;
		} else {
			return getVal(param, alist.right);
		}
	}

	/**
	 * Return the corresponding right node for a left node if found
	 * 
	 * @param param
	 *            Atomic S-Expression
	 * @param alist
	 *            current alist/dlist
	 * @param flag
	 *            true - implement list as a stack,false - implement as a list
	 * @return
	 * @throws LispException
	 */
	public static SExp getVal(SExp param, SExp alist, boolean flag)
			throws LispException {
		if (alist == null) {
			throw new LispException(Constants.PARAMETER_NOT_FOUND);
		}
		SExp latest = null;
		SExp temp1 = alist;
		while (temp1.type == 3) {
			if ((temp1.left.left.type == 2 && temp1.left.left.name
					.equals(param.name))
					|| (temp1.left.left.type == 1 && temp1.left.left.val == param.val)) {
				latest = temp1.left;
				if (!flag) {
					break;
				}
			}
			temp1 = temp1.right;
		}
		if (latest == null) {
			throw new LispException(Constants.PARAMETER_NOT_FOUND);
		} else {
			return latest.right;
		}
	}

	public static SExp evlis(SExp list, SExp alist) throws LispException {
		if (list == null) {
			return list;
		}
		if (list.type == 2 && list.name.equals(Constants.EMPTY_ATOM)) {
			return null;
		} else {
			return cons(eval(funcCAR(list), alist), evlis(funcCDR(list), alist));
		}
	}

	/**
	 * Main Evaluator Method
	 * 
	 * @param sexp
	 *            Input Lisp-Expression
	 * @param aList
	 *            Current aList
	 * @return Final evaluation of Lisp Expression
	 * @throws LispException
	 */
	public static SExp eval(SExp sexp, SExp aList) throws LispException {
		if (sexp.type != 3) {
			if (sexp.type == 1) {
				return sexp;
			} else if (sexp.type == 2 && primitives.contains(sexp.name)) {
				return sexp;
			} else {
				return getVal(sexp, aList, true);
			}
		} else {
			if (sexp.left.type == 2
					&& sexp.left.name.equals(Constants.FUNC_DEFUN)) {
				System.out.println(funcDEFUN(sexp.right));
				return null;
			} else if (sexp.left.type == 2
					&& sexp.left.name.equals(Constants.FUNC_QUOTE)) {
				return funcQUOTE(sexp.right);
			} else if (sexp.left.type == 2
					&& sexp.left.name.equals(Constants.FUNC_COND)) {
				return funcCOND(sexp.right, aList);
			} else if (sexp.left.type == 2 || sexp.left.type == 1) {
				return apply(sexp.left, evlis(sexp.right, aList), aList);
			} else {
				throw new LispException(Constants.INVALID_FUNCTION);
			}
		}
	}
}

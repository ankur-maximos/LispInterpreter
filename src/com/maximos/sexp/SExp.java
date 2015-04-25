/*
 * Creator : maximos 
 * Date : 02/26/2015
 */

package com.maximos.sexp;

public class SExp {
	int type; /* 1: integer atom; 2: symbolic atom; 3: non-atom */
	int val;
	String name;
	SExp left;
	SExp right;

	public SExp() {
		type = -1;
	}

	public SExp(int type, int val) {
		this.type = type;
		this.val = val;
	}

	public SExp(int type, String name) {
		this.type = type;
		this.name = name;
	}

	public SExp(int type, SExp left, SExp right) {
		this.left = left;
		this.right = right;
	}

	public void processInput(String input) {
		System.out.println("String to be processed :" + input);
	}

	public void printSExp(SExp sexp) {
		if (sexp.type == 1) {
			System.out.print(sexp.val);
		} else if (sexp.type == 2) {
			System.out.print(sexp.name);
		} else if (sexp.type == 3) {
			System.out.print("(");
			printSExp(sexp.left);
			System.out.print(" . ");
			printSExp(sexp.right);
			System.out.print(")");
		}
	}

	/**
	 * Checks Whether the S-Expression can be denoted in list form
	 * 
	 * @param sexp
	 *            input SExpression
	 * @return true - if the sexp can be denoted in list form false - if the
	 *         sexp cannot be denoted in list form
	 */
	public boolean checkList(SExp sexp) {
		boolean flag = false;
		SExp temp = sexp;
		while (temp.right.type == 3) {
			temp = temp.right;
		}
		if (temp.right.type == 2
				&& temp.right.name.equals(Constants.EMPTY_ATOM)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * Output the S-expression in list notation
	 * 
	 * @param sexp
	 *            input S-Expression
	 * @param list
	 *            Boolean to override printSExp and used in placing of opening
	 *            and closing parenthesis
	 */
	public void printSExp(SExp sexp, boolean list) {
		if (sexp.type == 1) {
			System.out.print(sexp.val);
		} else if (sexp.type == 2) {
			System.out.print(sexp.name);
		} else if (sexp.type == 3) {
			if (list)
				System.out.print(Constants.OPENING_BRACES);
			if (sexp.left.type != 3 && sexp.right.type != 3) {
				printSExp(sexp.left, false);
				if ((sexp.right.type == 2 && !sexp.right.name.equals("NIL"))
						|| (sexp.right.type == 1)) {
					System.out.print(" . ");
					printSExp(sexp.right, false);
				}
			} else if (sexp.left.type != 3 && (sexp.right.type == 3)) {
				printSExp(sexp.left, false);
				if (list && !checkList(sexp.right)) {
					System.out.print(" . ");
					printSExp(sexp.right, true);
				} else {
					System.out.print(" ");
					printSExp(sexp.right, false);
				}
			} else if (sexp.left.type == 3 && sexp.right.type != 3) {
				printSExp(sexp.left, true);
				if (!(sexp.right.type == 2 && sexp.right.name
						.equals(Constants.EMPTY_ATOM))) {
					System.out.print(" . ");
					printSExp(sexp.right, false);
				}
			} else {
				if (list && !checkList(sexp.right)) {
					printSExp(sexp.left, true);
					System.out.print(" . ");
					printSExp(sexp.right, true);
				} else {
					printSExp(sexp.left, true);
					System.out.print(" ");
					printSExp(sexp.right, false);
				}
			}
			if (list)
				System.out.print(Constants.CLOSING_BRACES);
		}
	}
}

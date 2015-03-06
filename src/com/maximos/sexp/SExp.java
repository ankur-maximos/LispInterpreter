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
}

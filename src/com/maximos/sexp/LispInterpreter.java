/*
 * Creator : maximos 
 * Date : 02/26/2015
 */

package com.maximos.sexp;

import java.util.Scanner;

public class LispInterpreter {

	public static void processingQueue(StringBuilder input) {
		if (input.length() == 0) {
			System.out.println(Constants.EMPTY_INPUT_STRING);
		} else {
			SExp sexp = ProcessData.process(input.toString().trim());
			if (ProcessData.errorFlag) {
				System.out.println(ProcessData.message.toString());
				ProcessData.message.delete(9, ProcessData.message.length());
				ProcessData.errorFlag = false;
			} else {
				System.out.print(Constants.OUTPUT_GREATER_THAN);
				sexp.printSExp(sexp);
				System.out.println();
			}
			input.delete(0, input.length());
		}
	}

	public static void main(String args[]) {
		String str = "(2 . ((3 . 4) . ((5 . 6) . NIL)))";
		System.out.println(str.matches("^\\( ?(\\w)+ ?\\..*\\)$"));
		Scanner sc = new Scanner(System.in);
		StringBuilder input = new StringBuilder();
		while (sc.hasNextLine()) {
			String temp = sc.nextLine();
			if (temp.equals("$")) {
				processingQueue(input);
			} else if (temp.equals("$$")) {
				processingQueue(input);
				System.out.println("Exiting interpreter");
				break;
			} else {
				input.append(" " + temp.trim());
			}
		}
		sc.close();
	}
}

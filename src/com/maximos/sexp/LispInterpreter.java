/*
 * Creator : maximos 
 * Date : 02/26/2015
 */

package com.maximos.sexp;

import java.util.Scanner;

public class LispInterpreter {

	/**
	 * Method to forward input given to ProcessData Class
	 * 
	 * @param input
	 *            input stringBuilder (to save garbage string objects)
	 */
	public static void processingQueue(StringBuilder input) {
		String input1 = input.toString();
		input1 = input1.trim();
		if (input1.length() == 0) {
			System.out.println(Constants.EMPTY_INPUT_STRING);
		} else {
			SExp sexp = ProcessData.process(input1);
			if (ProcessData.errorFlag) {
				System.out.println(ProcessData.message.toString());
				ProcessData.message.delete(9, ProcessData.message.length());
				ProcessData.errorFlag = false;
			} else {
				System.out.print(Constants.OUTPUT_GREATER_THAN);
				sexp.printSExp(sexp, true);
				System.out.println();
				try {
					sexp = Evaluator.eval(sexp, null);
					if (sexp != null) {
						sexp.printSExp(sexp, true);
						System.out.println();
					}
				} catch (LispException e) {
					System.out.println("***ERROR :" + e.getMessage());
				}
			}
			input.delete(0, input.length());
		}
	}

	/**
	 * Main Method
	 * 
	 * @param args
	 *            Arguments for input from console
	 */
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		StringBuilder input = new StringBuilder();
		while (sc.hasNextLine()) {
			String temp = sc.nextLine().trim();
			if (temp.equals("$")) {
				processingQueue(input);
			} else if (temp.equals("$$")) {
				if (input.toString().trim().length() > 0) {
					processingQueue(input);
				}
				System.out.println("Bye!");
				break;
			} else {
				input.append(" " + temp.trim());
			}
		}
		sc.close();
	}
}

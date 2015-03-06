package com.maximos.sexp;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Test {
	
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		Pattern pattern = Pattern.compile("(.*)((\n)|(\r\n))(abc)");
		Pattern pattern1 = Pattern.compile("$$");
		while (sc.hasNext(pattern)) {
			System.out.println("print");
			String temp = sc.next(pattern);
			System.out.println("print: \\s|" + temp);
		}
		sc.close();
	}

}

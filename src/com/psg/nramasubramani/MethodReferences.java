package com.psg.nramasubramani;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author rn5
 *
 */
public class MethodReferences {
	public static void main(String args[]) {
		MethodReferences methodReferences = new MethodReferences();
		methodReferences.instanceMethodReference();
	}

	private void instanceMethodReference() {
		Digit digit = new Digit();
		List<Long> numberList = Arrays.asList(123L, 234L, 134L, 221L);
		
		System.out.println("********** Digit Sum **********");

		System.out.println("Usual way");
		for (Long number : numberList) {
			digit.sumDigit(number);
		}

		// Lambda way
		System.out.println("\nLambda way");
		numberList.forEach(number -> digit.sumDigit(number));// foreach accepts consumer.

		// Method References Way
		System.out.println("\nMethod References way");
		numberList.forEach(digit::sumDigit);
		
		System.out.println("\n\n********** Fibonacci **********");
		
		//Usual Way
		List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6);
		System.out.println("Usual way");
		for (Integer number : integerList) {
			Digit.printFibonacci(number);
		}
		
		//Lambda Way
		System.out.println("\nLambda way");
		integerList.forEach(number -> Digit.printFibonacci(number));
		
		// Method References Way
		System.out.println("\nMethod References way");
		integerList.forEach(Digit::printFibonacci);
		
		List<Integer> list = integerList.stream().map(Digit::getFibonacci).collect(Collectors.toList());
		System.out.println("\nStream Map function");
		list.forEach(System.out::print);
		
	}
}

class Digit {
	public void sumDigit(long value) {
		long sum = 0;
		if (value < 10)
			sum = value;
		else {
			while (value > 0) {
				long rem = value % 10;
				value = value / 10;
				sum += rem;
			}
			System.out.print(sum + "\t");
		}
	}
	
	public static void printFibonacci(int n) {
		System.out.print(getFibonacci(n) + "\t");
	}
	
	public static int getFibonacci(int n) {
		if (n <= 1) {
			return 0;
		} else if (n == 2) {
			return 1;
		} else {
			return getFibonacci(n - 1) + getFibonacci(n - 2);
		}
	}
}
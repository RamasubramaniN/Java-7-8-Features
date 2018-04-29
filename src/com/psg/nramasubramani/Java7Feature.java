package com.psg.nramasubramani;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Java7Feature {

	public static void main(String[] args) {
		
		Java7Feature java7Feature = new Java7Feature();
		
		java7Feature.stringSwitchExample();
		java7Feature.diamondOperator();
		java7Feature.numericLiteral();
		
	}

	private void stringSwitchExample() {

		//Strings are allowed in switch statements in java7.
		String number = "one";

		try {
			switch (number) {
			case "five":
				System.out.println(5);
			case "zero":
				System.out.println(0);
				throw new ZeroException();
			case "one":
				System.out.println(1);
				throw new OneException();
			case "two":
				System.out.println(2);
				throw new TwoException();
			}
		} catch (OneException | TwoException e) { //Useful when we have same action for more than once exception.
			System.out.println("Positive int exception");
		} catch (ZeroException e) {
			System.out.println("Zero int exception");
		}
	}
	
	private void diamondOperator(){
		//Older way of doing this : 
		//Map<Integer, Integer> squareMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> squareMap = new HashMap<>(); //Redundant types in the right hand side is not required.
		squareMap.put(1, 1);
		System.out.println(squareMap.get(1));
	}
	
	private void numericLiteral(){
		
		//Having _ to reduce errors while defining integers.
		int thousand = 1_000;
		int million = 1_000_000;
		
		System.out.println("Thousand : " + thousand);
		System.out.println("Million : " + million);
		
		//binary literal
		int binary = 0b1000;
		System.out.println("Numeric value of Binary 1000 : "+ binary);
		float  pif = 3.14_15_92_65f;
	}
	
	private void finallyExample() throws FileNotFoundException {
		File file = new File("xyz.txt");
		//try(resources_to_be_cleant){ // your code }. No need of cleaning resources in the finally block.
		try (FileReader fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			bufferedReader.readLine();
			//No need of following things
			//fileReader.close();
			//bufferedReader.close();
		} catch (IOException e) {

		}
	}

}

class OneException extends Exception {
	
}

class TwoException extends Exception {
	
}

class ZeroException extends Exception {
	
}

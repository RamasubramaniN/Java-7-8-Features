/**
 * 
 */
package com.psg.nramasubramani;

/**
 * @author nramasubramani
 *
 */

interface Printer {
	//Java 8 enables us to add non-abstract method implementations to interfaces by utilizing 
	//the default keyword. This feature is also known as Extension Methods.
	//Generally we use Abstract class to provide default implementation but if you do it, you can extend only the abstract class
	//Alternatively, If you use default interface, you can extend one more class.
	void print();
	
	default void addBookMark(){
		//Add default bookmark for all printers
		System.out.println("Default bookmark");
	}
}

public class DefaultInterface {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("**********Simple Printer**********");
		Printer printer = new SimplePrinter();
		printer.print();
		printer.addBookMark(); //Refers default implementation
		
		System.out.println();
		System.out.println("**********Advanced Printer**********");

		Printer advancedPrinter = new AdvancedPrinter();
		advancedPrinter.print();
		advancedPrinter.addBookMark();//Refers customized implementation
		
		System.out.println();
		System.out.println("**********Anonymous Printer**********");
		
		Printer anonymousPrinter = new Printer() {
			
			@Override
			public void print() {
				System.out.println("Anonymous printer - Printing...");
				
			}
		};
		
		anonymousPrinter.print();
		anonymousPrinter.addBookMark();//Refers default implementation
	}

}

class SimplePrinter implements Printer{

	@Override
	public void print() {
		System.out.println("Simple Printer - Printing...");
	}
	
}

class AdvancedPrinter implements Printer{

	@Override
	public void print() {
		System.out.println("Advanced Printer - Printing...");
	}
	
	@Override
	public void addBookMark() {
		// TODO Auto-generated method stub
		//Printer.super.addBookMark();
		System.out.println("Advanced bookmark");
	}
	
}


package com.psg.nramasubramani;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author nramasubramani
 * Streams are Monads, thus playing a big part in bringing functional programming to Java. In functional programming, 
 * a monad is a structure that represents computations defined as sequences of steps. A type with a monad structure defines 
 * what it means to chain operations, or nest functions of that type together.
 *
 */
class Student {
	private String name;
	private String department;
	private Integer age;
	
	public Student(String name, String department, Integer age){
		this.name = name;
		this.department = department;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public String getDepartment() {
		return department;
	}

	public Integer getAge() {
		return age;
	}
	
}
/**
 * https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html
 * Streams Vs Collections
 * 1) No storage. A stream is not a data structure that stores elements; instead, it conveys elements from a source such as a data structure, an array, a generator function, or an I/O channel, through a pipeline of computational operations. 
 * 2) Functional in nature. An operation on a stream produces a result, but does not modify its source. For example, filtering a Stream obtained from a collection produces a new Stream without the filtered elements, rather than removing elements from the source collection.
 * 3) Laziness-seeking. Many stream operations, such as filtering, mapping, or duplicate removal, can be implemented lazily, exposing opportunities for optimization. For example, "find the first String with three consecutive vowels" need not examine all the input strings. Stream operations are divided into intermediate (Stream-producing) operations and terminal (value- or side-effect-producing) operations. Intermediate operations are always lazy.
 * 4) Possibly unbounded. While collections have a finite size, streams need not. Short-circuiting operations such as limit(n) or findFirst() can allow computations on infinite streams to complete in finite time.
 * 5) Consumable. The elements of a stream are only visited once during the life of a stream. Like an Iterator, a new stream must be generated to revisit the same elements of the source.
 *
 */

public class StreamExample {
	
	private List<Student> studentList;

	public static void main(String[] args) {
		StreamExample streamExample = new StreamExample();
		streamExample.testStream();
	}
	
	public StreamExample(){
		
		studentList = new ArrayList<Student>();
		
		studentList.add(new Student("Stu1", "ece", 18));
		studentList.add(new Student("Stu2", "ece", 17));
		studentList.add(new Student("Stu3", "ece", 21));
		studentList.add(new Student("Stu4", "cse", 21));
		studentList.add(new Student("Stu5", "cse", 18));
		studentList.add(new Student("Stu1", "it", 20));
		studentList.add(new Student("Stu1", "it", 21));
		
	}

	private void testStream() {
		example1();
		example2();
		example3();
		example4();
		example5();
		example6();
		filterCollectExample();
		filterMatchExample();
		reduceExample();
	}
	
	private void reduceExample() {
		
		//Many new functional interfaces are being defined in the Java 8, among the most popular, 
		//those found in the new java.util.function package.
		
		/*
		 * interface myInterface<T> {
		 * 		T get();
		 * }
		 */
		BinaryOperator<Integer> binaryOperator = new BinaryOperator<Integer>() {
			@Override
			public Integer apply(Integer int1, Integer int2) {
				return int1 + int2;
			}
		};
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i <= 10; i++) {
			list.add(i);
		}
		
		Optional<Integer> sum = list.stream().reduce(binaryOperator); //A container object which may or may not contain a non-null value. 
		//If a value is present, isPresent() will return true and get() will return the value. 
	
		System.out.println("Sum : " + sum.get());
		
		Integer sumWithIdentity = list.stream().reduce(10, binaryOperator);
		System.out.println("Sum with identity : " + sumWithIdentity);
	}
	
	private void filterMatchExample() {
		//Various matching operations can be used to check whether a certain predicate matches the stream. 
		//All of those operations are terminal and return a boolean result.
		
		System.out.println();
		Predicate<Student> anyMechPredicate = student -> {
			return "mech".equals(student.getDepartment());
		};
		
		//We generally use break statements to check if there is at least one element in the list matching the criteria.
		boolean atLeastOneMechStudent = studentList.stream().anyMatch(anyMechPredicate);
		System.out.println("At least one mech student in the list? "+ atLeastOneMechStudent);
		
		Predicate<Student> noEEEStudentPredicate = student -> {
			return "eee".equals(student.getDepartment());
		};

		//Return true if none matches the predicate in the list
		boolean noEEEStudent = studentList.stream().noneMatch(noEEEStudentPredicate);
		System.out.println("No EEE student? "+ noEEEStudent);
		
		Predicate<Student> isAllECEStudentPredicate = student -> {
			return "ece".equals(student.getDepartment());
		};
		
		//Return true if all matches the predicate in the list.
		boolean isAllECEStudent = studentList.stream().allMatch(isAllECEStudentPredicate);
		System.out.println("Is everyone ECE? "+ isAllECEStudent);
		
		Predicate<Student> countCSElementPredicate = student -> {
			return "cse".equals(student.getDepartment());
		};
		Long countCSElement = studentList.stream().filter(countCSElementPredicate).count();
		System.out.println("Total CS Students : "+ countCSElement);
		
		
		//Find First element in the stream matching the list
		Predicate<Student> firstECEStudentPredicate = student -> {
			return "ece".equals(student.getDepartment());
		};
		Optional<Student> student = studentList.stream().filter(firstECEStudentPredicate).findFirst();
		System.out.println("First ECE Student : "+ student.get().getName());
	}

	private void filterCollectExample() {
		//Filter accepts predicate to filter data.
		//collect() method used to recieve elements from a sream and store them in a collection and metioned in parameter funcion.
		System.out.println();
		System.out.println("**********Only CS Student**********");
		Predicate<Student> onlyCSPredicate = student ->
		{
			return "cse".equals(student.getDepartment());
		};
		List<Student> onlyCSStudent = studentList.stream().filter(onlyCSPredicate).collect(Collectors.toList());
		for(Student student : onlyCSStudent){
			System.out.print(student.getName() + "\t");
		}
		
		List<Integer> list = new ArrayList<Integer>();
		list.add(1); list.add(3); list.add(3); list.add(5); list.add(7); list.add(5); list.add(7);list.add(9);
		System.out.println("Total elements : " + list.size());
		
		List<Integer> distinctList = list.stream().distinct().collect(Collectors.toList());
		System.out.println("Distinct elements : " + distinctList.size());
	}

	private void example1() {
		Stream<Integer> stream = Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9); //varargs
		stream.forEach(p -> {
			System.out.print(p + "\t");
		});
	}

	//Converting array to stream
	private void example2(){
		System.out.println();
		Stream<Integer> stream = Stream.of(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
		stream.forEach(p -> System.out.print(p + "\t"));
	}
	
	//Converting list to stream
	private void example3() {
		System.out.println();
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < 10; i++) {
			list.add(i);
		}
		Stream<Integer> stream = list.stream();
		stream.forEach(p -> System.out.print(p + "\t"));
	}
	
	private void example4(){
		System.out.println();
		IntStream stream = "12345_abcdefg".chars();
        stream.forEach(p -> System.out.print(p +"\t"));
	}
	
	//Convert Stream to List using stream.collect(Collectors.toList()). Filter accepts a predicate to filter all elements of the stream. 
	//This operation is intermediate which enables us to call another stream operation (e.g. forEach) on the result.
	private void example5() {

		System.out.println();
		
		Predicate<Integer> evenPredicate = i -> {
			return i % 2 == 0;
		};
		
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i <= 10; i++) {
			list.add(i);
		}
		
		Stream<Integer> stream = list.stream();
		List<Integer> evenNumberList = stream.filter(evenPredicate).collect(Collectors.toList()); //Filter accepts predicate
		
		for(int i : evenNumberList){
			System.out.print(i + "\t");
		}
	}
	
	private void example6() {
		
		System.out.println();
		
		Predicate<Integer> muliplesOfThreePredicate = i -> {
			return i % 3 == 0;
		};
		
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i <= 10; i++) {
			list.add(i);
		}
		
		Stream<Integer> stream = list.stream();
		Integer[] mulipleOf3Array = stream.filter(muliplesOfThreePredicate).toArray(Integer[]::new);
		
		for (int i : mulipleOf3Array) {
			System.out.print(i + "\t");
		}
	}
}

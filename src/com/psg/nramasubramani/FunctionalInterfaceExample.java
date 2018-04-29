package com.psg.nramasubramani;

import java.util.ArrayList;
import java.util.List;

/**
 * @author nramasubramani
 *
 */


@FunctionalInterface
interface IntOperation {
	int someOperation(int x, int y);
}

public class FunctionalInterfaceExample {

	//functional interface is an interface with a single abstract method. 
	//The Java API has many one-method interfaces such as Runnable, Callable, Comparator, ActionListener and others. 
	//They can be implemented and instantiated using anonymous class syntax. 
	public static void main(String[] args) {
		
		Office office = new Office();
		List<Employee> employeeList = office.getEmployeeList();
		
		//Older way
		Runnable task = new Runnable() {
			
			@Override
			public void run() {
				System.out.println("My Task");
			}
		};
		Thread myThread = new Thread(task);
		myThread.start();
		
		//Runnable newTask = () -> {System.out.println("My new Task");};
		Thread myNewThread = new Thread(() -> {System.out.println("My new Task");});
		myNewThread.start();
		

		FunctionalInterfaceExample functionalInterfaceExample = new FunctionalInterfaceExample();
		functionalInterfaceExample.testLambdaExpressions(employeeList);
		
		IntOperation intOperation = (a, b) -> {
			return a + b;
		};
		
		System.out.println("Sum 7, 9 : " + intOperation.someOperation(7,  9));
	
	}

	//Lambda uses functional interfaces
	private void testLambdaExpressions(List<Employee> employeeList) {

		//Different anonymous implementations for validator using lambda expressions
		
		//Implementation1
		Validator opsOnly = emp -> "Ops".equals(emp.getDepartment());
		List<Employee> opsOnlyEmployeeList = getFilteredEmployees(opsOnly, employeeList);
		printEmployees("Ops Only", opsOnlyEmployeeList);
	
		//Implementation2
		Validator devOnly = employee -> "Dev".equals(employee.getDepartment());
		List<Employee> devOnlyEmployeeList = getFilteredEmployees(devOnly, employeeList);
		printEmployees("DEV Only", devOnlyEmployeeList);
		
		//Implementation3
		Validator devAbove15KAnd7B = employee -> {
			return "Dev".equals(employee.getDepartment()) && employee.getSalary() >= 15000
					&& "7B".equals(employee.getDesignation());
		};
		List<Employee> devAbove15KAnd7BEmployeeList = getFilteredEmployees(devAbove15KAnd7B, employeeList);
		printEmployees("DEV above 15K and 7B", devAbove15KAnd7BEmployeeList);

		//Implementation4
		Validator startsWithEmp = employee -> {
			return employee.getName().startsWith("Emp");
		};
		List<Employee> startsWithEmpEmployeeList = getFilteredEmployees(startsWithEmp, employeeList);
		printEmployees("Name starts with Emp", startsWithEmpEmployeeList);
	}

	//This accepts different versions of anonymous validator implementations and filters employees
	private List<Employee> getFilteredEmployees(Validator validator, List<Employee> employeeList){
		
		List<Employee> filteredEmployees = new ArrayList<Employee>();
		
		for (Employee employee : employeeList) {
			if (validator.validate(employee)) {
				filteredEmployees.add(employee);
			}
		}

		return filteredEmployees;
	}

	private void printEmployees(String header, List<Employee> employeeList) {
		
		System.out.println("**********" + header + "**********");
		
		employeeList.forEach(System.out::println);
		
		System.out.println("\n");
	}

}

//Functional interface annotation is not mandatory but to prevent from adding more than 1 interface. 
//You will get following compilation error if you try to add. Invalid '@FunctionalInterface' annotation; Validator is not a functional interface
@FunctionalInterface
interface Validator {
	boolean validate(Employee employee);
}

//The following is a valid functional interface because we have default implementation for a method and our interface still has only one abstract method.
@FunctionalInterface
interface AdvancedValidator {
	
	boolean validate(Employee employee);

	default boolean isSelected() {
		return true;
	}
}

class Office {

	private List<Employee> employeeList;

	public Office() {

		employeeList = new ArrayList<Employee>();

		employeeList.add(new Employee("Emp1", "7A", "Dev", 10000L));
		employeeList.add(new Employee("Emp2", "7A", "Dev", 12000L));
		employeeList.add(new Employee("Emp3", "7A", "Ops", 11000L));
		employeeList.add(new Employee("Emp4", "7A", "Ops", 10000L));
		employeeList.add(new Employee("Emp5", "7B", "Dev", 20000L));
		employeeList.add(new Employee("ABC", "7B", "Dev", 20000L));
		employeeList.add(new Employee("XYZ", "7B", "Ops", 14000L));
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}
}

class Employee {
	
	private final String name;
	private final String designation;
	private final String department;
	private final Long salary;

	public Employee(String name, String designation, String department, Long salary) {
		this.name = name;
		this.designation = designation;
		this.department = department;
		this.salary = salary;
	}

	public String getName() {
		return name;
	}

	public String getDesignation() {
		return designation;
	}

	public String getDepartment() {
		return department;
	}

	public Long getSalary() {
		return salary;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
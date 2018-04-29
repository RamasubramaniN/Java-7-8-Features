package com.psg.nramasubramani;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * @author rn5
 *
 */
public class Comparators {

	public static void main(String[] args) {
		Comparators comparators = new Comparators();
		comparators.beforeJava8();
		comparators.afterJava8();
	}

	private void beforeJava8() {
		
		System.out.println("----------- Before Java 8 -----------");

		List<Customer> customerCollection = getCustomerCollection();

		// sort by age
		Collections.sort(customerCollection, new Comparator<Customer>() {
			@Override
			public int compare(Customer c1, Customer c2) {
				if (c1.getAge() >= c2.getAge())
					return 1; //c1.getAge.compareTo(c2.getAge);
				return -1;
			}
		});
		printList(customerCollection, "Sort By Age");

		// Sort by name
		customerCollection = getCustomerCollection();
		Collections.sort(customerCollection, new Comparator<Customer>() {
			@Override
			public int compare(Customer c1, Customer c2) {
				if (c2.getName() == null)
					return 1;
				else if (c1.getName() == null)
					return -1;
				return c1.getName().compareTo(c2.getName());
			}
		});
		printList(customerCollection, "Sort By Name Nulls Last");

		customerCollection = getCustomerCollection();
		Collections.sort(customerCollection, new Comparator<Customer>() {
			@Override
			public int compare(Customer c1, Customer c2) {
				if(c1.getAge() != c2.getAge()) {
					return c1.getAccountId().compareTo(c2.getAccountId());
				}
				return ((Integer) (c1.getAge())).compareTo(c2.getAge());
			}
		});
		printList(customerCollection, "Sort By Age and then account id");

	}
	
	private void afterJava8() {
		
		System.out.println("----------- After Java 8 -----------");
		List<Customer> customerCollection = getCustomerCollection();
		
		// sort by age
		customerCollection.sort(Comparator.comparing(Customer::getAge)); //Function/Supplier/Consumer
		printList(customerCollection, "Sort By Age");
		
		//sort by name nulls last
		customerCollection = getCustomerCollection();
		customerCollection.sort(Comparator.comparing(Customer::getName, 
				Comparator.nullsLast(Comparator.naturalOrder())));
		printList(customerCollection, "Sort By Name Nulls Last");
		
		//sort by age then sort by Name nulls first
		customerCollection = getCustomerCollection();
		customerCollection.sort(Comparator.comparing(Customer::getAge)
				.thenComparing(Customer::getName, Comparator.nullsFirst(Comparator.naturalOrder())));
		printList(customerCollection, "Sort By Age then By name Nulls first");
		
		customerCollection = getCustomerCollection();
		customerCollection.sort(Comparator.comparing(Customer::getName, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)));
		printList(customerCollection, "Sort By Name nulls last case insensitive");

	}

	private List<Customer> getCustomerCollection() {

		List<Customer> customerList = new ArrayList<>();

		Customer customer1 = new Customer(Long.valueOf(1L), "Tom", 23);
		Customer customer2 = new Customer(Long.valueOf(2L), "bob", 21);
		Customer customer3 = new Customer(Long.valueOf(3L), "Alice", 27);
		Customer customer4 = new Customer(Long.valueOf(4L), null, 21);
		Customer customer5 = new Customer(Long.valueOf(5L), "Bob", 21);
		
		customerList.add(customer1);
		customerList.add(customer2);
		customerList.add(customer3);
		customerList.add(customer4);
		customerList.add(customer5);

		return customerList;
	}

	private void printList(List<Customer> customerList, String heading) {
		System.out.println("**********   " + heading + "   **********");
		customerList.forEach(System.out::println);
		System.out.println();
	}

}

class Customer {

	private String name;
	private int age;
	private Long accountId;

	public Customer(Long accountId, String name, int age) {
		this.name = name;
		this.age = age;
		this.accountId = accountId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Name : " + (name!=null? name : "Null") + ", Age : " + age + ", AccountId : "+ accountId;
	}

}
package com.arjen.graphql.entities;

import java.util.ArrayList;
import java.util.List;

public class DummyDataBuilder {

	private static List<Person> persons = new ArrayList<>();
	private static List<Address> addresses = new ArrayList<>();

	public static List<Person> getPersons() {
		return persons;
	}

	public static List<Address> getAddresses() {
		return addresses;
	}

	public static void setupSamplePersonData() {
		for (Integer i = 0; i < 100; i++) {
			Person p = new Person(i);
			persons.add(p);
			setupSampleAddressData(p);
		}
	}

	public static void setupSampleAddressData(Person person) {
		for (Integer i = 0; i < 10; i++) {
			person.addAddress(new Address(i, person));
		}
		addresses.addAll(person.getAddresses());
	}
}

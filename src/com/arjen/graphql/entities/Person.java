package com.arjen.graphql.entities;

import java.util.ArrayList;
import java.util.List;

public class Person {
	private Integer id;
	private String firstName;
	private String lastName;
	private String field1;
	private String field2;
	private String field3;
	private List<Address> addresses = new ArrayList<>();
	private String secret;

	public Person(Integer id) {
		super();
		this.id = id;
		this.firstName = "voornaam " + id;
		this.lastName = "achternaam " + id;
		this.secret = "secret voor persoon: " + id;
		this.field1 = "veld1 persoon: " + id;
		this.field2 = "veld2 voor persoon: " + id;
		this.field3 = "veld3 voor persoon: " + id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public void addAddress(Address address) {
		this.addresses.add(address);
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField3() {
		return field3;
	}

	public void setField3(String field3) {
		this.field3 = field3;
	}

}

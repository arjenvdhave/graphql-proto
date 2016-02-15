package com.arjen.graphql.entities;

public class Address {

	private Integer id;
	private String street;
	private String city;
	private String postalCode;
	private Person person;

	public Address(Integer id, Person person) {
		this.id = id;
		this.street = "[" + person.getId() + "]Straat " + id;
		this.city = "[" + person.getId() + "]City " + id;
		this.postalCode = "[" + person.getId() + "]PostalCode " + id;
		this.person = person;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}

package com.arjen.graphql.entities;

public class Person {
	private Integer id;
	private String firstName;
	private String lastName;
	private String secret;

	public Person(Integer id) {
		super();
		this.firstName = "voornaam " + id;
		this.lastName = "achternaam " + id;
		this.secret = "secret voor persoon: " + id;
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

}

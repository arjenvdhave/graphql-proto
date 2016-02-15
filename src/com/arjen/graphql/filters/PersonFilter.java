package com.arjen.graphql.filters;

import java.util.ArrayList;
import java.util.List;

import com.arjen.graphql.entities.Person;

public class PersonFilter extends AbstractFilter {

	@GraphQLFilterField(description = "Database id van de persoon")
	private Integer id;
	@GraphQLFilterField(description = "Voornaam van de persoon")
	private String firstName;
	@GraphQLFilterField(description = "Achternaam van de persoon")
	private String lastName;

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

	public List<Person> filterList(List<Person> persons) {
		List<Person> ret = new ArrayList<Person>();
		for (Person p : persons) {
			Boolean allFiltersMatch = true;
			if (getFirstName() != null
					&& !p.getFirstName().equals(getFirstName()))
				allFiltersMatch = false;

			if (getLastName() != null && !p.getLastName().equals(getLastName()))
				allFiltersMatch = false;

			if (getId() != null && !p.getId().equals(getId()))
				allFiltersMatch = false;

			if (allFiltersMatch)
				ret.add(p);

			if (getFirst() != null && ret.size() == getFirst())
				return ret;
		}
		return ret;
	}

}

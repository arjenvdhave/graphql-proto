package com.arjen.graphql.meta;

import graphql.Scalars;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLList;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.arjen.graphql.entities.Address;
import com.arjen.graphql.entities.DummyDataBuilder;
import com.arjen.graphql.entities.Person;
import com.arjen.graphql.filters.PersonFilter;

@Component
public class SchemaBuilder {

	private GraphQLSchema schema;
	private List<Person> persons = new ArrayList<>();
	private List<Address> addresses = new ArrayList<>();

	public SchemaBuilder() {
		DummyDataBuilder.setupSamplePersonData();
		persons = DummyDataBuilder.getPersons();
		addresses = DummyDataBuilder.getAddresses();

		GraphQLObjectType queryType = GraphQLObjectType.newObject()
				.name("query").field(getPersonsGraphQLFieldDefinition())
				.field(getAddressGraphQLFieldDefinition()).build();

		schema = GraphQLSchema.newSchema().query(queryType).build();
	}

	public GraphQLSchema getSchema() {
		return schema;
	}

	private GraphQLObjectType getAddressObjectType() {
		return GraphQLObjectType
				.newObject()
				.name("addressType")
				.field(GraphQLFieldDefinition.newFieldDefinition()
						.type(Scalars.GraphQLString).name("id").build())
				.field(GraphQLFieldDefinition.newFieldDefinition()
						.type(Scalars.GraphQLString).name("street")
						.description("Straatnaam").build())
				.field(GraphQLFieldDefinition.newFieldDefinition()
						.type(Scalars.GraphQLString).name("city")
						.description("Plaatsnaam").build()).build();
	}

	private GraphQLObjectType getPersonObjectType() {
		return GraphQLObjectType
				.newObject()
				.name("personType")
				.field(GraphQLFieldDefinition.newFieldDefinition()
						.type(Scalars.GraphQLString).name("id").build())
				.field(GraphQLFieldDefinition.newFieldDefinition()
						.type(Scalars.GraphQLString).name("firstName")
						.description("Voornaam").build())
				.field(GraphQLFieldDefinition.newFieldDefinition()
						.type(Scalars.GraphQLString).name("lastName")
						.description("Achternaam").build())
				.field(GraphQLFieldDefinition.newFieldDefinition()
						.type(Scalars.GraphQLString).name("field1")
						.description("Random veld1").build())
				.field(GraphQLFieldDefinition.newFieldDefinition()
						.type(Scalars.GraphQLString).name("field2")
						.description("Random veld2").build())
				.field(GraphQLFieldDefinition.newFieldDefinition()
						.type(Scalars.GraphQLString).name("field3")
						.description("Random veld3").build())
				.field(GraphQLFieldDefinition
						.newFieldDefinition()
						.type(new GraphQLList(getAddressObjectType()))
						.name("personAddresses")
						.dataFetcher(new DataFetcher() {

							@Override
							public Object get(
									DataFetchingEnvironment environment) {
								return ((Person) environment.getSource())
										.getAddresses();
							}
						}).description("Lijst van adressen voor deze persoon")
						.build())
				.field(GraphQLFieldDefinition.newFieldDefinition()
						.deprecate("Deprecated - Gebruik personAddresses")
						.type(new GraphQLList(getAddressObjectType()))
						.name("addresses").build()).build();
	}

	private GraphQLFieldDefinition getPersonsGraphQLFieldDefinition() {
		return GraphQLFieldDefinition
				.newFieldDefinition()
				.name("persons")
				.description("Lijst van personen")
				.type(new GraphQLList(getPersonObjectType()))
				.dataFetcher(new DataFetcher() {
					@Override
					public Object get(DataFetchingEnvironment environment) {
						if (environment.getArguments().containsKey("filter")) {
							Optional<PersonFilter> filter = FilterBuilder
									.createFilter(PersonFilter.class,
											environment.getArgument("filter"));
							return filter.isPresent() ? filter.get()
									.filterList(persons) : persons;
						}
						return persons;
					}
				})
				.argument(
						new GraphQLArgument("filter", FilterBuilder.build(
								"personFilterType", PersonFilter.class)))
				.build();
	}

	private GraphQLFieldDefinition getAddressGraphQLFieldDefinition() {
		return GraphQLFieldDefinition.newFieldDefinition().name("addresses")
				.type(new GraphQLList(getAddressObjectType()))
				.description("Lijst van alle adressen")
				.dataFetcher(new DataFetcher() {
					@Override
					public Object get(DataFetchingEnvironment environment) {
						return addresses;
					}
				}).build();
	}
}

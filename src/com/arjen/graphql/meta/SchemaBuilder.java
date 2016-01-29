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
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.arjen.graphql.entities.Person;

@Component
public class SchemaBuilder {

	private GraphQLSchema schema;
	private List<Person> persons = new ArrayList<>();

	public SchemaBuilder() {
		setupSampleData();
		GraphQLObjectType personType = GraphQLObjectType
				.newObject()
				.name("personType")
				.field(GraphQLFieldDefinition.newFieldDefinition()
						.type(Scalars.GraphQLString).name("firstName").build())
				.field(GraphQLFieldDefinition.newFieldDefinition()
						.type(Scalars.GraphQLString).name("lastName").build())
				.build();

		GraphQLObjectType addressType = GraphQLObjectType
				.newObject()
				.name("addressType")
				.field(GraphQLFieldDefinition.newFieldDefinition()
						.type(Scalars.GraphQLString).name("street").build())
				.field(GraphQLFieldDefinition.newFieldDefinition()
						.type(Scalars.GraphQLString).name("city").build())
				.build();

		GraphQLObjectType queryType = GraphQLObjectType
				.newObject()
				.name("query")
				.field(GraphQLFieldDefinition
						.newFieldDefinition()
						.name("persons")
						.type(new GraphQLList(personType))
						.dataFetcher(new DataFetcher() {
							@Override
							public Object get(
									DataFetchingEnvironment environment) {

								return persons.subList(
										0,
										getArgument("first",
												environment.getArguments(),
												Integer.class).orElse(
												persons.size()));
							}
						})
						.argument(
								GraphQLArgument.newArgument().name("first")
										.type(Scalars.GraphQLInt).build())
						.build())
				.field(GraphQLFieldDefinition.newFieldDefinition()
						.name("address").type(addressType).build())
				.field(GraphQLFieldDefinition.newFieldDefinition()
						.type(Scalars.GraphQLString).name("street")
						.staticValue("Straat").build()).build();

		schema = GraphQLSchema.newSchema().query(queryType).build();
	}

	@SuppressWarnings("unchecked")
	private <T> Optional<T> getArgument(String argumentName,
			Map<String, Object> arguments, Class<T> expextedClass) {
		Object arg = arguments.get(argumentName);
		if (arg == null || ((T) arg) == null)
			return Optional.empty();
		return Optional.of((T) arg);
	}

	private void setupSampleData() {
		for (Integer i = 0; i < 100; i++) {
			persons.add(new Person(i));
		}
	}

	public GraphQLSchema getSchema() {
		return schema;
	}

}

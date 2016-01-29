package com.arjen.graphql.rest;

import graphql.GraphQL;

import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.arjen.graphql.meta.SchemaBuilder;

@RestController
public class GraphQlService {

	@Autowired
	private SchemaBuilder schemaBuilder;

	@SuppressWarnings("unchecked")
	@RequestMapping("/graphql")
	public @ResponseBody LinkedHashMap<String, Object> graphQl(
			@RequestParam String request) {
		LinkedHashMap<String, Object> result = (LinkedHashMap<String, Object>) new GraphQL(
				schemaBuilder.getSchema()).execute(request).getData();
		return result;
	}
}

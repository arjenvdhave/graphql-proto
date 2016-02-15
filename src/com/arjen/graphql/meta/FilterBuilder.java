package com.arjen.graphql.meta;

import graphql.Scalars;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLInputType;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.arjen.graphql.filters.GraphQLFilterField;

public class FilterBuilder {

	public static GraphQLInputObjectType build(String name,
			Class<?> filterObject) {

		List<GraphQLInputObjectField> fields = new ArrayList<>();

		for (Field field : getAllFields(new ArrayList<Field>(), filterObject)) {
			GraphQLInputObjectField graphQLField = createInputField(field);
			if (graphQLField != null)
				fields.add(graphQLField);
		}
		GraphQLInputObjectType filter = GraphQLInputObjectType.newInputObject()
				.fields(fields).name(name).build();
		return filter;
	}

	public static <T> Optional<T> createFilter(Class<T> filterClass,
			Map<String, Object> arguments) {
		T ret;
		BeanInfo beanInfo;
		try {
			ret = filterClass.newInstance();
			beanInfo = Introspector.getBeanInfo(ret.getClass());
		} catch (Exception e) {
			return Optional.empty();
		}

		for (String field : arguments.keySet()) {
			PropertyDescriptor pd = getPropertyDescriptorForProperty(field,
					beanInfo);
			if (pd == null)
				continue;
			try {
				pd.getWriteMethod().invoke(ret, arguments.get(field));
			} catch (Exception e) {
			}
		}

		return Optional.of(ret);
	}

	private static GraphQLInputObjectField createInputField(Field field) {
		GraphQLInputType type = null;
		if (field.getType().equals(String.class)) {
			type = Scalars.GraphQLString;
		} else if (field.getType().equals(Integer.class)) {
			type = Scalars.GraphQLInt;
		} else if (field.getType().equals(Long.class)) {
			type = Scalars.GraphQLLong;
		} else if (field.getType().equals(Boolean.class)) {
			type = Scalars.GraphQLBoolean;
		} else if (field.getType().equals(Float.class)) {
			type = Scalars.GraphQLFloat;
		}

		if (type == null)
			return null;

		GraphQLFilterField fieldAnnotation = field
				.getAnnotation(GraphQLFilterField.class);
		String name = field.getName();
		String desc = "";
		if (fieldAnnotation != null) {
			name = fieldAnnotation.name() == "" ? fieldAnnotation.name() : name;
			desc = fieldAnnotation.description();
		}

		return GraphQLInputObjectField.newInputObjectField().type(type)
				.name(name).description(desc).build();
	}

	private static List<Field> getAllFields(List<Field> fields, Class<?> type) {
		fields.addAll(Arrays.asList(type.getDeclaredFields()));

		if (type.getSuperclass() != null) {
			fields = getAllFields(fields, type.getSuperclass());
		}

		return fields;
	}

	private static PropertyDescriptor getPropertyDescriptorForProperty(
			String property, BeanInfo beanInfo) {
		for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
			if (pd.getName().equals(property))
				return pd;
		}
		return null;
	}
}

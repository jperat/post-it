package com.jperat.postit.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.apache.commons.lang3.StringUtils;

@Converter
public class LongArrayToStringConverter implements AttributeConverter<List<Long>, String> {

	public String convertToDatabaseColumn(List<Long> attribute) {
		return attribute == null ? null : StringUtils.join(attribute, ",");
	}

	public List<Long> convertToEntityAttribute(String dbData) {
		if (StringUtils.isBlank(dbData)) {
			return new ArrayList<Long>();
		}
		try (Stream<String> stream = Arrays.stream(dbData.split(","))) {
			return stream.map(Long::parseLong).collect(Collectors.toList());
		}
	}

}

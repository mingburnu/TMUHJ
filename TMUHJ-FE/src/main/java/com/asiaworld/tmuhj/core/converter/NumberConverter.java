package com.asiaworld.tmuhj.core.converter;

import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

@Component
public class NumberConverter extends RootConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		try {
			if (NumberUtils.isNumber(values[0])) {
				if (toClass.equals(Long.class)) {
					return Long.parseLong(values[0]);
				}

				if (toClass.equals(Integer.class)) {
					return Integer.parseInt(values[0]);
				}
			}
		} catch (NumberFormatException e) {
			return null;
		}

		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {

		if (o != null) {
			return o.toString();
		} else {
			return "";
		}
	}

}

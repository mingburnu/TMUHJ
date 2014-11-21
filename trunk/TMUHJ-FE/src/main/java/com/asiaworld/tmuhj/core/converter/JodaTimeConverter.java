package com.asiaworld.tmuhj.core.converter;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Value;

/**
 * Joda Time Converter
 * @author David Hsu
 * @version 2014/3/17
 */
public class JodaTimeConverter extends StrutsTypeConverter {

	protected final transient Logger log = Logger.getLogger(getClass());
	
	@Value("#{systemConfigurer['dateTime.pattern']}")
	private String dateTimePattern;
	
	@Value("#{systemConfigurer['date.pattern']}")
	private String datePattern;
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		LocalDateTime dateTime = null;
		
		if(StringUtils.isNotEmpty(values[0])) {
			try {
				log.debug("input date: " + values[0]);
				dateTime = LocalDateTime.parse(values[0], DateTimeFormat.forPattern(dateTimePattern));
			} catch(IllegalArgumentException e) {
				log.debug("IllegalArgumentException for dateTimePattern, change use datePattern");
				try {
					dateTime = LocalDateTime.parse(values[0], DateTimeFormat.forPattern(datePattern));
				} catch (Exception e2) {
					log.error(ExceptionUtils.getStackTrace(e));
				}
			} catch (Exception e) {
				log.error(ExceptionUtils.getStackTrace(e));
			}
		}
		
		return dateTime;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		
		if(o instanceof LocalDateTime) {
			LocalDateTime dateTime = (LocalDateTime)o;			
			String formattedTime = dateTime.toString(DateTimeFormat.forPattern(dateTimePattern));
			return formattedTime;
		}
		System.out.println(" (String) o =="+ (String) o);
		return (String) o;
	}

}

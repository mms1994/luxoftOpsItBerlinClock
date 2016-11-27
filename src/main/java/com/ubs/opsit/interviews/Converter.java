package com.ubs.opsit.interviews;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Michał Spirała
 */
public class Converter implements TimeConverter
{
	private static final String YELLOW = "Y";
	private static final String RED = "R";
	private static final String EMPTY = "O";
	private static final String NEW_LINE = "\r\n";
	private static final Pattern TIME = Pattern.compile("(\\S\\S):(\\S\\S):(\\S\\S)");


	@Override
	public String convertTime(String aTime)
	{
		Matcher matcher = TIME.matcher(aTime);
		if (matcher.matches())
		{
			String result = "";
//			prepare data to create result
			Integer hours = Integer.parseInt(matcher.group(1));
			Integer minutes = Integer.parseInt(matcher.group(2));
			Integer seconds = Integer.parseInt(matcher.group(3));

//          setting 'seconds' line
			result = setSeconds(result, seconds);

//          setting 'hours' line
			result = setHours(result, hours);

//          setting 'minutes' line
			result = setMinutes(result, minutes);

			return result;
		} else
//			if invalid format
			return null;
	}

	private String setMinutes(String result, Integer minutes)
	{
		return String.format("%s%s%s%s", result, prepareLine(minutes / 5), NEW_LINE,
							 prepareLine(minutes % 5, YELLOW, 4));
	}

	private String setHours(String result, Integer hours)
	{
		return String
				.format("%s%s%s%s%s", result, prepareLine(hours / 5, RED, 4), NEW_LINE, prepareLine(hours % 5, RED, 4),
						NEW_LINE);
	}

	private String setSeconds(String result, Integer seconds)
	{
		result += seconds % 2 == 0 ? YELLOW : EMPTY;
		result += NEW_LINE;
		return result;
	}

	private String prepareLine(Integer counter, String symbol, Integer maxLength)
	{
		String result = "";
		Integer index;
		for (index = 0; index < counter; index++)
			result += symbol;
		for (index = 0; index < maxLength - counter; index++)
			result += EMPTY;
		return result;
	}

	private String prepareLine(Integer counter)
	{
		char[] temporary = prepareLine(counter, YELLOW, 11).toCharArray();
		for (Integer index = 2; index < temporary.length; index += 3)
			if (temporary[index] == YELLOW.charAt(0))
				temporary[index] = RED.charAt(0);
		return new String(temporary);
	}
}

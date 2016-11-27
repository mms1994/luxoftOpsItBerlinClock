package com.ubs.opsit.interviews;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Michał Spirała
 */
public class Converter implements TimeConverter
{
	//	constants
	private static final String YELLOW = "Y";
	private static final String RED = "R";
	private static final String EMPTY = "O";
	private static final String NEW_LINE = System.getProperty("line.separator");
	private static final Pattern TIME = Pattern.compile("(\\S\\S):(\\S\\S):(\\S\\S)");

	//  function from interface
	@Override
	public String convertTime(String aTime)
	{
		Matcher matcher = TIME.matcher(aTime);
		if (matcher.matches())
		{
			StringBuilder stringBuilder = new StringBuilder();
			// prepare data to create result
			Integer hours = Integer.parseInt(matcher.group(1));
			Integer minutes = Integer.parseInt(matcher.group(2));
			Integer seconds = Integer.parseInt(matcher.group(3));

			// in case incorrect format
			if (hours > 24 || minutes > 59 || seconds > 59 || hours < 0 || minutes < 0 || seconds < 0)
				return null;

			// append 'seconds' line
			stringBuilder.append(prepareSeconds(seconds));

			// append 'hours' lines
			stringBuilder.append(prepareHours(hours));

			// append 'minutes' lines
			stringBuilder.append(prepareMinutes(minutes));

			return stringBuilder.toString();
		} else
			// in case incorrect format
			return null;
	}

//	auxiliary functions

	//	preparing hours section
	private String prepareHours(Integer hours)
	{
		// building first line of hours
		final String firstLine = prepareLine(hours / 5, RED, 4);
		// building second line of hours
		final String secondLine = prepareLine(hours % 5, RED, 4);
		// return result
		return String.format("%s%s%s%s", firstLine, NEW_LINE, secondLine, NEW_LINE);
	}

	//	preparing minutes section
	private String prepareMinutes(Integer minutes)
	{
		// building first line of minutes
		final String firstLine = prepareLine(minutes / 5);
		// building second line of minutes
		final String secondLine = prepareLine(minutes % 5, YELLOW, 4);
		// return result
		return String.format("%s%s%s", firstLine, NEW_LINE, secondLine);
	}

	//	preparing seconds section
	private String prepareSeconds(Integer seconds)
	{
		// building line of seconds
		final String line = seconds % 2 == 0 ? YELLOW : EMPTY;
		// return result
		return String.format("%s%s", line, NEW_LINE);
	}

	// preparing single line (minutes+hours)
	private String prepareLine(Integer numberOfSymbols, String symbol, Integer lengthOfLine)
	{
		// preparing variables
		StringBuilder stringBuilder = new StringBuilder();
		Integer index = 0;
		// inserting symbols
		for (; index < numberOfSymbols; index++)
			stringBuilder.append(symbol);
		// completion line to achieve correct length of line
		for (; index < lengthOfLine; index++)
			stringBuilder.append(EMPTY);
		// return ready line
		return stringBuilder.toString();
	}

	//	preparing line with minutes (where there are red and yellow lamps)
	private String prepareLine(Integer counter)
	{
		//	getting and splitting line with yellow lamps (or 'empty' lamps)
		String[] array = prepareLine(counter, YELLOW, 11).split("");
		// if 2,5 and 8 index of string contains yellow, replace with red (first, second and third quarter)
		for (Integer index = 2; index < 11; index += 3)
			if (array[index].equals(YELLOW))
				array[index] = RED;
		// joining array to single string and return
		return String.join("", array);
	}
}

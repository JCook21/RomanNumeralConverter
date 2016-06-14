package ca.jeremycook.romannumeralconverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static ca.jeremycook.romannumeralconverter.ErrorMessages.COMMAND_ERROR;
import static ca.jeremycook.romannumeralconverter.ErrorMessages.INVALID_COMMAND;
import static ca.jeremycook.romannumeralconverter.ErrorMessages.NUMBER_ERROR_MESSAGE;
import static ca.jeremycook.romannumeralconverter.ErrorMessages.NUMBER_PARSE_ERROR;
import static ca.jeremycook.romannumeralconverter.ErrorMessages.ROMAN_NUMERAL_ERROR;

public class Main
{

	private static final Predicate<String> romanNumeralValidator = Pattern.compile("^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$", Pattern.CASE_INSENSITIVE)
			.asPredicate();

	private static final int MAX_ROMAN_NUMBER = 3999;

	private static final String EXIT_COMMAND = "exit";

	private static final Collection<Integer> commands = new ArrayList<>();

	static
	{
		commands.add(1);
		commands.add(2);
	}

	public static void main(String[] args) throws IOException
	{
		System.out.println("Welcome to the Arabic to Roman converter!");
		System.out.println("Type 'exit' to exit.");

		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		while (true)
		{
			System.out.println("Enter 1 to convert a number to a Roman Numeral.");
			System.out.println("Enter 2 to convert a Roman Numeral to a number.");
			System.out.print("> ");
			String input = bufferedReader.readLine();
			if (input.toLowerCase().equals(EXIT_COMMAND))
			{
				break;
			}
			String result = "";
			try
			{
				result = Stream.of(input)
						.mapToInt(Integer::parseInt)
						.filter(commands::contains)
						.mapToObj(val -> convertValue(val, bufferedReader))
						.findFirst()
						.orElse(String.format(INVALID_COMMAND.toString(), input));
			}
			catch (NumberFormatException e)
			{
				result = String.format(COMMAND_ERROR.toString(), input);
			}
			System.out.println(result);
		}
		bufferedReader.close();
		System.out.println("Exiting.");
	}

	private static String convertValue(Integer input, BufferedReader bufferedReader)
	{
		if (input == 1)
		{
			return convertToRoman(bufferedReader);
		}
		return convertToArabic(bufferedReader);
	}

	private static String convertToRoman(BufferedReader bufferedReader)
	{
		System.out.println("Enter an Arabic number below to see it converted to Roman numerals.");
		System.out.print("> ");
		try
		{
			return bufferedReader.lines()
					.limit(1)
					.mapToInt(Integer::parseInt)
					.filter(val -> val > 0 && val <= MAX_ROMAN_NUMBER)
					.mapToObj(RomanNumeralConverter::convertToRoman)
					.findFirst()
					.orElse(String.format(NUMBER_ERROR_MESSAGE.toString(), MAX_ROMAN_NUMBER));
		}
		catch (NumberFormatException e)
		{
			return NUMBER_PARSE_ERROR.toString();
		}
	}

	private static String convertToArabic(BufferedReader bufferedReader)
	{
		System.out.println("Enter a Roman Numeral below to see it converted to an Arabic number.");
		System.out.print("> ");
		return bufferedReader.lines()
				.limit(1)
				.filter(romanNumeralValidator)
				.map(String::toUpperCase)
				.mapToInt(RomanNumeralConverter::convertToArabic)
				.mapToObj(Integer::toString)
				.findFirst()
				.orElse(ROMAN_NUMERAL_ERROR.toString());
	}
}

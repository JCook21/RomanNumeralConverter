package ca.jeremycook.romannumeralconverter;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Class to convert to and from Roman Numerals and Arabic numbers.
 */
final class RomanNumeralConverter
{
	/**
	 * Map of boundary values used to convert between Roman and Arabic numbers.
	 */
	private final NavigableMap<Integer, String> romanNumeralBoundaries;

	private final Map<String, Integer> romanCharacterLookup;

	RomanNumeralConverter()
	{
		NavigableMap<Integer, String> boundaries = new TreeMap<>();
		boundaries.put(1000, "M");
		boundaries.put(900, "CM");
		boundaries.put(500, "D");
		boundaries.put(400, "CD");
		boundaries.put(100, "C");
		boundaries.put(90, "XC");
		boundaries.put(50, "L");
		boundaries.put(40, "XL");
		boundaries.put(10, "X");
		boundaries.put(9, "IX");
		boundaries.put(5, "V");
		boundaries.put(4, "IV");
		boundaries.put(1, "I");
		romanNumeralBoundaries = Collections.unmodifiableNavigableMap(boundaries);

		romanCharacterLookup = romanNumeralBoundaries.entrySet()
				.stream()
				.collect(Collectors.toUnmodifiableMap(Entry::getValue, Entry::getKey));
	}

	/**
	 * Converts an arabic number to roman numerals.
	 * The algorithm works by using TreeMap::floorEntry to find the key closest to the number.
	 * The number is decremented by the key found each time.
	 *
	 * @param arabicNumber number to convert
	 *
	 * @return number represented as roman numerals
	 */
	String convertToRoman(int arabicNumber)
	{
		var entry = romanNumeralBoundaries.floorEntry(arabicNumber);
		var remainder = arabicNumber - entry.getKey();
		var currentRomanNumeral = entry.getValue();

		return remainder > 0 ? currentRomanNumeral + convertToRoman(remainder) : currentRomanNumeral;
	}

	/**
	 * Convert a Roman Numeral into an Arabic Number.
	 * Algorithm finds the Arabic value of the first character and the second character if
	 *
	 * @param romanNumber roman numeral number to convert
	 *
	 * @return integer as the arabic representation of the number
	 */
	Integer convertToArabic(String romanNumber)
	{
		var firstCharValue = romanCharacterLookup.get(romanNumber.substring(0, 1));
		if (romanNumber.length() == 1)
		{
			return firstCharValue;
		}
		var nextCharValue = romanCharacterLookup.get(romanNumber.substring(1, 2));
		if (nextCharValue > firstCharValue)
		{
			firstCharValue = -firstCharValue;
		}

		return firstCharValue + convertToArabic(romanNumber.substring(1));
	}
}

package ca.jeremycook.romannumeralconverter;

import java.util.Collections;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Function;

/**
 * Class to convert to and from Roman Numerals and Arabic numbers.
 */
final class RomanNumeralConverter
{
	/**
	 * Map of boundary values used to convert between Roman and Arabic numbers.
	 */
	private static final NavigableMap<Integer, String> romanNumeralBoundaries;

	static
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
	}

	/**
	 * Function to map a roman numeral character to the arabic number contained in the romanNumeralBoundaries map.
	 */
	private static final Function<String, Integer> findArabicNumberFromRomanCharacter = character -> romanNumeralBoundaries.entrySet()
			.stream()
			.filter(e -> e.getValue().equals(character))
			.findFirst()
			.map(Entry::getKey)
			.orElseThrow(() -> new IllegalArgumentException(String.format("Unable to map roman numeral '%s' to an arabic character.", character)));

	/**
	 * Converts an arabic number to roman numerals.
	 * The algorithm works by using TreeMap::floorEntry to find the key closest to the number.
	 * The number is decremented by the key found each time.
	 *
	 * @param arabicNumber number to convert
	 *
	 * @return number represented as roman numerals
	 */
	static String convertToRoman(int arabicNumber)
	{
		Entry<Integer, String> entry = romanNumeralBoundaries.floorEntry(arabicNumber);
		int remainder = arabicNumber - entry.getKey();
		String currentRomanNumeral = entry.getValue();

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
	static Integer convertToArabic(String romanNumber)
	{
		if (romanNumber.length() == 1)
		{
			return findArabicNumberFromRomanCharacter.apply(romanNumber);
		}
		int firstCharValue = findArabicNumberFromRomanCharacter.apply(romanNumber.substring(0, 1));
		int nextCharValue = findArabicNumberFromRomanCharacter.apply(romanNumber.substring(1, 2));
		if (nextCharValue > firstCharValue)
		{
			firstCharValue = -firstCharValue;
		}

		return firstCharValue + convertToArabic(romanNumber.substring(1));
	}
}

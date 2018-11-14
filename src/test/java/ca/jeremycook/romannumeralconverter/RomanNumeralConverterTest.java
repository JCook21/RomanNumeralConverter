package ca.jeremycook.romannumeralconverter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test cases for the implementation of the roman numeral converter.
 */
@RunWith(Parameterized.class)
public class RomanNumeralConverterTest
{
	@Parameter
	public int arabicNumber;

	@Parameter(value = 1)
	public String romanNumber;

	private RomanNumeralConverter converter;

	@Parameters
	public static Collection<Object[]> data()
	{
		return Arrays.asList(new Object[][] {
				{ 1, "I" },
				{ 2, "II" },
				{ 3, "III" },
				{ 4, "IV" },
				{ 5, "V" },
				{ 6, "VI" },
				{ 7, "VII" },
				{ 8, "VIII" },
				{ 9, "IX" },
				{ 10, "X" },
				{ 12, "XII" },
				{ 18, "XVIII" },
				{ 19, "XIX" },
				{ 20, "XX" },
				{ 21, "XXI" },
				{ 25, "XXV" },
				{ 27, "XXVII" },
				{ 34, "XXXIV" },
				{ 39, "XXXIX" },
				{ 40, "XL" },
				{ 41, "XLI" },
				{ 153, "CLIII" },
				{ 268, "CCLXVIII" },
				{ 400, "CD" },
				{ 999, "CMXCIX" },
				{ 1000, "M" },
				{ 2578, "MMDLXXVIII" },
				{ 2499, "MMCDXCIX" }
		});
	}

	@Before
	public void setUp()
	{
		converter = new RomanNumeralConverter();
	}

	@Test
	public void testConvertingArabicToRoman()
	{
		assertThat(converter.convertToRoman(arabicNumber), is(romanNumber));
	}

	@Test
	public void testConvertingRomanToArabic()
	{
		assertThat(converter.convertToArabic(romanNumber), is(arabicNumber));
	}
}
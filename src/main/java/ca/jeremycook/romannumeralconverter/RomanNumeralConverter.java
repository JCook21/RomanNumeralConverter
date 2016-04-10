package ca.jeremycook.romannumeralconverter;

import ca.jeremycook.romannumeralconverter.collector.RomanToArabicSummingCollector;

import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

/**
 * Class to convert to and from Roman Numerals and Arabic numbers.
 */
public class RomanNumeralConverter {
    /**
     * Map of boundary values used to convert between Roman and Arabic numbers.
     */
    private static final NavigableMap<Integer, String> boundaries = new TreeMap<>();

    static {
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
    }

    /**
     * Function to map a roman numeral character to the arabic number contained in the boundaries map.
     */
    private static final Function<String, Integer> findArabicNumberFromRomanCharacter = character -> boundaries.entrySet()
            .stream()
            .filter(e -> e.getValue().equals(character))
            .map(Entry::getKey)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException(String.format("Unable to map roman numeral '%s' to an arabic character.", character)));

    /**
     * Converts an arabic number to roman numerals.
     * The algorithm works by using TreeMap::floorKey to find the key closest to the number.
     * The number is decremented by the key found each time.
     * Once the stream is generated zero values are removed and floorKey is used again to find the map keys for each number.
     * The collection of keys is then used to look up the roman numeral for each value, returning these joined as a string.
     *
     * @param arabicNumber number to convert
     * @return number represented as roman numerals
     */
    static String convertToRoman(int arabicNumber) {
        return Stream.iterate(arabicNumber, remainder -> {
            Integer boundariesKey = boundaries.floorKey(remainder);

            return boundariesKey != null ? (remainder - boundariesKey) : 0;
        })
                .limit(boundaries.size())
                .filter(val -> val > 0)
                .map(boundaries::floorKey)
                .map(boundaries::get)
                .collect(joining());
    }

    /**
     * Convert a Roman Numeral into an Arabic Number.
     * Algorithm reverses the string and then maps each character to the number value using the boundaries map.
     * It then uses the Roman to Arabic summing collector to reduce the stream into an integer.
     * @param romanNumber roman numeral number to convert
     * @return integer as the arabic representation of the number
     */
    static int convertToArabic(CharSequence romanNumber) {
        return new StringBuilder(romanNumber)
                .reverse()
                .chars()
                .mapToObj(character -> Character.toString((char) character))
                .map(findArabicNumberFromRomanCharacter)
                .collect(new RomanToArabicSummingCollector());
    }
}

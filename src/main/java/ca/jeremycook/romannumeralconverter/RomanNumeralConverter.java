package ca.jeremycook.romannumeralconverter;

import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

/**
 * Class to convertToRoman to and from Roman Numerals to Arabic numbers.
 */
public class RomanNumeralConverter {
    /**
     * Map of boundary values used to convertToRoman between Roman and Arabic numbers.
     */
    private static final TreeMap<Integer, String> boundaries = new TreeMap<>();
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
     * Converts an arabic number to roman numerals.
     * The algorithm works by using TreeMap::floorKey to find the key closest to the number.
     * The number is decremented by the key found each time.
     * The collection of keys is then used to look up the roman numeral for each value, returning these joined as a string.
     * @param arabicNumber number to convert
     * @return number represented as roman numerals
     */
    public String convertToRoman(int arabicNumber) {
        return IntStream.iterate(arabicNumber, remainder -> {
            Optional<Integer> boundariesKey = Optional.ofNullable(boundaries.floorKey(remainder));

            return (boundariesKey.isPresent()) ? (remainder - boundariesKey.get()) : 0;
        })
                .limit(boundaries.size())
                .filter(val -> val > 0)
                .map(boundaries::floorKey)
                .mapToObj(boundaries::get)
                .collect(joining());
    }
}

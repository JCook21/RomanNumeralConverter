package ca.jeremycook.romannumeralconverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {

    public static final Predicate<String> romanNumeralValidator = Pattern.compile("^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$").asPredicate();

    private static final int MAX_ROMAN_NUMBER = 3999;

    private static final String EXIT_COMMAND = "exit";

    private static final String NUMBER_ERROR_MESSAGE = "Enter a valid integer > 0 and <= %d, '%s' entered.";
    private static final String ROMAN_NUMERAL_ERROR = "Unable to convert '%s' into an Arabic number. Did you enter a valid roman numeral?";
    private static final String COMMAND_ERROR = "Unable to parse input '%s' into 1 or 2.";
    private static final String INVALID_COMMAND = "Invalid command '%d' entered.";

    private static final Collection<Integer> commands = new ArrayList<>();

    static {
        commands.add(1);
        commands.add(2);
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the Arabic to Roman converter!");
        System.out.println("Type 'exit' to exit.");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Enter 1 to convert a number to a Roman Numeral.");
            System.out.println("Enter 2 to convert a Roman Numeral to a number.");
            System.out.print("> ");
            String input = br.readLine();
            if (input.toLowerCase().equals(EXIT_COMMAND)) {
                break;
            }
            try {
                Integer command = Integer.parseInt(input);
                if (!commands.contains(command)) {
                    System.out.println(String.format(INVALID_COMMAND, command));
                    continue;
                }
                String result = convertValue(command, br);
                System.out.println(result);
            } catch (NumberFormatException e) {
                System.out.println(String.format(COMMAND_ERROR, input));
            }
        }
        System.out.println("Exiting.");
    }

    private static String convertValue(Integer input, BufferedReader br) throws IOException {
        if (input == 1) {
            return convertToRoman(br);
        }

        return convertToArabic(br);
    }

    private static String convertToRoman(BufferedReader br) throws IOException {
        System.out.println("Enter an Arabic number below to see it converted to Roman numerals.");
        System.out.print("> ");
        String input = br.readLine();
        return Stream.generate(() -> {
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                return 0;
            }
        })
                .limit(1)
                .filter(val -> val > 0 && val <= MAX_ROMAN_NUMBER)
                .map(RomanNumeralConverter::convertToRoman)
                .findFirst()
                .orElse(String.format(NUMBER_ERROR_MESSAGE, MAX_ROMAN_NUMBER, input));
    }

    private static String convertToArabic(BufferedReader br) throws IOException {
        System.out.println("Enter a Roman Numeral below to see it converted to an Arabic number.");
        System.out.print("> ");
        String input = br.readLine();
        return Stream.generate(() -> input)
                .limit(1)
                .map(String::toUpperCase)
                .filter(romanNumeralValidator)
                .mapToInt(RomanNumeralConverter::convertToArabic)
                .mapToObj(Integer::toString)
                .findFirst()
                .orElse(String.format(ROMAN_NUMERAL_ERROR, input));
    }
}

package ca.jeremycook.romannumeralconverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

public class Main {

    private static final int MAX_ROMAN_NUMBER = 3999;
    private static final String EXIT_COMMAND = "exit";
    private static final String ERROR_MESSAGE = "Enter a valid integer > 0 and <= %d, '%s' entered.";

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the Arabic to Roman converter!");
        System.out.println("Type 'exit' to exit.");
        System.out.println("Enter an Arabic number below to see it converted to Roman numerals.");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("> ");
            String input = br.readLine();
            if (input.toLowerCase().equals(EXIT_COMMAND)) {
                break;
            }
            String result = IntStream.generate(() -> {
                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    return 0;
                }
            })
                    .limit(1)
                    .filter(val -> val > 0 && val <= MAX_ROMAN_NUMBER)
                    .mapToObj(RomanNumeralConverter::convertToRoman)
                    .findFirst()
                    .orElse(String.format(ERROR_MESSAGE, MAX_ROMAN_NUMBER, input));
            System.out.println(result);
        }
        System.out.println("Exiting.");
    }
}

package ca.jeremycook.romannumeralconverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.stream.Stream;

public class Main {

    private static final int MAX_ROMAN_NUMBER = 3999;
    private static final String EXIT_COMMAND = "exit";

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the Arabic to Roman converter!");
        System.out.println("Type 'exit' to exit.");
        System.out.println("Enter an Arabic number below to see it converted to Roman numerals.");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("> ");
            String input = br.readLine();
            if (input.toLowerCase().equals(EXIT_COMMAND)) {
                System.out.println("Exiting.");
                break;
            }
            Optional<String> romanNumeral = Stream.generate(() -> input)
                    .limit(1)
                    .mapToInt(val -> {
                        try {
                            return Integer.parseInt(val);
                        } catch (NumberFormatException e) {
                            return 0;
                        }
                    })
                    .filter(val -> val != 0)
                    .filter(val -> val <= MAX_ROMAN_NUMBER)
                    .mapToObj(RomanNumeralConverter::convertToRoman)
                    .findFirst();
            System.out.println(romanNumeral.orElse(String.format("Enter a valid integer <= %d, '%s' entered.", MAX_ROMAN_NUMBER, input)));
        }
    }
}

package ca.jeremycook.romannumeralconverter;

enum ErrorMessages
{
	NUMBER_ERROR_MESSAGE("Enter a valid integer > 0 and <= %d."),
	ROMAN_NUMERAL_ERROR("Unable to convert your input into an Arabic number. Did you enter a valid roman numeral?"),
	COMMAND_ERROR("Unable to parse input '%s' into 1 or 2."),
	INVALID_COMMAND("Invalid command '%s' entered."),
	NUMBER_PARSE_ERROR("Unable to parse input into a valid number.");
	private String value;

	ErrorMessages(String value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return value;
	}
}
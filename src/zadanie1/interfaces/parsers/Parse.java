package zadanie1.interfaces.parsers;

import zadanie1.exceptions.parserExceptions.ParsingException;

public interface Parse {

	public String getFormatType();

	public <T> T getRateFromString(String inputString) throws ParsingException;
}

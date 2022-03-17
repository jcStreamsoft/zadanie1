package zadanie1.interfaces.parsers;

import java.math.BigDecimal;

import zadanie1.exceptions.parserExceptions.ParsingException;

public interface ApiParse extends Parse {

	@Override
	public String getFormatType();

	@Override
	public BigDecimal getRateFromString(String inputString) throws ParsingException;
}

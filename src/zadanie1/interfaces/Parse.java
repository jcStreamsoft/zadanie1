package zadanie1.interfaces;

import java.math.BigDecimal;

import zadanie1.exceptions.parserExceptions.ParsingException;

public interface Parse {

	public String getFormatType();

	public BigDecimal getRateFromString(String inputString) throws ParsingException;
}

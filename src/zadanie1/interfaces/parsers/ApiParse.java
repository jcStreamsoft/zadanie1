package zadanie1.interfaces.parsers;

import zadanie1.exceptions.parserExceptions.ParsingException;
import zadanie1.model.apiModel.Rate;

public interface ApiParse extends Parse {

	@Override
	public String getFormatType();

	@Override
	public Rate getRateFromString(String inputString) throws ParsingException;
}

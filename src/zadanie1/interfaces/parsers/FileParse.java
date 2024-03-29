package zadanie1.interfaces.parsers;

import java.util.List;

import zadanie1.exceptions.parserExceptions.ParsingException;
import zadanie1.model.fileModel.RatesTable;

public interface FileParse extends Parse {
	@Override
	public String getFormatType();

	@Override
	public List<RatesTable> getRateFromString(String inputString) throws ParsingException;
}

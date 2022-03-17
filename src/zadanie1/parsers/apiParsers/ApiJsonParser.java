package zadanie1.parsers.apiParsers;

import java.io.IOException;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import zadanie1.exceptions.parserExceptions.ParsingException;
import zadanie1.interfaces.parsers.ApiParse;
import zadanie1.model.apiModel.Rate;
import zadanie1.model.apiModel.RatesTable;

public class ApiJsonParser implements ApiParse {

	private final static String formatType = "json";

	@Override
	public String getFormatType() {
		return formatType;
	}

	@Override
	public Rate getRateFromString(String inputString) throws ParsingException {
		try {
			RatesTable ratesTable = parseData(inputString);
			Rate result = extractRate(ratesTable);
			return result;
		} catch (IOException e) {
			throw new ParsingException("B³¹d parsowania danych", e);
		}
	}

	private RatesTable parseData(String inputString) throws StreamReadException, DatabindException, IOException {
		return new ObjectMapper().readValue(inputString, RatesTable.class);
	}

	private Rate extractRate(RatesTable ratesTable) {
		return ratesTable.getRates().get(0);
	}

}
package zadanie1.parsers.apiParsers;

import java.io.IOException;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import zadanie1.exceptions.parserExceptions.ParsingException;
import zadanie1.exceptions.parserExceptions.ReadingCurrencyRateException;
import zadanie1.interfaces.parsers.ApiParse;
import zadanie1.model.apiModel.Rate;
import zadanie1.model.apiModel.RatesTable;

public class ApiXmlParser implements ApiParse {

	private final static String formatType = "xml";

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
		} catch (Exception e) {
			throw new ParsingException("B³¹d parsowania danych", e);
		}
	}

	private RatesTable parseData(String inputString) throws StreamReadException, DatabindException, IOException {
		return new XmlMapper().readValue(inputString, RatesTable.class);
	}

	private Rate extractRate(RatesTable ratesTable) throws ReadingCurrencyRateException {
		return ratesTable.getRates().get(0);
	}

}
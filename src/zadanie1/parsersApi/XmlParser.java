package zadanie1.parsersApi;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import zadanie1.exceptions.parserExceptions.ParsingException;
import zadanie1.exceptions.parserExceptions.ReadingCurrencyRateException;
import zadanie1.interfaces.Parse;
import zadanie1.model.Response;

public class XmlParser implements Parse {

	private final static String formatType = "xml";

	@Override
	public String getFormatType() {
		return formatType;
	}

	@Override
	public BigDecimal getRateFromStream(String inputString) throws ParsingException {
		try {
			Response response = parseData(inputString);
			BigDecimal result = extractRate(response);
			return result;
		} catch (Exception e) {
			throw new ParsingException("B³¹d parsowania danych", e);
		}
	}

	private Response parseData(String inputString) throws StreamReadException, DatabindException, IOException {
		return new XmlMapper().readValue(inputString, Response.class);
	}

	private BigDecimal extractRate(Response response) throws ReadingCurrencyRateException {
		BigDecimal rate = response.getRates().get(0).getMid();
		if (rate == null) {
			throw new ReadingCurrencyRateException("Nie znaleziono kursu w danych XML");
		}
		return rate;
	}

}
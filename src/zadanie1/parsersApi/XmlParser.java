package zadanie1.parsersApi;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import zadanie1.exceptions.parserExceptions.ParsingException;
import zadanie1.exceptions.parserExceptions.ReadingCurrencyRateException;
import zadanie1.interfaces.NbpApiParser;
import zadanie1.model.Response;

public class XmlParser implements NbpApiParser {

	private final static String formatType = "xml";

	@Override
	public String getFormatType() {
		return formatType;
	}

	@Override
	public BigDecimal getRateFromStream(InputStream stream) throws ParsingException {
		try {
			Response response = parseData(stream);
			BigDecimal result = extractRate(response);
			return result;
		} catch (Exception e) {
			throw new ParsingException("B³¹d parsowania danych ->" + e.toString());
		}
	}

	private Response parseData(InputStream stream) throws StreamReadException, DatabindException, IOException {
		return new XmlMapper().readValue(stream, Response.class);
	}

	private BigDecimal extractRate(Response response) throws ReadingCurrencyRateException {
		return response.getRates().get(0).getMid();
	}
}

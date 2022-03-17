package zadanie1.parsers.fileParsers;

import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import zadanie1.exceptions.parserExceptions.ParsingException;
import zadanie1.interfaces.parsers.FileParse;
import zadanie1.model.Response;

public class FileJsonParser implements FileParse {

	private final static String formatType = "json";

	@Override
	public String getFormatType() {
		return formatType;
	}

	@Override
	public BigDecimal getRateFromString(String inputString) throws ParsingException {
		try {
			Response response = parseData(inputString);
			BigDecimal result = extractRate(response);
			return result;
		} catch (IOException e) {
			throw new ParsingException("B³¹d parsowania danych", e);
		}
	}

	private Response parseData(String inputString) throws StreamReadException, DatabindException, IOException {
		return new ObjectMapper().readValue(inputString, Response.class);
	}

	private BigDecimal extractRate(Response response) {
		return response.getRates().get(0).getMid();
	}

}

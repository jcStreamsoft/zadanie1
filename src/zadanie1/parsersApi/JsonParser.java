package zadanie1.parsersApi;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import zadanie1.exceptions.ReadingCurrencyRateException;
import zadanie1.interfaces.NbpApiParser;
import zadanie1.model.Response;

public class JsonParser implements NbpApiParser {

	@Override
	public String getFormatType() {
		return ("json");
	}

	@Override
	public BigDecimal getRateFromStream(InputStream stream) {
		try {
			Response response = parseData(stream);
			BigDecimal result = extractRate(response);
			return result;
		} catch (IOException | ReadingCurrencyRateException e) {
			// TODO Auto-generated catch block
		}
		return null;
	}

	@Override
	public Response parseData(InputStream stream) throws StreamReadException, DatabindException, IOException {
		return new ObjectMapper().readValue(stream, Response.class);
	}

	@Override
	public BigDecimal extractRate(Response response)throws ReadingCurrencyRateException {
		BigDecimal rate = response.getRates().get(0).getMid();
		if(rate == null) {
			throw new ReadingCurrencyRateException();
		}
		return rate;
	}
}

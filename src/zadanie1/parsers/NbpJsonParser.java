package zadanie1.parsers;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;

import com.fasterxml.jackson.databind.ObjectMapper;

import zadanie1.exceptions.ReadingCurrencyRateException;
import zadanie1.interfaces.NbpParser;
import zadanie1.model.Response;

public class NbpJsonParser implements NbpParser {
	public BigDecimal readRateFromResponse(HttpURLConnection connection){
		Response responseJSON;
		try {
			responseJSON = new ObjectMapper().readValue(connection.getInputStream(), Response.class);
			return responseJSON.getRates().get(0).getMid();
		} catch (IOException e) {
			throw new ReadingCurrencyRateException();
		}
	}
}

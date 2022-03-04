package zadanie1;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import zadanie1.enums.ResponseType;
import zadanie1.interfaces.ResponseParser;
import zadanie1.model.Response;

public class ResponseParserJson implements ResponseParser{

	@Override
	public BigDecimal getCurrencyRate(ResponseType responseType,HttpURLConnection currentConnection) throws StreamReadException, DatabindException, IOException {
		
		Response rs = new ObjectMapper().readValue(currentConnection.getInputStream(), Response.class);
		
		BigDecimal currencyRate  = rs.getRates().get(0).getMid();
		return currencyRate;
	}		
}

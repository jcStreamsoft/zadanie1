package zadanie1;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import zadanie1.enums.ResponseType;
import zadanie1.exceptions.ReadingCurrencyRateException;
import zadanie1.model.Response;

public class NbpDataParser {
	
	public BigDecimal getCurrencyRate(ResponseType responseType,HttpURLConnection connection) {
		try {
			switch (responseType) {
			case JSON:
				return readRateFromJson(connection);
			case XML:
				return readRateFromXml(connection);
			}
		}catch(StreamReadException | DatabindException  e) {
			throw new ReadingCurrencyRateException();
		}catch(IOException e) {
			throw new ReadingCurrencyRateException();
		}
		return new BigDecimal(1);
	}
	private BigDecimal readRateFromJson(HttpURLConnection connection) throws StreamReadException, DatabindException, IOException {
		Response responseJSON = new ObjectMapper().readValue(connection.getInputStream(), Response.class);
		return responseJSON.getRates().get(0).getMid();
	}
	private BigDecimal readRateFromXml(HttpURLConnection connection) throws StreamReadException, DatabindException, IOException{
		Response responseXML = new XmlMapper().readValue(connection.getInputStream(), Response.class);
		return responseXML.getRates().get(0).getMid();
	}
	
}

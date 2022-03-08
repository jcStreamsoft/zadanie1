package zadanie1.parsers;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import zadanie1.exceptions.ReadingCurrencyRateException;
import zadanie1.interfaces.NbpParser;
import zadanie1.model.Response;

public class NbpXmlParser implements NbpParser{
	public BigDecimal readRateFromResponse(HttpURLConnection connection) {
		Response responseXML;
		try {
			responseXML = new XmlMapper().readValue(connection.getInputStream(), Response.class);
			return responseXML.getRates().get(0).getMid();
		} catch (IOException e) {
			throw new ReadingCurrencyRateException();
		}
		
	}
}

package zadanie1.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import zadanie1.exceptions.ReadingCurrencyRateException;
import zadanie1.interfaces.StreamParser;
import zadanie1.model.Response;

public class XmlParser implements StreamParser{
	
	public BigDecimal getRateFromStream(InputStream stream) {
		try {
			Response response = parseData(stream);
			BigDecimal result = extractRate(response);
			return result;
		} catch (IOException e) {
			//save 
		}
		return null;
	}
	
	public Response parseData(InputStream stream) throws StreamReadException, DatabindException, IOException {
		return new XmlMapper().readValue(stream, Response.class);
	};
	public BigDecimal extractRate(Response response) {
		return response.getRates().get(0).getMid();
	};
}

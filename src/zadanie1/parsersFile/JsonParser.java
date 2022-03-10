package zadanie1.parsersFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import zadanie1.exceptions.ReadingCurrencyRateException;
import zadanie1.interfaces.NbpApiParser;
import zadanie1.interfaces.NbpFileParser;
import zadanie1.model.Response;

public class JsonParser implements NbpFileParser {

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
		} catch (IOException e) {
			// TODO Auto-generated catch block

		}

		return null;
	}

	@Override
	public Response parseData(InputStream stream) throws StreamReadException, DatabindException, IOException {
		return new ObjectMapper().readValue(stream, Response.class);
	}

	@Override
	public BigDecimal extractRate(Response response) {
		return response.getRates().get(0).getMid();
	}
}

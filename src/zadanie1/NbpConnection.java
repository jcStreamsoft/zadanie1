package zadanie1;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import zadanie1.enums.Currency;
import zadanie1.enums.ResponseType;
import zadanie1.model.Response;

public class NbpConnection {
	private final String NBP_ADRESS = "http://api.nbp.pl/api/exchangerates/rates/a/";
	private HttpURLConnection connection;
	private final int MAX_ATTEMPTS = 7;
	
	public void createConnection(Currency currency, LocalDate localDate, ResponseType responseType,int attempt) {
		try {
			URL url = new URL(NBP_ADRESS + currency.getCode() + "/" + localDate.toString() + "/?format="
					+ responseType.getType());
			System.out.println(url);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			if (connection.getResponseCode() == 404 && attempt < MAX_ATTEMPTS) {
				connection.disconnect();
				createConnection(currency, localDate.minusDays(1),  responseType,++attempt);
			}else {
				createLastCurrencyReading(currency, responseType);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public void createLastCurrencyReading(Currency currency, ResponseType responseType) {
		try {
			URL url = new URL(NBP_ADRESS + currency.getCode() + "/last/1/?format=" + responseType.getType());
			System.out.println(url);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public URL createUrl(Currency currency, LocalDate localDate, ResponseType responseType) throws MalformedURLException {
		return new URL(NBP_ADRESS + currency.getCode() + "/" + localDate.toString() + "/?format="+ responseType.getType());
	}
	public URL createUrl(Currency currency, ResponseType responseType) throws MalformedURLException {
		return new URL(NBP_ADRESS + currency.getCode() + "/last/1/?format="+ responseType.getType());
	}

	
	public BigDecimal getCurrencyRate(ResponseType responseType)throws IOException {
		try {
			switch (responseType) {
			case JSON:
				Response responseJSON = new ObjectMapper().readValue(connection.getInputStream(), Response.class);
				return responseJSON.getRates().get(0).getMid();
			case XML:
				Response responseXML = new XmlMapper().readValue(connection.getInputStream(), Response.class);
				return responseXML.getRates().get(0).getMid();
			}
		}catch(StreamReadException | DatabindException  e) {
			return new BigDecimal(1);
		}catch(IOException e) {
			return new BigDecimal(1);
		}
		return new BigDecimal(1);
	}
	public void disconnectConnection() {
		connection.disconnect();
	}

}

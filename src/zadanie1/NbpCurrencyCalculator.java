package zadanie1;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import zadanie1.enums.Currency;
import zadanie1.enums.ResponseType;
import zadanie1.model.Response;

public class NbpCurrencyCalculator {
	private final String NBP_ADRESS = "http://api.nbp.pl/api/exchangerates/rates/a/";
	private HttpURLConnection connection;

	public void getConnection(Currency currency, LocalDate localDate, ResponseType responseType) {

		try {
			URL url = new URL(NBP_ADRESS + currency.getCode() + "/" + localDate.toString() + "/?format="
					+ responseType.getType());
			// TODO dodawanie atrybutu
			System.out.println(url);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			if (connection.getResponseCode() == 404) {
				getLastCurrencyReading(currency, responseType);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void getLastCurrencyReading(Currency currency, ResponseType responseType) {
		try {
			URL url = new URL(NBP_ADRESS + currency.getCode() + "/last/1/?format=" + responseType.getType());
			// TODO dodawanie atrybutu
			System.out.println(url);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BigDecimal getCurrencyRate(ResponseType responseType)
			throws StreamReadException, DatabindException, IOException {
		switch (responseType) {
		case JSON:
			Response responseJSON = new ObjectMapper().readValue(connection.getInputStream(), Response.class);
			return responseJSON.getRates().get(0).getMid();
		case XML:
			Response responseXML = new XmlMapper().readValue(connection.getInputStream(), Response.class);
			return responseXML.getRates().get(0).getMid();	
		}
		
		return new BigDecimal(1);
	}

	public BigDecimal calculateToPln(BigDecimal value, Currency currency, ResponseType responseType, LocalDate date)
			throws StreamReadException, DatabindException, IOException {
		getConnection(currency, date, responseType);
		BigDecimal result = value.multiply(getCurrencyRate(responseType));
		connection.disconnect();
		return result;
	}

	public BigDecimal calculateToPln(BigDecimal value, Currency currency, ResponseType responseType)
			throws StreamReadException, DatabindException, IOException {
		return calculateToPln(value, currency, responseType, LocalDate.now().minusDays(1));
	}

	public BigDecimal calculateFromPln(BigDecimal value, Currency currency, ResponseType responseType, LocalDate date)
			throws StreamReadException, DatabindException, IOException {
		getConnection(currency, date, responseType);
		BigDecimal result = value.divide(getCurrencyRate(responseType), RoundingMode.CEILING);
		connection.disconnect();
		return result;
	}

	public BigDecimal calculateFromPln(BigDecimal value, Currency currency, ResponseType responseType)
			throws StreamReadException, DatabindException, IOException {
		return calculateFromPln(value, currency, responseType, LocalDate.now().minusDays(1));
	}

}

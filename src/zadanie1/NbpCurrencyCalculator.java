package zadanie1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jdi.connect.spi.Connection;

import zadanie1.enums.Currency;
import zadanie1.enums.ResponseType;
import zadanie1.exceptions.CurrencyNotFoundException;
import zadanie1.model.Response;

public class NbpCurrencyCalculator {
	private final String NBP_ADRESS = "http://api.nbp.pl/api/exchangerates/rates/a/";
	private final int MAX_ATTEMPTS = 7;
	private int numberOfAttempts = 0;
	private HttpURLConnection connection;

	public void getConnection(Currency currency,LocalDate localDate,ResponseType responseType){
		
		try {
			URL url = new URL(NBP_ADRESS + currency.getCode() + "/" + localDate.toString() + "/?format="+responseType.getType());
			// TODO dodawanie atrybutu
			// obs³uga data
			System.out.println(url);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			if (connection.getResponseCode() == 404) {
				getLastCurrencyReading(currency,responseType);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	public void getLastCurrencyReading(Currency currency,ResponseType responseType) {
			try {
				URL url = new URL(NBP_ADRESS + currency.getCode() + "/last/1/?format="+responseType.getType());
				// TODO dodawanie atrybutu
				System.out.println(url);
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
			} catch(IOException e) {
				e.printStackTrace();
			}
	}
	public void test() throws IOException {
		int responseCode = connection.getResponseCode();
		InputStream inputStream;
	    if (200 <= responseCode && responseCode <= 299) {
	        inputStream = connection.getInputStream();
	    } else {
	        inputStream = connection.getErrorStream();
	    }

	    BufferedReader in = new BufferedReader(
	        new InputStreamReader(
	            inputStream));

	    StringBuilder response = new StringBuilder();
	    String currentLine;

	    while ((currentLine = in.readLine()) != null) 
	        response.append(currentLine);
	    System.out.println(response.toString());
	    in.close();
	}
	public BigDecimal getCurrencyRate(ResponseType responseType) throws StreamReadException, DatabindException, IOException {		
		Response rs = new ObjectMapper().readValue(connection.getInputStream(), Response.class);
		BigDecimal currencyRate  = rs.getRates().get(0).getMid();
		return currencyRate;
	}
	
	public BigDecimal calculateToPln(BigDecimal value, Currency currency,ResponseType responseType,LocalDate date)  throws StreamReadException, DatabindException, IOException{
		getConnection(currency,date,responseType);
		BigDecimal result=value.multiply(getCurrencyRate(responseType));
		connection.disconnect();
		return result;
	}
	public BigDecimal calculateToPln(BigDecimal value, Currency currency,ResponseType responseType)  throws StreamReadException, DatabindException, IOException{
		return calculateToPln(value,currency,responseType,LocalDate.now().minusDays(1));
	}

	public BigDecimal calculateFromPln(BigDecimal value, Currency currency,ResponseType responseType,LocalDate date)  throws StreamReadException, DatabindException, IOException{
		getConnection(currency,date,responseType);
		BigDecimal result = value.divide(getCurrencyRate(responseType),RoundingMode.CEILING);
		connection.disconnect();
		return result;
	}
	public BigDecimal calculateFromPln(BigDecimal value, Currency currency,ResponseType responseType)  throws StreamReadException, DatabindException, IOException{
		return calculateFromPln(value,currency,responseType,LocalDate.now().minusDays(1));
	}

}

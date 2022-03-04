package zadanie1;

import java.io.BufferedReader;
import java.io.IOException;
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
	private final String nbpAdress = "http://api.nbp.pl/api/exchangerates/rates/a/";

	private HttpURLConnection currentConnection;

	public void getConnection(Currency currency,LocalDate localDate,ResponseType responseType){
		
		try {
			URL url = new URL(nbpAdress + currency.getCode() + "/" + localDate.toString() + "/?format="+responseType.getType());
			
			System.out.println(url);
			currentConnection = (HttpURLConnection) url.openConnection();
			currentConnection.setRequestMethod("GET");
			int responseCode = currentConnection.getResponseCode();

			if (responseCode != 200) {
				throw new CurrencyNotFoundException();
			}
		} catch (CurrencyNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}

	}

	public BigDecimal getCurrencyRate(ResponseType responseType) throws StreamReadException, DatabindException, IOException {
		
		Response rs = new ObjectMapper().readValue(currentConnection.getInputStream(), Response.class);
		System.out.println(rs.toString());
		
		BigDecimal currencyRate  = rs.getRates().get(0).getMid();
		
		return currencyRate;
	}
	
	public BigDecimal calculateCurrency(BigDecimal value, Currency currency,ResponseType responseType) throws StreamReadException, DatabindException, IOException {
		getConnection(currency,LocalDate.now().minusDays(1),responseType);
		BigDecimal rate=getCurrencyRate(responseType);
		BigDecimal result = value.multiply(rate);
		
		System.out.println(currentConnection.getResponseCode());
		return result;
		
	}

	public BigDecimal calculateToPln(BigDecimal value, Currency currency,ResponseType responseType,LocalDate date)  throws StreamReadException, DatabindException, IOException{
		getConnection(currency,date,responseType);
		BigDecimal rate=getCurrencyRate(responseType);
		BigDecimal result = value.multiply(rate);
		currentConnection.disconnect();
		System.out.println("wynik"+result);
		return result;
	}
	public BigDecimal calculateToPln(BigDecimal value, Currency currency,ResponseType responseType)  throws StreamReadException, DatabindException, IOException{
		return calculateToPln(value,currency,responseType,LocalDate.now().minusDays(1));
	}

	public BigDecimal calculateFromPln(BigDecimal value, Currency currency,ResponseType responseType,LocalDate date)  throws StreamReadException, DatabindException, IOException{
		getConnection(currency,date,responseType);
		BigDecimal rate=getCurrencyRate(responseType);
		BigDecimal result = value.divide(rate,RoundingMode.CEILING);
		
		currentConnection.disconnect();
		return result;
	}
	public BigDecimal calculateFromPln(BigDecimal value, Currency currency,ResponseType responseType)  throws StreamReadException, DatabindException, IOException{
		return calculateFromPln(value,currency,responseType,LocalDate.now().minusDays(1));
	}

}

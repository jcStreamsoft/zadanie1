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
import zadanie1.exceptions.ConnectionException;
import zadanie1.exceptions.ReadingCurrencyRateException;
import zadanie1.exceptions.WrongURLException;
import zadanie1.model.Response;

public class Connection {
	private final String NBP_ADRESS = "http://api.nbp.pl/api/exchangerates/rates/a/";
	private HttpURLConnection connection;
	private final int MAX_ATTEMPTS = 7;
	
	public void createConnection(Currency currency, LocalDate localDate, ResponseType responseType) {
		createConnection(currency, localDate,  responseType,0);
	}
	private void createConnection(Currency currency, LocalDate localDate, ResponseType responseType,int attempt) {
		try {
			URL url = new URL(NBP_ADRESS + currency.getCode() + "/" + localDate + "/?format=" + responseType.getType());
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			
			if ( connection.getResponseCode() == 404 && attempt < MAX_ATTEMPTS) {
				connection.disconnect();
				createConnection(currency, localDate.minusDays(1),  responseType,++attempt);
			}else {
				createLastCurrencyReading(currency, responseType);
			}
		} catch(MalformedURLException e){
			throw new WrongURLException();
		}catch (IOException e) {
			connection.disconnect();
			throw new ConnectionException();
		}
	}
	
	public void createLastCurrencyReading(Currency currency, ResponseType responseType) {
		try {
			URL url = new URL(NBP_ADRESS + currency.getCode() + "/last/1/?format=" + responseType.getType());
			//System.out.println(url);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
		} catch(MalformedURLException e){
			throw new WrongURLException();
		}catch (IOException e) {
			connection.disconnect();
			throw new ConnectionException();
		}
	}
	public InputStream getInputStream() {
		try {
			return connection.getInputStream();
		} catch (IOException e) {
			return null;
		}
	}
	public void disconnectConnection() {
		connection.disconnect();
	}
	
	

}

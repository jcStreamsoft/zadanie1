package zadanie1;


import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import zadanie1.enums.Currency;
import zadanie1.enums.ResponseType;
import zadanie1.parsers.DataParser;

public class CurrencyCalculator {
	private Connection nbpConnection;
	private DataParser nbpDataParser;
	public CurrencyCalculator() {
		this.nbpConnection = new Connection();
		this.nbpDataParser = new DataParser();
	}
	
	public BigDecimal calculateToPln(BigDecimal value, Currency currency, ResponseType responseType, LocalDate date){
		nbpConnection.createConnection(currency, date, responseType);
		InputStream stream =  nbpConnection.getInputStream();
		BigDecimal rate = nbpDataParser.getCurrencyRate(responseType,stream);
		nbpConnection.disconnectConnection();
		return calculateFromPln(value,rate);
	}

	public BigDecimal calculateToPln(BigDecimal value, Currency currency, ResponseType responseType) {
		return calculateToPln(value, currency, responseType, LocalDate.now().minusDays(1));
	}

	public BigDecimal calculateFromPln(BigDecimal value, Currency currency, ResponseType responseType, LocalDate date){
		nbpConnection.createConnection(currency, date, responseType);
		InputStream stream =  nbpConnection.getInputStream();
		BigDecimal rate = nbpDataParser.getCurrencyRate(responseType,stream);
		nbpConnection.disconnectConnection();
		return calculateToPln(value,rate);
	}

	public BigDecimal calculateFromPln(BigDecimal value, Currency currency, ResponseType responseType) {
		return calculateFromPln(value, currency, responseType, LocalDate.now().minusDays(1));
	}
	
	private BigDecimal calculateToPln(BigDecimal value,BigDecimal rate) {
		return value.divide(rate, RoundingMode.CEILING);
	}
	private BigDecimal calculateFromPln(BigDecimal value,BigDecimal rate) {
		return value.multiply(rate);
	}
}

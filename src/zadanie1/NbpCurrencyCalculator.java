package zadanie1;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import zadanie1.enums.Currency;
import zadanie1.enums.ResponseType;

public class NbpCurrencyCalculator {
	private NbpConnection nbpConnection;
	private NbpDataParser nbpDataParser;
	public NbpCurrencyCalculator() {
		this.nbpConnection = new NbpConnection();
		this.nbpDataParser = new NbpDataParser();
	}
	
	public BigDecimal calculateToPln(BigDecimal value, Currency currency, ResponseType responseType, LocalDate date){
		nbpConnection.createConnection(currency, date, responseType,0);
		BigDecimal rate = nbpDataParser.getCurrencyRate(responseType, nbpConnection.getConnection());
		BigDecimal result = value.multiply(rate);
		nbpConnection.disconnectConnection();
		return result;
	}

	public BigDecimal calculateToPln(BigDecimal value, Currency currency, ResponseType responseType) {
		return calculateToPln(value, currency, responseType, LocalDate.now().minusDays(1));
	}

	public BigDecimal calculateFromPln(BigDecimal value, Currency currency, ResponseType responseType, LocalDate date){
		nbpConnection.createConnection(currency, date, responseType,0);
		BigDecimal rate = nbpDataParser.getCurrencyRate(responseType, nbpConnection.getConnection());
		BigDecimal result = value.divide(rate, RoundingMode.CEILING);
		nbpConnection.disconnectConnection();
		return result;
	}

	public BigDecimal calculateFromPln(BigDecimal value, Currency currency, ResponseType responseType) {
		return calculateFromPln(value, currency, responseType, LocalDate.now().minusDays(1));
	}

}

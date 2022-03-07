package zadanie1;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import zadanie1.enums.Currency;
import zadanie1.enums.ResponseType;

public class NbpCurrencyCalculator {
	private NbpConnection nbpConnection;
	public NbpCurrencyCalculator() {
		this.nbpConnection = new NbpConnection();
	}

	public BigDecimal calculateToPln(BigDecimal value, Currency currency, ResponseType responseType, LocalDate date)
			throws IOException {
		nbpConnection.createConnection(currency, date, responseType,0);
		BigDecimal result = value.multiply(nbpConnection.getCurrencyRate(responseType));
		nbpConnection.disconnectConnection();
		return result;
	}

	public BigDecimal calculateToPln(BigDecimal value, Currency currency, ResponseType responseType)
			throws IOException {
		return calculateToPln(value, currency, responseType, LocalDate.now().minusDays(1));
	}

	public BigDecimal calculateFromPln(BigDecimal value, Currency currency, ResponseType responseType, LocalDate date)
			throws IOException {
		nbpConnection.createConnection(currency, date, responseType,0);
		BigDecimal result = value.divide(nbpConnection.getCurrencyRate(responseType), RoundingMode.CEILING);
		nbpConnection.disconnectConnection();
		return result;
	}

	public BigDecimal calculateFromPln(BigDecimal value, Currency currency, ResponseType responseType)
			throws IOException {
		return calculateFromPln(value, currency, responseType, LocalDate.now().minusDays(1));
	}

}

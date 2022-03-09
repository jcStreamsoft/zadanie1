package zadanie1;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import zadanie1.enums.Currency;
import zadanie1.interfaces.StreamParser;
import zadanie1.parsers.DataParser;

public class CurrencyCalculator {

	private Connection connection;
	private DataParser dataParser;
	private String dataFormat;

	public CurrencyCalculator(StreamParser parser) {
		this.dataParser = new DataParser(parser);
		this.dataFormat = dataParser.getDataParserFormat();
	}

	public BigDecimal calculateToPln(BigDecimal value, Currency currency, LocalDate date) {
		InputStream stream = getStreamFromNbp(currency, date);
		BigDecimal rate = getRateFromStream(stream);
		connection.disconnectConnection();
		return calculateFromPln(value, rate);
	}

	public BigDecimal calculateToPln(BigDecimal value, Currency currency) {
		return calculateToPln(value, currency, LocalDate.now());
	}

	public BigDecimal calculateFromPln(BigDecimal value, Currency currency, LocalDate date) {
		InputStream stream = getStreamFromNbp(currency, date);
		BigDecimal rate = getRateFromStream(stream);
		connection.disconnectConnection();
		return calculateToPln(value, rate);
	}

	public BigDecimal calculateFromPln(BigDecimal value, Currency currency) {
		return calculateFromPln(value, currency, LocalDate.now());
	}

	private BigDecimal calculateToPln(BigDecimal value, BigDecimal rate) {
		return value.divide(rate, RoundingMode.CEILING);
	}

	private BigDecimal calculateFromPln(BigDecimal value, BigDecimal rate) {
		return value.multiply(rate);
	}

	public InputStream getStreamFromNbp(Currency currency, LocalDate date) {
		connection = new Connection(currency, dataFormat);
		connection.createConnection(date);
		return connection.getInputStream();
	}

	private BigDecimal getRateFromStream(InputStream inputStream) {
		return dataParser.getCurrencyRate(inputStream);
	}
}

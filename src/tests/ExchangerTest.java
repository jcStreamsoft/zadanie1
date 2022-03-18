package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import zadanie1.Exchanger;
import zadanie1.connectors.ApiConnection;
import zadanie1.enums.Currency;
import zadanie1.exceptions.ExchangerException;
import zadanie1.interfaces.DataConnection;
import zadanie1.model.Request;
import zadanie1.parsers.apiParsers.ApiJsonParser;

public class ExchangerTest {
	Exchanger exchanger;
	BigDecimal value;
	LocalDate date;
	Currency currency;

	@BeforeMethod
	public void setup() {
		List<DataConnection> connections = List.of(new ApiConnection(new ApiJsonParser()));
		value = new BigDecimal(1);
		date = LocalDate.now();
		currency = Currency.EUR;
		exchanger = new Exchanger(connections);
	}

	@Test
	public void shouldReturnTrue_whenInputIsCorrectOnExchangeToPln() {
		// given
		date = LocalDate.parse("2002-01-04");
		value = new BigDecimal("2");
		Request request = Request.getBuilder(value, currency).date(date).build();
		BigDecimal expected = new BigDecimal("0.5658348893792791263509308");
		// when
		BigDecimal result = exchanger.exchangeToPln(request);
		// then
		assertEquals(result, expected);
	}

	@Test
	public void shouldReturnTrue_whenInputIsCorrectOnExchangeFromPln() {
		// given
		date = LocalDate.parse("2002-01-04");
		value = new BigDecimal("2");
		Request request = Request.getBuilder(value, currency).date(date).build();
		BigDecimal expected = new BigDecimal("7.0692");
		// when
		BigDecimal result = exchanger.exchangeFromPln(request);
		// then
		assertEquals(result, expected);
	}

	@Test
	public void shouldThrowExchangerException_whenRequestBeforFirstLocalDate() {
		// given
		date = LocalDate.parse("2002-01-01");
		Request request = Request.getBuilder(value, currency).date(date).build();
		// throws
		assertThrows(ExchangerException.class, () -> exchanger.exchangeFromPln(request));
	}

	@Test
	public void shouldThrowExchangerException_whenRequestAfterTodayLocalDate() {
		// given
		date = LocalDate.now().plusDays(1);
		Request request = Request.getBuilder(value, currency).date(date).build();
		// throws
		assertThrows(ExchangerException.class, () -> exchanger.exchangeFromPln(request));
	}

	@Test
	public void shouldThrowExchangerException_whenRequestNullValue() {
		// given
		value = null;
		Request request = Request.getBuilder(value, currency).date(date).build();
		// throws
		assertThrows(ExchangerException.class, () -> exchanger.exchangeFromPln(request));
	}

	@Test
	public void shouldThrowExchangerException_whenRequestNegativeValue() {
		// given
		value = new BigDecimal(-1);
		Request request = Request.getBuilder(value, currency).date(date).build();
		// throws
		assertThrows(ExchangerException.class, () -> exchanger.exchangeFromPln(request));
	}

	@Test
	public void shouldThrowExchangerException_whennRequestNullCurrency() {
		// given
		currency = null;
		Request request = Request.getBuilder(value, currency).date(date).build();
		// throws
		assertThrows(ExchangerException.class, () -> exchanger.exchangeFromPln(request));
	}
}

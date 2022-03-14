package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import zadanie1.CurrencyCalculator;
import zadanie1.Exchanger;
import zadanie1.connectors.ApiConnection;
import zadanie1.connectors.FileConnection;
import zadanie1.enums.Currency;
import zadanie1.model.Request;
import zadanie1.parsersApi.JsonParser;

public class ExchangerTest {
	Exchanger exchanger;
	BigDecimal value;
	LocalDate date;
	Currency currency;

	@BeforeTest
	public void setup() {
		value = new BigDecimal(1);
		date = LocalDate.now();
		currency = Currency.EUR;
		exchanger = new Exchanger(new JsonParser(), new CurrencyCalculator(), new ApiConnection());
	}
	@Test
	public void givenCorrectRequest_whenExchangeToPln_thenValue() {
		// given
		date = LocalDate.parse("2002-01-04");
		value = new BigDecimal(2);
		Request request = new Request
				.Builder(value, currency)
				.localDate(date)
				.build();
		MathContext m = new MathContext(15);
		// when
		BigDecimal result = exchanger.exchangeToPln(request);
		result = result.round(m);
		// then
		BigDecimal expected = new BigDecimal(0.5658348893792791263509308);
		expected = expected.round(m);
		//assertEquals(result, expected);
		assertTrue(result.compareTo(expected)==0);
	}
	@Test
	public void givenCorrectRequest_whenExchangeFromPln_thenValue() {
		// given
		date = LocalDate.parse("2002-01-04");
		value = new BigDecimal(2);
		Request request = new Request
				.Builder(value, currency)
				.localDate(date)
				.build();
		MathContext m = new MathContext(1);
		// when
		BigDecimal result = exchanger.exchangeFromPln(request);
		result = result.round(m);
		// then
		BigDecimal expected = new BigDecimal(7.0692);
		expected = expected.round(m);
		//assertEquals(result, expected);
		assertTrue(result.compareTo(expected)==0);
	}

	@Test
	public void givenRequestNullLocalDate_whenExchangeToPln_thenRetrunNull() {
		// given
		date = null;
		Request request = new Request
				.Builder(value, currency)
				.localDate(date)
				.build();

		// when
		BigDecimal result = exchanger.exchangeFromPln(request);

		// then
		assertEquals(result, null);
	}

	@Test
	public void givenRequestBeforFirstLocalDate_whenExchangeToPln_thenRetrunNull() {
		// given
		date = LocalDate.parse("2002-01-01");
		Request request = new Request
				.Builder(value, currency)
				.localDate(date)
				.build();

		// when
		BigDecimal result = exchanger.exchangeFromPln(request);

		// then
		assertEquals(result, null);
	}

	@Test
	public void givenRequestAfterTodayLocalDate_whenExchangeToPln_thenRetrunNull() {
		// given
		date = LocalDate.now().plusDays(1);
		Request request = new Request
				.Builder(value, currency)
				.localDate(date)
				.build();

		// when
		BigDecimal result = exchanger.exchangeFromPln(request);

		// then
		assertEquals(result, null);
	}

	@Test
	public void givenRequestNullValue_whenExchangeToPln_thenRetrunNull() {
		// given
		value = null;
		Request request = new Request
				.Builder(value, currency)
				.localDate(date)
				.build();

		// when
		BigDecimal result = exchanger.exchangeFromPln(request);

		// then
		assertEquals(result, null);
	}

	@Test
	public void givenRequestNegativeValue_whenExchangeToPln_thenRetrunNull() {
		// given
		value = new BigDecimal(-1);
		Request request = new Request
				.Builder(value, currency)
				.localDate(date)
				.build();

		// when
		BigDecimal result = exchanger.exchangeFromPln(request);

		// then
		assertEquals(result, null);
	}

	@Test
	public void givenRequestNullCurrency_whenExchangeToPln_thenRetrunNull() {
		// given
		currency = null;
		Request request = new Request
				.Builder(value, currency)
				.localDate(date)
				.build();

		// when
		BigDecimal result = exchanger.exchangeFromPln(request);

		// then
		assertEquals(result, null);
	}

	@Test
	public void givenRequestNoPath_whenExchangeToPln_thenRetrunNull() {
		// given
		Request request = new Request
				.Builder(value, currency)
				.localDate(date)
				.build();
		exchanger = new Exchanger(new JsonParser(), new CurrencyCalculator(), new FileConnection());

		// when
		BigDecimal result = exchanger.exchangeFromPln(request);

		// then
		assertEquals(result, null);
	}

	@Test
	public void givenNoParser_whenExchangeToPln_thenRetrunNull() {
		// given
		Request request = new Request
				.Builder(value, currency)
				.localDate(date)
				.build();
		exchanger = new Exchanger(null, new CurrencyCalculator(), new FileConnection());

		// when
		BigDecimal result = exchanger.exchangeFromPln(request);

		// then
		assertEquals(result, null);
	}

	@Test
	public void givenNoCalculator_whenExchangeToPln_thenRetrunNull() {
		// given
		Request request = new Request
				.Builder(value, currency)
				.localDate(date)
				.build();
		exchanger = new Exchanger(new JsonParser(), null, new FileConnection());

		// when
		BigDecimal result = exchanger.exchangeFromPln(request);

		// then
		assertEquals(result, null);
	}

	@Test
	public void givenNoStreams_whenExchangeToPln_thenRetrunNull() {
		// given
		Request request = new Request
				.Builder(value, currency)
				.localDate(date)
				.build();
		exchanger = new Exchanger(new JsonParser(), new CurrencyCalculator(), null);

		// when
		BigDecimal result = exchanger.exchangeFromPln(request);

		// then
		assertEquals(result, null);
	}
}

package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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
		exchanger = new Exchanger(new JsonParser(), new ApiConnection());
	}

	@Test
	public void shouldReturnTrue_whenInputIsCorrectOnExchangeToPln() {
		// given
		date = LocalDate.parse("2002-01-04");
		value = new BigDecimal(2);
		Request request = Request.getBuilder(value, currency).localDate(date).build();
		MathContext m = new MathContext(15);
		BigDecimal expected = new BigDecimal(0.5658348893792791263509308);
		expected = expected.round(m);
		// when
		BigDecimal result = exchanger.exchangeToPln(request);
		result = result.round(m);
		// then

		assertEquals(result, expected);
		// assertTrue(result.compareTo(expected) == 0);
	}

	@Test
	public void shouldReturnTrue_whenInputIsCorrectOnExchangeFromPln() {
		// given
		date = LocalDate.parse("2002-01-04");
		value = new BigDecimal(2);
		Request request = Request.getBuilder(value, currency).localDate(date).build();
		MathContext m = new MathContext(10);
		BigDecimal expected = new BigDecimal(7.0692);
		expected = expected.round(m);
		// when
		BigDecimal result = exchanger.exchangeFromPln(request);
		result = result.round(m);
		// then

		// assertEquals(result, expected);
		assertTrue(result.compareTo(expected) == 0);
	}

	@Test
	public void shouldReturnNull_whenRequestDateIsNull() {
		// given
		date = null;
		Request request = Request.getBuilder(value, currency).localDate(date).build();

		// when
		BigDecimal result = exchanger.exchangeFromPln(request);

		// then
		assertEquals(result, null);
	}

	@Test
	public void shouldRetrunNull_whenRequestBeforFirstLocalDate() {
		// given
		date = LocalDate.parse("2002-01-01");
		Request request = Request.getBuilder(value, currency).localDate(date).build();

		// when
		BigDecimal result = exchanger.exchangeFromPln(request);

		// then
		assertEquals(result, null);
	}

	@Test
	public void shouldRetrunNull_whenRequestAfterTodayLocalDate() {
		// given
		date = LocalDate.now().plusDays(1);
		Request request = Request.getBuilder(value, currency).localDate(date).build();

		// when
		BigDecimal result = exchanger.exchangeFromPln(request);

		// then
		assertEquals(result, null);
	}

	@Test
	public void shouldRetrunNull_whenRequestNullValue() {
		// given
		value = null;
		Request request = Request.getBuilder(value, currency).localDate(date).build();

		// when
		BigDecimal result = exchanger.exchangeFromPln(request);

		// then
		assertEquals(result, null);
	}

	@Test
	public void shouldRetrunNull_whenRequestNegativeValue() {
		// given
		value = new BigDecimal(-1);
		Request request = Request.getBuilder(value, currency).localDate(date).build();

		// when
		BigDecimal result = exchanger.exchangeFromPln(request);

		// then
		assertEquals(result, null);
	}

	@Test
	public void shouldRetrunNull_whennRequestNullCurrency() {
		// given
		currency = null;
		Request request = Request.getBuilder(value, currency).localDate(date).build();

		// when
		BigDecimal result = exchanger.exchangeFromPln(request);

		// then
		assertEquals(result, null);
	}

	@Test
	public void shouldRetrunNull_whenNoParser() {
		// given
		Request request = Request.getBuilder(value, currency).localDate(date).build();
		exchanger = new Exchanger(null, new FileConnection("fileJson.txt"));

		// when
		BigDecimal result = exchanger.exchangeFromPln(request);

		// then
		assertEquals(result, null);
	}

	@Test
	public void shouldRetrunNull_whenNoResturnStringInput() {
		// given
		Request request = Request.getBuilder(value, currency).localDate(date).build();
		exchanger = new Exchanger(new JsonParser(), null);

		// when
		BigDecimal result = exchanger.exchangeFromPln(request);

		// then
		assertEquals(result, null);
	}
}

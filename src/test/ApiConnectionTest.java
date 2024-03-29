package test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.testng.annotations.Test;

import zadanie1.connectors.ApiConnection;
import zadanie1.enums.Currency;
import zadanie1.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie1.model.RateData;
import zadanie1.model.Request;
import zadanie1.parsers.apiParsers.ApiJsonParser;

public class ApiConnectionTest {
	@Test
	public void shouldCorrectRateFromDate_whenFindingApiRateData() throws ReadingRateDataException {
		// given
		LocalDate date = LocalDate.parse("2002-01-04");
		BigDecimal rate = new BigDecimal("3.9383");
		BigDecimal value = new BigDecimal("1");
		Currency currency = Currency.USD;
		RateData expected = new RateData(date, rate, currency);
		Request request = Request.getBuilder(value, currency).date(date).build();
		ApiConnection api = new ApiConnection(new ApiJsonParser());
		// when
		RateData result = api.getRateData(request);
		// then
		assertTrue(result.equals(expected));
	}

	@Test
	public void shouldReturnNull_whenFindingOlderRateData() throws ReadingRateDataException {
		// given
		LocalDate date = LocalDate.parse("2002-01-05");
		BigDecimal value = new BigDecimal("1");
		Currency currency = Currency.USD;

		Request request = Request.getBuilder(value, currency).date(date).build();
		ApiConnection api = new ApiConnection(new ApiJsonParser());
		// when
		RateData result = api.getRateData(request, date);
		// then
		assertEquals(result, null);
	}
}

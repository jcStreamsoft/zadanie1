package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.testng.annotations.Test;

import zadanie1.connectors.CachedConnection;
import zadanie1.enums.Currency;
import zadanie1.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie1.model.RateData;
import zadanie1.model.Request;

public class CachedConnectionTest {

	@Test
	public void shouldReturnTrue_whenFindingSavedRateData() throws ReadingRateDataException {
		// given
		LocalDate date = LocalDate.now();
		BigDecimal value = new BigDecimal("1");
		Currency currency = Currency.USD;
		RateData expected = new RateData(date, value, currency);
		Request request = Request.getBuilder(value, currency).date(date).build();
		CachedConnection cache = new CachedConnection();
		cache.saveData(expected);
		// when
		RateData result = cache.getRateData(request);

		// then
		assertEquals(result, expected);
	}

	@Test
	public void shouldReturnFalse_whenFindingRequestWithNewDate() throws ReadingRateDataException {
		// given
		LocalDate date = LocalDate.now();
		LocalDate newDate = LocalDate.now().minusDays(1);
		BigDecimal value = new BigDecimal("1");
		Currency currency = Currency.USD;
		RateData rateData = new RateData(date, value, currency);
		Request request = Request.getBuilder(value, currency).date(newDate).build();
		CachedConnection cache = new CachedConnection();
		cache.saveData(rateData);
		// when
		RateData result = cache.getRateData(request);

		// then
		assertNotEquals(result, rateData);
	}

	@Test
	public void shouldReturnFalse_whenFindingRequestWithNewCurrency() throws ReadingRateDataException {
		// given
		LocalDate date = LocalDate.now();
		BigDecimal value = new BigDecimal("1");
		Currency currency = Currency.USD;
		Currency newCurrency = Currency.EUR;
		RateData rateData = new RateData(date, value, currency);
		Request request = Request.getBuilder(value, newCurrency).date(date).build();
		CachedConnection cache = new CachedConnection();
		cache.saveData(rateData);
		// when
		RateData result = cache.getRateData(request);

		// then
		assertNotEquals(result, rateData);
	}

	@Test
	public void shouldReturnTrue_whenFindingOlderRateData() throws ReadingRateDataException {
		// given
		LocalDate date = LocalDate.now();
		LocalDate olderDate = LocalDate.now().minusDays(1);
		BigDecimal value = new BigDecimal("1");
		Currency currency = Currency.USD;
		RateData expected = new RateData(olderDate, value, currency);
		Request request = Request.getBuilder(value, currency).date(date).build();
		CachedConnection cache = new CachedConnection();
		cache.saveData(expected);
		// when
		RateData result = cache.getOlderRateData(request);

		// then
		assertEquals(result, expected);
	}
}

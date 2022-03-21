package test;

import static org.testng.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.testng.annotations.Test;

import zadanie1.connectors.FileConnection;
import zadanie1.enums.Currency;
import zadanie1.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie1.model.RateData;
import zadanie1.model.Request;
import zadanie1.parsers.fileParsers.FileJsonParser;

public class FileConnectionTest {

	@Test
	public void shouldReturnTrue_whenFindingApiRateData() throws ReadingRateDataException {
		// given
		LocalDate date = LocalDate.parse("2022-03-07");
		BigDecimal rate = new BigDecimal("4.5722");
		BigDecimal value = new BigDecimal("1");
		Currency currency = Currency.USD;
		RateData expected = new RateData(date, rate, currency);
		Request request = Request.getBuilder(value, currency).date(date).build();
		FileConnection file = new FileConnection(new FileJsonParser(), "fileArrayJson.txt");
		// when
		RateData result = file.getRateData(request);
		// then
		assertTrue(result.equals(expected));
	}

	@Test
	public void shouldReturnTheSameRateData_whenFindingOlderRateData() throws ReadingRateDataException {
		// given
		LocalDate date = LocalDate.parse("2022-03-06");
		LocalDate olderDate = LocalDate.parse("2022-03-04");
		BigDecimal value = new BigDecimal("1");
		BigDecimal rate = new BigDecimal("4.3910");
		Currency currency = Currency.USD;
		RateData expected = new RateData(olderDate, rate, currency);
		Request request = Request.getBuilder(value, currency).date(date).build();
		FileConnection file = new FileConnection(new FileJsonParser(), "fileArrayJson.txt");
		// when
		RateData result = file.getRateData(request, olderDate);
		// then
		assertTrue(result.equals(expected));
	}
}

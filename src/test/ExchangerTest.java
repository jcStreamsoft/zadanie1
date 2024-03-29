package test;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import zadanie1.Exchanger;
import zadanie1.connectors.ApiConnection;
import zadanie1.connectors.CachedConnection;
import zadanie1.connectors.FileConnection;
import zadanie1.enums.Currency;
import zadanie1.exceptions.ExchangerException;
import zadanie1.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie1.interfaces.DataConnection;
import zadanie1.model.RateData;
import zadanie1.model.Request;
import zadanie1.parsers.apiParsers.ApiJsonParser;
import zadanie1.parsers.fileParsers.FileJsonParser;

public class ExchangerTest {
	Exchanger exchanger;
	BigDecimal value;
	LocalDate date;
	Currency currency;

	@BeforeMethod
	public void setup() {
		List<DataConnection> connections = List.of(new ApiConnection(new ApiJsonParser()), new CachedConnection());
		value = new BigDecimal(1);
		date = LocalDate.now();
		currency = Currency.EUR;
		exchanger = new Exchanger(connections);

	}

	@Test
	public void shouldReturnRateData_firstDataConnectionContainsRate() throws ReadingRateDataException {
		// given
		ApiConnection spyApiCon = spy(new ApiConnection(new ApiJsonParser()));
		List<DataConnection> connections = List.of(spyApiCon,
				new FileConnection(new FileJsonParser(), "fileArrayJson.txt"));

		exchanger = new Exchanger(connections);
		Request request = Request.getBuilder(value, currency).date(LocalDate.parse("2002-01-04")).build();

		RateData expected = new RateData(LocalDate.parse("2002-01-04"), new BigDecimal("3.5346"), currency);
		// when
		RateData result = exchanger.findRate(request);
		// then
		verify(spyApiCon, times(1)).getRateData(request);
		assertEquals(result, expected);
	}

	@Test
	public void shouldReturnRateData_nextDataConnectionContainsRate() throws ReadingRateDataException {
		// given
		ApiConnection spyApiCon = spy(new ApiConnection(new ApiJsonParser()));
		FileConnection spyFileCon = spy(new FileConnection(new FileJsonParser(), "fileArrayJson.txt"));
		List<DataConnection> connections = List.of(spyFileCon, spyApiCon);
		exchanger = new Exchanger(connections);
		Request request = Request.getBuilder(value, currency).date(LocalDate.parse("2002-01-04")).build();
		RateData expected = new RateData(LocalDate.parse("2002-01-04"), new BigDecimal("3.5346"), currency);
		// when
		RateData result = exchanger.findRate(request);
		// then
		verify(spyApiCon, times(1)).getRateData(request);
		verify(spyFileCon, times(1)).getRateData(request);
		assertEquals(result, expected);
	}

	@Test
	public void shouldReturnLastRateData_firstDataConnectionContainsRate() throws ReadingRateDataException {
		// given
		ApiConnection spyApiCon = spy(new ApiConnection(new ApiJsonParser()));
		List<DataConnection> connections = List.of(spyApiCon,
				new FileConnection(new FileJsonParser(), "fileArrayJson.txt"));

		exchanger = new Exchanger(connections);
		LocalDate noRateDate = LocalDate.parse("2002-01-05");
		Request request = Request.getBuilder(value, currency).date(noRateDate).build();

		RateData expected = new RateData(LocalDate.parse("2002-01-04"), new BigDecimal("3.5346"), currency);
		// when
		RateData result = exchanger.findRate(request);
		// then
		verify(spyApiCon, times(1)).getRateData(request);
		verify(spyApiCon, times(1)).getRateData(request, LocalDate.parse("2002-01-04"));
		assertEquals(result, expected);
	}

	@Test
	public void shouldReturnLastRateData_nextDataConnectionContainsRate() throws ReadingRateDataException {
		// given
		ApiConnection spyApiCon = spy(new ApiConnection(new ApiJsonParser()));
		FileConnection spyFileCon = spy(new FileConnection(new FileJsonParser(), "fileArrayJson.txt"));
		List<DataConnection> connections = List.of(spyFileCon, spyApiCon);
		exchanger = new Exchanger(connections);
		Request request = Request.getBuilder(value, currency).date(LocalDate.parse("2002-01-05")).build();
		RateData expected = new RateData(LocalDate.parse("2002-01-04"), new BigDecimal("3.5346"), currency);
		// when
		RateData result = exchanger.findRate(request);
		// then
		verify(spyFileCon, times(1)).getRateData(request);
		verify(spyApiCon, times(1)).getRateData(request);
		verify(spyFileCon, times(1)).getRateData(request, LocalDate.parse("2002-01-04"));
		verify(spyApiCon, times(1)).getRateData(request, LocalDate.parse("2002-01-04"));
		assertEquals(result, expected);
	}

	@Test
	public void shouldNull_whenNoDataConnectionContains() throws ReadingRateDataException {
		// given

		FileConnection spyFileCon = spy(new FileConnection(new FileJsonParser(), "fileArrayJson.txt"));
		CachedConnection spyCacheCon = spy(new CachedConnection());
		List<DataConnection> connections = List.of(spyFileCon, spyCacheCon);
		exchanger = new Exchanger(connections);
		Request request = Request.getBuilder(value, currency).date(LocalDate.parse("2002-01-05")).build();
		// RateData expected = new RateData(LocalDate.parse("2002-01-04"), new
		// BigDecimal("3.5346"), currency);
		// when
		RateData result = exchanger.findRate(request);
		// then
		verify(spyFileCon, times(1)).getRateData(request);
		verify(spyCacheCon, times(1)).getRateData(request);
		verify(spyFileCon, times(1)).getRateData(request, LocalDate.parse("2002-01-04"));
		verify(spyCacheCon, times(1)).getRateData(request, LocalDate.parse("2002-01-04"));
		assertEquals(result, null);
	}

	@Test
	public void shouldReturnExpectedValue_whenInputIsCorrectOnExchangeToPln() {
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
	public void shouldReturnExpectedValue_whenInputIsCorrectOnExchangeFromPln() {
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

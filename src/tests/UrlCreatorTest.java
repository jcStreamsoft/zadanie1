package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import org.testng.annotations.Test;

import zadanie1.connectors.UrlCreator;
import zadanie1.enums.Currency;

import zadanie1.exceptions.streamInputExceptions.CreatingURLException;

public class UrlCreatorTest {

	@Test
	public void givenCorrectValues_whenCreateLastRateUrl_thenTrue() {
		UrlCreator creator = new UrlCreator (Currency.EUR,"json");
		LocalDate date = LocalDate.parse("2020-01-07");
		try {
			URL url = creator.createDateRateUrl(date);
			URL expected = new URL("http://api.nbp.pl/api/exchangerates/rates/a/eur/2020-01-07/?format=json");
			assertEquals(url,expected);
		} catch (MalformedURLException | CreatingURLException e) {
			fail("B³¹d przy tworzeniu linku");
		} 	
	}
	@Test
	public void givenCurrencyNull_whenCreateLastRateUrl_thenThrowsCreatingURLException() {
		UrlCreator creator = new UrlCreator (null,"json");
		LocalDate date = LocalDate.parse("2020-01-07"); 	
		assertThrows(CreatingURLException.class,() -> creator.createDateRateUrl(date));	
	}
	@Test
	public void givenResponseTypeNull_whenCreateLastRateUrl_thenThrowsCreatingURLException() {
		UrlCreator creator = new UrlCreator (Currency.EUR,null);
		LocalDate date = LocalDate.parse("2020-01-07");
		assertThrows(CreatingURLException.class,() -> creator.createDateRateUrl(date));	
	}
	@Test
	public void givenDateNullwhenCreateLastRateUrl_thenThrowsCreatingURLException() {
		UrlCreator creator = new UrlCreator (null,"json");
		LocalDate date = null;	
		assertThrows(CreatingURLException.class,() -> creator.createDateRateUrl(date));	
	}
	
}

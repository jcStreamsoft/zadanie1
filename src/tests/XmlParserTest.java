package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import zadanie1.exceptions.parserExceptions.ParsingException;
import zadanie1.exceptions.parserExceptions.ReadingCurrencyRateException;
import zadanie1.parsersApi.XmlParser;

public class XmlParserTest {

	XmlParser parser;

	@BeforeMethod
	public void setValidator() {
		parser = new XmlParser();
	}

	@Test
	public void givenCorrectStream_whenGetRateFromStream_thenBigDecimalEqual() {
		String testString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ExchangeRatesSeries xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Table>A</Table><Country>USA</Country><Symbol>787</Symbol><Code>USD</Code><Rates><Rate><No>3/A/NBP/2002</No><EffectiveDate>2002-01-04</EffectiveDate><Mid>3.9383</Mid></Rate></Rates></ExchangeRatesSeries>";
		InputStream inputStream = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		try {
			BigDecimal rate  = parser.getRateFromStream(inputStream);
			BigDecimal result =  new BigDecimal(3.9383).setScale(4, RoundingMode.CEILING);
			assertEquals(rate,result);
		} catch (Exception expected) {
			fail("B³¹d przy parsowaniu");
		}
	}
	@Test
	public void givenNotCorrectStream_whenGetRateFromStream_thenThrowsParsingException() {
		String testString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ExchangeRatesSeries xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Country>USA</CountrSymbol>787</Symbol><Code>USD</Code><Rates><Rate><No>3/A/NBP/2002</No><EffectiveDate>2002-01-04</EffectiveDate><Mid>3.9383</Mid></Rate></Rates></ExchangeRatesSeries>";
		InputStream inputStream = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		assertThrows(ParsingException.class,() -> parser.getRateFromStream(inputStream));	
	}
	@Test
	public void givenStreamMidNull_whenGetRateFromStream_thenThrowsParsingException() {
		String testString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ExchangeRatesSeries xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Table>A</Table><Country>USA</Country><Symbol>787</Symbol><Code>USD</Code><Rates><Rate><No>3/A/NBP/2002</No><EffectiveDate>2002-01-04</EffectiveDate><Mid></Mid></Rate></Rates></ExchangeRatesSeries>";
		InputStream inputStream = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		assertThrows(ParsingException.class,() -> parser.getRateFromStream(inputStream));	
	}
}

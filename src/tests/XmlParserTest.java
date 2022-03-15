package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import zadanie1.exceptions.parserExceptions.ParsingException;
import zadanie1.parsersApi.XmlParser;

public class XmlParserTest {

	XmlParser parser;

	@BeforeMethod
	public void setValidator() {
		parser = new XmlParser();
	}

	@Test
	public void givenCorrectStream_whenGetRateFromStream_thenBigDecimalEqual() throws ParsingException {
		// given
		String testString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ExchangeRatesSeries xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Table>A</Table><Country>USA</Country><Symbol>787</Symbol><Code>USD</Code><Rates><Rate><No>3/A/NBP/2002</No><EffectiveDate>2002-01-04</EffectiveDate><Mid>3.9383</Mid></Rate></Rates></ExchangeRatesSeries>";
		BigDecimal result = new BigDecimal(3.9383).setScale(4, RoundingMode.CEILING);
		// when
		BigDecimal rate = parser.getRateFromString(testString);
		// then
		assertEquals(rate, result);
	}

	@Test
	public void givenNotCorrectStream_whenGetRateFromStream_thenThrowsParsingException() {
		// given
		String testString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ExchangeRatesSeries xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Country>USA</CountrSymbol>787</Symbol><Code>USD</Code><Rates><Rate><No>3/A/NBP/2002</No><EffectiveDate>2002-01-04</EffectiveDate><Mid>3.9383</Mid></Rate></Rates></ExchangeRatesSeries>";
		// catch throw
		assertThrows(ParsingException.class, () -> parser.getRateFromString(testString));
	}

	@Test
	public void givenStreamMidNull_whenGetRateFromStream_thenThrowsParsingException() {
		// given
		String testString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ExchangeRatesSeries xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Table>A</Table><Country>USA</Country><Symbol>787</Symbol><Code>USD</Code><Rates><Rate><No>3/A/NBP/2002</No><EffectiveDate>2002-01-04</EffectiveDate><Mid></Mid></Rate></Rates></ExchangeRatesSeries>";
		// throw
		assertThrows(ParsingException.class, () -> parser.getRateFromString(testString));
	}
}

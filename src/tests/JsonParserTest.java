package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;
import static org.testng.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import zadanie1.enums.Currency;
import zadanie1.exceptions.parserExceptions.ParsingException;
import zadanie1.exceptions.parserExceptions.ReadingCurrencyRateException;
import zadanie1.model.Request;
import zadanie1.parsersApi.JsonParser;

public class JsonParserTest {

	JsonParser parser;

	@BeforeMethod
	public void setValidator() {
		parser = new JsonParser();
	}

	@Test
	public void givenCorrectStream_whenGetRateFromStream_thenBigDecimalEqual() {
		String testString = "{\"table\":\"A\",\"country\":\"USA\",\"symbol\":\"787\",\"code\":\"USD\",\"rates\":[{\"no\":\"3/A/NBP/2002\",\"effectiveDate\":\"2002-01-04\",\"mid\":3.9383}]}";
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
		String testString = "{\"table\":\"A\",:\"USA\",\"symbol\":\"787\",\"code\":\"USD\",\"rates\":[{\"no\":\"3/A/NBP/2002\",\"effectiveDate\":\"2002-01-04\",\"mid\":3.9383}]}";
		InputStream inputStream = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		assertThrows(ParsingException.class,() -> parser.getRateFromStream(inputStream));	
	}
	@Test
	public void givenStreamMidNull_whenGetRateFromStream_thenThrowsParsingException() {
		String testString = "{\"table\":\"A\",\"country\":\"USA\",\"symbol\":\"787\",\"code\":\"USD\",\"rates\":[{\"no\":\"3/A/NBP/2002\",\"effectiveDate\":\"2002-01-04\",\"mid\":}]}";
		InputStream inputStream = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		assertThrows(ParsingException.class,() -> parser.getRateFromStream(inputStream));	
	}
}

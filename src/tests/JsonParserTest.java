package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import zadanie1.exceptions.parserExceptions.ParsingException;
import zadanie1.parsersApi.JsonParser;

public class JsonParserTest {

	JsonParser parser;

	@BeforeMethod
	public void setValidator() {
		parser = new JsonParser();
	}

	@Test
	public void givenCorrectStream_whenGetRateFromStream_thenBigDecimalEqual() throws ParsingException {
		// given
		String testString = "{\"table\":\"A\",\"country\":\"USA\",\"symbol\":\"787\",\"code\":\"USD\",\"rates\":[{\"no\":\"3/A/NBP/2002\",\"effectiveDate\":\"2002-01-04\",\"mid\":3.9383}]}";
		InputStream inputStream = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		BigDecimal expectedResult = new BigDecimal(3.9383).setScale(4, RoundingMode.CEILING);

		// when
		BigDecimal rate = parser.getRateFromStream(inputStream);

		// then
		assertEquals(rate, expectedResult);

	}

	@Test
	public void shouldThrowException_whenJsonInputIsIncorrect() {
		// given
		String testString = "{\"table\":\"A\",:\"USA\",\"symbol\":\"787\",\"code\":\"USD\",\"rates\":[{\"no\":\"3/A/NBP/2002\",\"effectiveDate\":\"2002-01-04\",\"mid\":3.9383}]}";
		InputStream inputStream = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		// catch throws
		assertThrows(ParsingException.class, () -> parser.getRateFromStream(inputStream));
	}

	@Test
	public void givenStreamMidNull_whenGetRateFromStream_thenThrowsParsingException() {
		// given
		String testString = "{\"table\":\"A\",\"country\":\"USA\",\"symbol\":\"787\",\"code\":\"USD\",\"rates\":[{\"no\":\"3/A/NBP/2002\",\"effectiveDate\":\"2002-01-04\",\"mid\":}]}";
		InputStream inputStream = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		// catch throws
		assertThrows(ParsingException.class, () -> parser.getRateFromStream(inputStream));
	}
}

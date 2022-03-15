package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
	public void shouldReturnTrue_whenJsonStringCorrect() throws ParsingException {
		// given
		String correctJsonString = "{\"table\":\"A\",\"country\":\"USA\",\"symbol\":\"787\",\"code\":\"USD\",\"rates\":[{\"no\":\"3/A/NBP/2002\",\"effectiveDate\":\"2002-01-04\",\"mid\":3.9383}]}";
		BigDecimal expectedResult = new BigDecimal(3.9383).setScale(4, RoundingMode.CEILING);
		// when
		BigDecimal rate = parser.getRateFromString(correctJsonString);
		// then
		assertEquals(rate, expectedResult);
	}

	@Test
	public void shouldThrowException_whenJsonInputIsIncorrect() {
		// given
		String jsonIncorrectString = "{\"table\":\"A\",:\"USA\",\"symbol\":\"787\",\"code\":\"USD\",\"rates\":[{\"no\":\"3/A/NBP/2002\",\"effectiveDate\":\"2002-01-04\",\"mid\":3.9383}]}";
		// catch throws
		assertThrows(ParsingException.class, () -> parser.getRateFromString(jsonIncorrectString));
	}

	@Test
	public void shouldThrowParsingException_whenJsonStringMidValueNull() {
		// given
		String jsonStringMidValueNull = "{\"table\":\"A\",\"country\":\"USA\",\"symbol\":\"787\",\"code\":\"USD\",\"rates\":[{\"no\":\"3/A/NBP/2002\",\"effectiveDate\":\"2002-01-04\",\"mid\":}]}";
		// catch throws
		assertThrows(ParsingException.class, () -> parser.getRateFromString(jsonStringMidValueNull));
	}
}

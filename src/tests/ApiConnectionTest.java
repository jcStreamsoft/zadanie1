package tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import org.testng.annotations.Test;

import zadanie1.connectors.ApiConnection;
import zadanie1.enums.Currency;
import zadanie1.exceptions.streamInputExceptions.CreatingInputStreamException;
import zadanie1.model.Request;

public class ApiConnectionTest {
	
	
	@Test
	public void givenCorrectRequest_whenGetInputStream_thenReturnStream() {
		String testString = "{\"table\":\"A\",\"country\":\"USA\",\"symbol\":\"787\",\"code\":\"USD\",\"rates\":[{\"no\":\"3/A/NBP/2002\",\"effectiveDate\":\"2002-01-04\",\"mid\":3.9383}]}";
		InputStream expected = new ByteArrayInputStream(testString.getBytes(StandardCharsets.UTF_8));
		Request request = new Request(LocalDate.parse("2002-01-04"),new BigDecimal(1),Currency.EUR);
		ApiConnection con = new ApiConnection();
		InputStream result;
		try {
			result = con.getInputStream(request);
			assertEquals(result,expected);
		}catch(CreatingInputStreamException e) {
			fail("B³¹d odczytu InputStream");
		}
		
	}

}

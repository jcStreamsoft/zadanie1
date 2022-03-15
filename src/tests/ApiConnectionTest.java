package tests;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import org.testng.annotations.Test;

import zadanie1.connectors.ApiConnection;
import zadanie1.enums.Currency;
import zadanie1.exceptions.streamInputExceptions.CreatingInputStringException;
import zadanie1.model.Request;

public class ApiConnectionTest {

	@Test
	public void givenCorrectRequest_whenGetInputStream_thenReturnStream()
			throws CreatingInputStringException, IOException {
		// given
		File filePath = new File("fileJson.txt");
		Request request = new Request.Builder(new BigDecimal(1), Currency.USD).localDate(LocalDate.parse("2002-01-04"))
				.build();
		request.setDataFormat("json");
		ApiConnection con = new ApiConnection();
		InputStream result;
		InputStream expected = new FileInputStream(filePath);
		// when
		result = con.getInputStream(request);
		// then
		assertEquals(new String(result.readAllBytes(), StandardCharsets.UTF_8), new String(expected.readAllBytes()));
	}
}

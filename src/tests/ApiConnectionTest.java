package tests;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.testng.annotations.Test;

import zadanie1.connectors.ApiConnection;
import zadanie1.enums.Currency;
import zadanie1.exceptions.streamInputExceptions.CreatingInputStringException;
import zadanie1.model.Request;

public class ApiConnectionTest {

	@Test
	public void shouldReturnString_whenStringIsCorrect() throws CreatingInputStringException, IOException {
		// given
		File filePath = new File("fileJson.txt");
		Request request = new Request.Builder(new BigDecimal(1), Currency.USD).localDate(LocalDate.parse("2002-01-04"))
				.build();
		request.setDataFormat("json");
		ApiConnection con = new ApiConnection();
		InputStream expected = new FileInputStream(filePath);
		// when
		String result = con.getInputString(request);
		// then
		assertEquals(result, new String(expected.readAllBytes()));
	}
}

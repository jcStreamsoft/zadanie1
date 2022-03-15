package tests;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.testng.annotations.Test;

import zadanie1.connectors.FileConnection;
import zadanie1.enums.Currency;
import zadanie1.exceptions.streamInputExceptions.CreatingInputStringException;
import zadanie1.model.Request;

public class FileConnectionTest {
	@Test
	public void givenCorrectRequest_whenGetInputStream_thenReturnStream()
			throws CreatingInputStringException, IOException {
		// given
		File filePath = new File("fileJson.txt");
		Request request = new Request.Builder(new BigDecimal(1), Currency.USD).localDate(LocalDate.parse("2002-01-04"))
				.build();
		request.setFilePath("fileJson.txt");
		FileConnection con = new FileConnection();

		InputStream expected = new FileInputStream(filePath);
		// when

		InputStream result = con.getInputStream(request);

		// then
		assertEquals(result.readAllBytes(), expected.readAllBytes());
	}
}

package tests;

import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.stream.Collectors;

import org.testng.annotations.Test;

import zadanie1.connectors.FileConnection;
import zadanie1.enums.Currency;
import zadanie1.exceptions.streamInputExceptions.CreatingInputStringException;
import zadanie1.model.Request;

public class FileConnectionTest {
	@Test
	public void shouldReturnTrue_whenReadingStringInput() throws CreatingInputStringException, IOException {
		// given
		File filePath = new File("fileJson.txt");
		Request request = new Request.Builder(new BigDecimal(1), Currency.USD).localDate(LocalDate.parse("2002-01-04"))
				.build();
		FileConnection con = new FileConnection("fileJson.txt");
		String expected = new BufferedReader(
				new InputStreamReader(new FileInputStream("fileJson.txt"), StandardCharsets.UTF_8)).lines()
						.collect(Collectors.joining());

		// when
		String result = con.getInputString(request);

		// then
		assertEquals(result, expected);
	}
}

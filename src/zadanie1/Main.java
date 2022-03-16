package zadanie1;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import zadanie1.connectors.ApiConnection;
import zadanie1.connectors.FileConnection;
import zadanie1.enums.Currency;
import zadanie1.model.Request;
import zadanie1.parsersApi.JsonParser;
import zadanie1.parsersApi.XmlParser;

public class Main {
	public static void main(String[] args) throws IOException {
		test1();

	}

	public static void test1() throws IOException {
		BigDecimal value = new BigDecimal(2);
		LocalDate date = LocalDate.parse("2002-01-04");

		Exchanger nbp = new Exchanger(new JsonParser(), new ApiConnection());
		Request request = Request.getBuilder(value, Currency.EUR).localDate(date).build();

		BigDecimal result = nbp.exchangeFromPln(request);
		System.out.println(result);
		Exchanger nbp1 = new Exchanger(new XmlParser(), new FileConnection("fileXml.txt"));

		BigDecimal result2 = nbp1.exchangeToPln(request);
		System.out.println(result2);
	}
}
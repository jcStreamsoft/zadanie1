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
		BigDecimal value = new BigDecimal(123.240);
		LocalDate date = LocalDate.parse("2002-01-02");

		Exchanger nbp = new Exchanger(new JsonParser(),new CurrencyCalculator(),new FileConnection());
		Request request = new Request(date,value,Currency.EUR);
		request.setFilePath("fileJson.txt");
		BigDecimal result = nbp.exchangeToPln(request);
		System.out.println(result);
		Exchanger nbp1 = new Exchanger(new XmlParser(),new CurrencyCalculator(),new FileConnection());
		request.setFilePath("fileXml.txt");
		BigDecimal result2 = nbp1.exchangeToPln(request);
		System.out.println(result2);
	}
}
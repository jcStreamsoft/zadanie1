package zadanie1;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import zadanie1.connectors.ApiConnection;
import zadanie1.enums.Currency;
import zadanie1.model.Request;
import zadanie1.parsersApi.XmlParser;
import zadanie1.parsersApi.XmlParser;

public class Main {
	public static void main(String[] args) throws IOException {
		test1();
	}

	public static void test1() throws IOException {
		BigDecimal value = new BigDecimal(123.240);
		LocalDate date = LocalDate.parse("2022-03-06");

		Exchanger nbp = new Exchanger(new XmlParser(),new CurrencyCalculator(),new ApiConnection());

		BigDecimal result = nbp.exchangeToPln(new Request(date,value,Currency.EUR));
		System.out.println(result);
		BigDecimal result2 = nbp.exchangeFromPln(new Request(date,value,Currency.EUR));
		System.out.println(result2);
	}
}
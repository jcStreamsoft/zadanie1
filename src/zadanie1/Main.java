package zadanie1;


import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import zadanie1.enums.Currency;
import zadanie1.enums.ResponseType;
import zadanie1.parsers.XmlParser;

public class Main {
	public static void main(String[] args) throws IOException  {
		test1();
	}
	
	public static void test1() {
		BigDecimal value = new BigDecimal(123.24);
		CurrencyCalculator nbp = new CurrencyCalculator(new XmlParser());
		BigDecimal result = nbp.calculateToPln(value, Currency.EUR, LocalDate.parse("2022-03-06"));
		System.out.println(result );
		BigDecimal result2 = nbp.calculateFromPln(value, Currency.EUR);
		System.out.println( result2);	
	}
}
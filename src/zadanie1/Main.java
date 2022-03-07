package zadanie1;


import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import zadanie1.enums.Currency;
import zadanie1.enums.ResponseType;

public class Main {
	public static void main(String[] args) throws IOException  {
		test1();
	}
	
	public static void test1() {
		BigDecimal value = new BigDecimal(123.24);
		NbpCurrencyCalculator nbp = new NbpCurrencyCalculator();
		BigDecimal result = nbp.calculateFromPln(value, Currency.EUR, ResponseType.JSON, LocalDate.parse("2015-01-09"));
		System.out.println(result );
		BigDecimal result2 = nbp.calculateFromPln(value, Currency.EUR, ResponseType.XML);
		System.out.println( result2);	
	}
}
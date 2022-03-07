package zadanie1;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import zadanie1.enums.Currency;
import zadanie1.enums.ResponseType;

public class Main {
	public static void main(String[] args)throws IOException, ParserConfigurationException, SAXException  {
		BigDecimal value = new BigDecimal(123.24);
		NbpCurrencyCalculator npb = new NbpCurrencyCalculator();
		BigDecimal result = npb.calculateFromPln(value, Currency.EUR, ResponseType.JSON, LocalDate.parse("2002-03-07"));
		System.out.println(result );
		BigDecimal result2 = npb.calculateFromPln(value, Currency.EUR, ResponseType.XML);
		System.out.println( result2);	
	}
}
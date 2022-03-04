package zadanie1;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import zadanie1.enums.Currency;
import zadanie1.enums.ResponseType;
import zadanie1.exceptions.CurrencyNotFoundException;

public class Main {

	public static void main(String[] args) throws IOException, CurrencyNotFoundException {
		BigDecimal value = new BigDecimal(123.24);
		NbpCurrencyCalculator npb = new NbpCurrencyCalculator();
		BigDecimal result = npb.calculateFromPln(value, Currency.EUR, ResponseType.JSON);
		BigDecimal result2 = npb.calculateToPln(value, Currency.EUR, ResponseType.JSON);
		System.out.println(result + " " + result2);

		CurrencyCalculator cc = new CurrencyCalculator.Builder()
				.value(new BigDecimal(0))
				.time(LocalDate.now())
				.currency(Currency.EUR).build();
	}

}
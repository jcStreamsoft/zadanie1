package zadanie1;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import zadanie1.model.Currency;

public class Main {

	public static void main(String[] args) throws IOException, CurrencyNotFoundException {
		BigDecimal value = new BigDecimal(123.24);
		NbpCurrencyCalculator npb = new NbpCurrencyCalculator();
		BigDecimal result = npb.calculateCurrency(value, "usd", "pln");
		System.out.println(result);

		CurrencyCalculator cc = new CurrencyCalculator.Builder()
				.value(new BigDecimal(0))
				.time(LocalDate.now())
				.currency(Currency.EUR).build();
	}

}
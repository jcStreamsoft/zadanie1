package zadanie1;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import zadanie1.enums.Currency;
import zadanie1.interfaces.Calculator;
import zadanie1.interfaces.NbpApiParser;
import zadanie1.parsersApi.ApiParser;

public class CurrencyCalculator implements Calculator{
	
	public BigDecimal calculateToPln(BigDecimal value, BigDecimal rate) {
		return value.divide(rate, RoundingMode.CEILING);
	}

	public BigDecimal calculateFromPln(BigDecimal value, BigDecimal rate) {
		return value.multiply(rate);
	}
}

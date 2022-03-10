package zadanie1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import zadanie1.interfaces.Calculator;

public class CurrencyCalculator implements Calculator {

	public BigDecimal calculateToPln(BigDecimal value, BigDecimal rate) {
		return value.divide(rate, RoundingMode.CEILING);
	}

	public BigDecimal calculateFromPln(BigDecimal value, BigDecimal rate) {
		return value.multiply(rate);
	}
}

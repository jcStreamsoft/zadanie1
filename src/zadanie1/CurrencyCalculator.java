package zadanie1;

import java.math.BigDecimal;
import java.math.MathContext;
import zadanie1.interfaces.Calculator;

public class CurrencyCalculator implements Calculator {

	public BigDecimal calculateToPln(BigDecimal value, BigDecimal rate) {
		MathContext m = new MathContext(25);
		BigDecimal result = value.divide(rate,m);
		return result;
	}

	public BigDecimal calculateFromPln(BigDecimal value, BigDecimal rate) {
		BigDecimal result = value.multiply(rate);
		return result;
	}
}

package tests;

import static org.testng.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.MathContext;

import org.testng.annotations.Test;

import zadanie1.CurrencyCalculator;
import zadanie1.interfaces.Calculator;

public class CurrencyCalculatorTest {

	@Test
	public void givenValueRate_whenCalculateFromPln_thenCorrectResult() {
		// given
		Calculator calc = new CurrencyCalculator();
		BigDecimal value = new BigDecimal(2);
		BigDecimal rate = new BigDecimal(3.5346);
		MathContext m = new MathContext(25);
		// when
		BigDecimal result = calc.calculateFromPln(value,rate);
		result = result.round(m);

		// then
		BigDecimal expected = new BigDecimal(7.0692);
		expected = expected.round(m);
		assertEquals(result, expected);
	}
	@Test
	public void givenValueRate_whenCalculateToPln_thenCorrectResult() {
		// given
		Calculator calc = new CurrencyCalculator();
		BigDecimal value = new BigDecimal(2);
		BigDecimal rate = new BigDecimal(3.5346);
		MathContext m = new MathContext(1);
		// when
		BigDecimal result = calc.calculateToPln(value,rate);
		result = result.round(m);

		// then
		BigDecimal expected = new BigDecimal(0.5658348893792791);
		expected = expected.round(m);
		assertEquals(result, expected);
	}
}

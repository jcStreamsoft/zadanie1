package zadanie1.interfaces;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface Calculator {
	public BigDecimal calculateToPln(BigDecimal value, BigDecimal rate);

	public BigDecimal calculateFromPln(BigDecimal value, BigDecimal rate);
}

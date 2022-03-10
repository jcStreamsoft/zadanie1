package zadanie1.interfaces;

import java.math.BigDecimal;

public interface Calculator {

	public BigDecimal calculateToPln(BigDecimal value, BigDecimal rate);

	public BigDecimal calculateFromPln(BigDecimal value, BigDecimal rate);
}

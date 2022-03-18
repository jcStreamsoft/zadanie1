package tests;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.testng.annotations.Test;

import zadanie1.enums.Currency;
import zadanie1.model.Request;

public class Testmethod {

	@Test
	public void test() {
		Request request = Request.getBuilder(new BigDecimal(1), Currency.USD).date(LocalDate.now()).build();

		LocalDate dateMinusOne = request.getDate().minusDays(1);
		LocalDate dateNow = request.getDate();
		// assertEquals(dateNow, dateMinusOne);
	}
}

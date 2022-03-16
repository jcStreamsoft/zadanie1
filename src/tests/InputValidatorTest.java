package tests;

import static org.testng.Assert.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import zadanie1.InputValidator;
import zadanie1.enums.Currency;
import zadanie1.exceptions.inputExceptions.DateAfterTodayException;
import zadanie1.exceptions.inputExceptions.DateBeforeFirstException;
import zadanie1.exceptions.inputExceptions.NegativeValueException;
import zadanie1.model.Request;

public class InputValidatorTest {
	InputValidator validator;

	@BeforeMethod
	public void setValidator() {
		validator = new InputValidator();
	}

	@Test
	public void shouldThrowNegativeValueException_whenValueNegative() {
		// given
		Request request = Request.getBuilder(new BigDecimal(-1), Currency.EUR).localDate(LocalDate.now()).build();
		// throws
		assertThrows(NegativeValueException.class, () -> validator.checkValue(request.getValue()));
	}

	@Test
	public void shouldThrowsDateAfterTodayException_whenDateAfterToday() {
		// given
		Request request = Request.getBuilder(new BigDecimal(1), Currency.EUR).localDate(LocalDate.now().plusDays(1))
				.build();
		// throws
		assertThrows(DateAfterTodayException.class, () -> validator.checkDate(request.getLocalDate()));
	}

	@Test
	public void shouldThrowDateBeforeFirstException_whenDateBeforeFirst() {
		// given
		Request request = Request.getBuilder(new BigDecimal(1), Currency.EUR).localDate(LocalDate.parse("2002-01-01"))
				.build();
		// throws
		assertThrows(DateBeforeFirstException.class, () -> validator.checkDate(request.getLocalDate()));
	}

}

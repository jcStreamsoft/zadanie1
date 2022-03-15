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
import zadanie1.exceptions.inputExceptions.InputValueNullException;
import zadanie1.exceptions.inputExceptions.NegativeValueException;
import zadanie1.model.Request;

public class InputValidatorTest {
	InputValidator validator;

	@BeforeMethod
	public void setValidator() {
		validator = new InputValidator();
	}

	@Test
	public void shouldThrowInputValueNullException_whenValueNull() {
		assertThrows(InputValueNullException.class,() -> validator.validate(
				Request.getBuilder(null, Currency.EUR).localDate(LocalDate.now()).build()));
	}

	@Test
	public void shouldThrowNegativeValueException_whenValueNegative() {
		assertThrows(NegativeValueException.class, () -> validator.validate(
				Request.getBuilder(new BigDecimal(-1), Currency.EUR).localDate(LocalDate.now()).build()));
	}

	@Test
	public void shouldThrowsDateAfterTodayException_whenDateAfterToday() {
		assertThrows(DateAfterTodayException.class, () -> validator.validate(
				Request.getBuilder(new BigDecimal(1), Currency.EUR).localDate(LocalDate.now().plusDays(1)).build()));
	}

	@Test
	public void shouldThrowDateBeforeFirstException_whenDateBeforeFirst(){
		assertThrows(DateBeforeFirstException.class, () -> validator.validate(
				Request.getBuilder(new BigDecimal(1), Currency.EUR).localDate(LocalDate.parse("2002-01-01")).build()));
	}

	@Test
	public void shouldThrowInputValueNullException_whenCurrencyNull() {
		assertThrows(InputValueNullException.class, () -> validator.validate(
				Request.getBuilder(new BigDecimal(1), null).localDate(LocalDate.now()).build()));
	}
}

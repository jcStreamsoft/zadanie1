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
	public void givenValueNull_whenValidate_thenThrowsInputValueNullException() {
		assertThrows(InputValueNullException.class,() -> validator.validate( new Request
				.Builder(null, Currency.EUR)
				.localDate(LocalDate.now())
				.build()));	
	}
	@Test
	public void givenValueNegative_whenValidate_thenThrowsNegativeValueException() {
		assertThrows(NegativeValueException.class,() -> validator.validate(new Request
				.Builder(new BigDecimal(-1), Currency.EUR)
				.localDate(LocalDate.now())
				.build()));	
	}
	@Test
	public void givenDateAfterToday_whenValidate_thenThrowsDateAfterTodayException() {
		assertThrows(DateAfterTodayException.class,() -> validator.validate(new Request
				.Builder(new BigDecimal(1), Currency.EUR)
				.localDate(LocalDate.now().plusDays(1))
				.build()));
	}
	@Test
	public void givenDateBeforeFirst_whenValidate_thenThrowsDateBeforeFirstException() {
		assertThrows(DateBeforeFirstException.class,() -> validator.validate(new Request
				.Builder(new BigDecimal(1), Currency.EUR)
				.localDate(LocalDate.parse("2002-01-01"))
				.build()));	
	}
//	@Test
//	public void givenDateNull_whenValidate_thenThrowsInputValueNullException() {
//		assertThrows(InputValueNullException.class,() -> validator.validate(new Request
//				.Builder(new BigDecimal(-1), Currency.EUR)
//				.localDate(LocalDate.now())
//				.build()));	
//	}
	@Test
	public void givenCurrencyNull_whenValidate_thenThrowsInputValueNullException() {
		assertThrows(InputValueNullException.class,() -> validator.validate( new Request
				.Builder(new BigDecimal(1), null)
				.localDate(LocalDate.now())
				.build()));
	}
}

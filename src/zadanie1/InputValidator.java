package zadanie1;

import java.math.BigDecimal;
import java.time.LocalDate;

import zadanie1.enums.Currency;
import zadanie1.exceptions.inputExceptions.DateAfterTodayException;
import zadanie1.exceptions.inputExceptions.DateBeforeFirstException;
import zadanie1.exceptions.inputExceptions.NegativeValueException;

public class InputValidator {

	public void checkValue(BigDecimal value) throws NegativeValueException {
		int result = value.compareTo(new BigDecimal(0));
		if (result == -1) {
			throw new NegativeValueException();
		}
	}

	public void checkDate(LocalDate localDate) throws DateBeforeFirstException, DateAfterTodayException {
		LocalDate today = LocalDate.now();
		LocalDate firstDate = LocalDate.parse("2002-01-02");
		if (localDate.isBefore(firstDate)) {
			throw new DateBeforeFirstException();
		} else if (localDate.isAfter(today)) {
			throw new DateAfterTodayException();
		}
	}

	public void checkCurrency(Currency currency) {
		if (currency == null) {
			throw new NullPointerException();
		}
	}
}

package zadanie1;

import java.math.BigDecimal;
import java.time.LocalDate;

import zadanie1.enums.Currency;
import zadanie1.exceptions.inputExceptions.DateAfterTodayException;
import zadanie1.exceptions.inputExceptions.DateBeforeFirstException;
import zadanie1.exceptions.inputExceptions.InputValueNullException;
import zadanie1.exceptions.inputExceptions.NegativeValueException;

public class InputValidator {

	private final static LocalDate MIN_DATE = LocalDate.parse("2002-01-02");

	public static void checkValue(BigDecimal value) throws NegativeValueException, InputValueNullException {
		if (value == null) {
			throw new InputValueNullException("Wartość do przeliczenia nie może być NULL");
		}
		int result = value.compareTo(new BigDecimal(0));
		if (result == -1) {
			throw new NegativeValueException("Wartość do przeliczenia musi być wieksza od 0.");
		}
	}

	public static void checkDate(LocalDate localDate)
			throws DateBeforeFirstException, DateAfterTodayException, InputValueNullException {
		LocalDate today = LocalDate.now();

		if (localDate == null) {
			throw new InputValueNullException("LocalDate nie może być NULL");
		} else if (localDate.isBefore(MIN_DATE)) {
			throw new DateBeforeFirstException("Data nie może być wcześniejsza niż " + MIN_DATE);
		} else if (localDate.isAfter(today)) {
			throw new DateAfterTodayException("Data nie może byæ pózniejsza niż dzisaj.");
		}
	}

	public static void checkCurrency(Currency currency) throws InputValueNullException {
		if (currency == null) {
			throw new InputValueNullException("Typ Currency nie może być NULL");
		}
	}
}
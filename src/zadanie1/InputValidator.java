package zadanie1;

import java.math.BigDecimal;
import java.time.LocalDate;

import zadanie1.enums.Currency;
import zadanie1.exceptions.inputExceptions.DateAfterTodayException;
import zadanie1.exceptions.inputExceptions.DateBeforeFirstException;
import zadanie1.exceptions.inputExceptions.InputValueNullException;
import zadanie1.exceptions.inputExceptions.NegativeValueException;
import zadanie1.exceptions.inputExceptions.NoCurrencyTypeException;
import zadanie1.model.Request;

public class InputValidator {
	
	public void validate(Request request)
			throws NegativeValueException, DateBeforeFirstException, DateAfterTodayException, InputValueNullException {
		checkValue(request.getValue());
		checkDate(request.getLocalDate());
		checkCurrency(request.getCurrency());
	}

	private void checkValue(BigDecimal value) throws NegativeValueException, InputValueNullException {
		if(value == null) {
			throw new InputValueNullException("Watoœæ do przeliczenia nie mo¿e byæ NULL");
		}
		int result = value.compareTo(new BigDecimal(0));
		if (result == -1) {
			throw new NegativeValueException("Wartoœæ do przeliczenia musi byæ wieksza od 0.");
		}
	}

	private void checkDate(LocalDate localDate) throws DateBeforeFirstException, DateAfterTodayException, InputValueNullException {
		LocalDate today = LocalDate.now();
		LocalDate firstDate = LocalDate.parse("2002-01-02");
		if(localDate == null) {
			throw new InputValueNullException("LocalDate nie mo¿e byæ NULL");
		}else if (localDate.isBefore(firstDate)) {
			throw new DateBeforeFirstException("Data nie mo¿e byæ wczeœniejsza ni¿ " + firstDate);
		} else if (localDate.isAfter(today)) {
			throw new DateAfterTodayException("Data nie mo¿e byæ poŸniejsza ni¿ dzisaj.");
		}
	}

	private void checkCurrency(Currency currency) throws InputValueNullException {
		if (currency == null) {
			throw new InputValueNullException("Typ Currency nie mo¿e byæ NULL");
		}
	}
}

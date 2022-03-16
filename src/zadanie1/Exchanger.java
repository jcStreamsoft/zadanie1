package zadanie1;

import java.math.BigDecimal;

import zadanie1.exceptions.ExchangerException;
import zadanie1.exceptions.inputExceptions.DateAfterTodayException;
import zadanie1.exceptions.inputExceptions.DateBeforeFirstException;
import zadanie1.exceptions.inputExceptions.InputValueNullException;
import zadanie1.exceptions.inputExceptions.NegativeValueException;
import zadanie1.interfaces.InputConnection;
import zadanie1.interfaces.Parse;
import zadanie1.model.Request;

public class Exchanger {
	private InputConnection streamConnection;
	private Parse parser;
	private CurrencyCalculator currencyCalc;
	private InputValidator validator;

	public Exchanger(Parse parser, InputConnection streamConnection) {
		this.currencyCalc = new CurrencyCalculator();
		this.streamConnection = streamConnection;
		this.parser = parser;
		this.validator = new InputValidator();
	}

	public BigDecimal exchangeToPln(Request request) {
		try {
			checkValidation(request);

			request.setDataFormat(parser.getFormatType());
			String inputString = streamConnection.getInputString(request);
			BigDecimal rate = parser.getRateFromString(inputString);
			BigDecimal value = request.getValue();

			return currencyCalc.calculateToPln(value, rate);
		} catch (Exception e) {
			throw new ExchangerException("Wyst¹pi³ b³¹d.", e);
		}
	}

	public BigDecimal exchangeFromPln(Request request) {
		try {
			checkValidation(request);

			request.setDataFormat(parser.getFormatType());
			String inputString = streamConnection.getInputString(request);
			BigDecimal rate = parser.getRateFromString(inputString);
			BigDecimal value = request.getValue();

			return currencyCalc.calculateFromPln(value, rate);
		} catch (Exception e) {
			throw new ExchangerException("Wyst¹pi³ b³¹d.", e);
		}
	}

	private void checkValidation(Request request)
			throws NegativeValueException, InputValueNullException, DateBeforeFirstException, DateAfterTodayException {
		validator.checkDate(request.getLocalDate());
		validator.checkValue(request.getValue());
		validator.checkCurrency(request.getCurrency());
	}

}

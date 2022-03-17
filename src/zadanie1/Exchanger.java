package zadanie1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import zadanie1.exceptions.ExchangerException;
import zadanie1.exceptions.inputExceptions.DateAfterTodayException;
import zadanie1.exceptions.inputExceptions.DateBeforeFirstException;
import zadanie1.exceptions.inputExceptions.InputValueNullException;
import zadanie1.exceptions.inputExceptions.NegativeValueException;
import zadanie1.interfaces.DataConnection;
import zadanie1.interfaces.Parse;
import zadanie1.model.Request;

public class Exchanger {
	private DataConnection dataConnection;
	private Parse parser;
	private CurrencyCalculator currencyCalc;

	public Exchanger(Parse parser, DataConnection dataConnection) {
		this.currencyCalc = new CurrencyCalculator();
		this.dataConnection = dataConnection;
		this.parser = parser;
	}

	private BigDecimal findDataConnection() {
		List<DataConnection> lista = new ArrayList<>();
		for (DataConnection d : lista) {
			this.dataConnection = d;
			// data find specific LocalDate
			exchangeToPln(null);
		}

		return null;
	}

	public BigDecimal exchangeToPln(Request request) {
		try {
			checkValidation(request);

			request.setDataFormat(parser.getFormatType());
			String inputString = dataConnection.getInputString(request);
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
			String inputString = dataConnection.getInputString(request);
			BigDecimal rate = parser.getRateFromString(inputString);
			BigDecimal value = request.getValue();

			return currencyCalc.calculateFromPln(value, rate);
		} catch (Exception e) {
			throw new ExchangerException("Wyst¹pi³ b³¹d.", e);
		}
	}

	private void checkValidation(Request request)
			throws NegativeValueException, InputValueNullException, DateBeforeFirstException, DateAfterTodayException {
		InputValidator.checkDate(request.getLocalDate());
		InputValidator.checkValue(request.getValue());
		InputValidator.checkCurrency(request.getCurrency());
	}

}

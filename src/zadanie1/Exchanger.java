package zadanie1;

import java.math.BigDecimal;

import zadanie1.interfaces.Parse;
import zadanie1.interfaces.ReturnInputString;
import zadanie1.model.Request;

public class Exchanger {
	private ReturnInputString streamConnection;
	private Parse parser;
	private CurrencyCalculator currencyCalc;
	private InputValidator validator;

	public Exchanger(Parse parser, ReturnInputString streamConnection) {
		this.currencyCalc = new CurrencyCalculator();
		this.streamConnection = streamConnection;
		this.parser = parser;
		this.validator = new InputValidator();
	}

	public BigDecimal exchangeToPln(Request request) {
		try {
			validator.validate(request);
			request.setDataFormat(parser.getFormatType());
			String inputString = streamConnection.getInputString(request);
			BigDecimal rate = parser.getRateFromString(inputString);
			BigDecimal value = request.getValue();
			System.out.println(value + " rate =" + rate);
			return currencyCalc.calculateToPln(value, rate);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public BigDecimal exchangeFromPln(Request request) {
		try {
			validator.validate(request);
			request.setDataFormat(parser.getFormatType());
			String inputString = streamConnection.getInputString(request);
			BigDecimal rate = parser.getRateFromString(inputString);
			BigDecimal value = request.getValue();
			System.out.println(value + " rate =" + rate);
			return currencyCalc.calculateFromPln(value, rate);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

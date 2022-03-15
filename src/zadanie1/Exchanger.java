package zadanie1;

import java.math.BigDecimal;
import java.util.logging.Logger;

import zadanie1.interfaces.Parse;
import zadanie1.interfaces.ReturnInputString;
import zadanie1.model.Request;

public class Exchanger {
	private static final Logger LOG = Logger.getLogger(Exchanger.class.getName());
	private ReturnInputString streamConnection;
	private Parse parser;
	private CurrencyCalculator currencyCalc;
	private InputValidator validator;

	public Exchanger(Parse parser, CurrencyCalculator calculator, ReturnInputString streamConnection) {
		this.currencyCalc = calculator;
		this.streamConnection = streamConnection;
		this.parser = parser;
		this.validator = new InputValidator();
	}

	public BigDecimal exchangeToPln(Request request) {
		try {
			validator.validate(request);
			request.setDataFormat(parser.getFormatType());
			String inputString = streamConnection.getInputString(request);
			BigDecimal rate = parser.getRateFromStream(inputString);
			BigDecimal value = request.getValue();

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
			BigDecimal rate = parser.getRateFromStream(inputString);
			BigDecimal value = request.getValue();

			return currencyCalc.calculateFromPln(value, rate);
		} catch (Exception e) {
			e.printStackTrace();
			// LOG.log(Level.WARNING, "{0}", e.toString());
			return null;
		}
	}

}

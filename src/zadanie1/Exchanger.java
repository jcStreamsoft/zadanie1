package zadanie1;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

import zadanie1.interfaces.Calculator;
import zadanie1.interfaces.NbpApiParser;
import zadanie1.interfaces.Streams;
import zadanie1.model.Request;

public class Exchanger {
	private static final Logger LOG = Logger.getLogger(Exchanger.class.getName());
	private Streams streamConnection;
	private NbpApiParser parser;
	private Calculator currencyCalc;
	private InputValidator validator;

	public Exchanger(NbpApiParser parser, Calculator calculator, Streams streamConnection) {
		this.currencyCalc = calculator;
		this.streamConnection = streamConnection;
		this.parser = parser;
		this.validator = new InputValidator();
	}

	public BigDecimal exchangeToPln(Request request) {
		try {
			LOG.log(Level.INFO, "Walidowanie danych");
			validator.validate(request);
			request.setDataFormat(parser.getFormatType());
			LOG.log(Level.INFO, "Tworzenie InputStreama");
			InputStream stream = streamConnection.getInputStream(request);
			LOG.log(Level.INFO, "Parsowanie InputStreama");
			BigDecimal rate = parser.getRateFromStream(stream);
			BigDecimal value = request.getValue();
			LOG.log(Level.INFO, "Zamykanie InputStreama");
			streamConnection.close();
			LOG.log(Level.INFO, "Przeliczanie kursu");
			System.out.println("value = "+ value + " rate = " +rate);
			return currencyCalc.calculateToPln(value, rate);
		} catch (Exception e) {
			LOG.log(Level.WARNING, "{0}", e.toString());
			return null;
		}
	}

	public BigDecimal exchangeFromPln(Request request) {
		try {
			LOG.log(Level.INFO, "Walidowanie danych");
			validator.validate(request);
			request.setDataFormat(parser.getFormatType());
			LOG.log(Level.INFO, "Tworzenie InputStreama");
			InputStream stream = streamConnection.getInputStream(request);
			LOG.log(Level.INFO, "Parsowanie InputStreama");
			BigDecimal rate = parser.getRateFromStream(stream);
			BigDecimal value = request.getValue();
			LOG.log(Level.INFO, "Zamykanie InputStreama");
			streamConnection.close();
			LOG.log(Level.INFO, "Przeliczanie kursu ");
			return currencyCalc.calculateFromPln(value, rate);
		} catch (Exception e) {
			LOG.log(Level.WARNING, "{0}", e.toString());
			return null;
		}
	}

}

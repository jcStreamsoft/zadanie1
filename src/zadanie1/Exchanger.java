package zadanie1;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import zadanie1.interfaces.Calculator;
import zadanie1.interfaces.NbpApiParser;
import zadanie1.interfaces.Streams;
import zadanie1.model.Request;

public class Exchanger {
	private Streams streamConnection;
	private NbpApiParser parser;
	private Calculator currencyCalc;

	public Exchanger(NbpApiParser parser, Calculator calculator, Streams streamConnection) {
		this.currencyCalc = calculator;
		this.streamConnection = streamConnection;
		this.parser = parser;
	}

	public BigDecimal exchangeToPln(Request request) throws IOException {
		request.setDataFormat(parser.getFormatType());
		InputStream stream = streamConnection.getInputStream(request);
		BigDecimal rate = parser.getRateFromStream(stream);
		BigDecimal value = request.getValue();
		streamConnection.close();
		System.out.println(rate + " " + value);
		return currencyCalc.calculateFromPln(value, rate);
	}

	public BigDecimal exchangeFromPln(Request request) throws IOException {
		request.setDataFormat(parser.getFormatType());
		InputStream stream = streamConnection.getInputStream(request);
		BigDecimal rate = parser.getRateFromStream(stream);
		BigDecimal value = request.getValue();
		streamConnection.close();
		System.out.println(rate + " " + value);
		return currencyCalc.calculateToPln(value, rate);
	}

}

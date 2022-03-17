package zadanie1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import zadanie1.connectors.CachedConnection;
import zadanie1.exceptions.ExchangerException;
import zadanie1.exceptions.inputExceptions.DateAfterTodayException;
import zadanie1.exceptions.inputExceptions.DateBeforeFirstException;
import zadanie1.exceptions.inputExceptions.InputValueNullException;
import zadanie1.exceptions.inputExceptions.NegativeValueException;
import zadanie1.interfaces.DataConnection;
import zadanie1.model.RateData;
import zadanie1.model.Request;

public class Exchanger {
	private DataConnection dataConnection;
	private CurrencyCalculator currencyCalc;
	private static CachedConnection cache;

	public Exchanger(DataConnection dataConnection) {
		this.currencyCalc = new CurrencyCalculator();
		this.dataConnection = dataConnection;
		this.cache = new CachedConnection();
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

			RateData rateData = dataConnection.getRateData(request);

			BigDecimal value = request.getValue();
			cache.saveData(rateData);
			return currencyCalc.calculateToPln(value, rateData.getRate());
		} catch (Exception e) {
			throw new ExchangerException("Wyst¹pi³ b³¹d.", e);
		}
	}

	public BigDecimal exchangeFromPln(Request request) {
		try {
			checkValidation(request);

			RateData rateData = dataConnection.getRateData(request);

			BigDecimal value = request.getValue();
			cache.saveData(rateData);
			return currencyCalc.calculateFromPln(value, rateData.getRate());
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

	public void printCache() {
		cache.print();
	}

}

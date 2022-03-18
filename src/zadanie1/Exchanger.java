package zadanie1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import zadanie1.connectors.CachedConnection;
import zadanie1.exceptions.ExchangerException;
import zadanie1.exceptions.RateNotFoundException;
import zadanie1.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie1.exceptions.inputExceptions.DateAfterTodayException;
import zadanie1.exceptions.inputExceptions.DateBeforeFirstException;
import zadanie1.exceptions.inputExceptions.InputValueNullException;
import zadanie1.exceptions.inputExceptions.NegativeValueException;
import zadanie1.interfaces.DataConnection;
import zadanie1.model.RateData;
import zadanie1.model.Request;

public class Exchanger {
	private List<DataConnection> dataConnections;
	private CurrencyCalculator currencyCalc;
	private static CachedConnection cache;

	public Exchanger(List<DataConnection> dataConnections) {
		this.currencyCalc = new CurrencyCalculator();
		this.dataConnections = dataConnections;
		cache = new CachedConnection();
	}

	public BigDecimal exchangeToPln(Request request) {
		try {
			checkValidation(request);

			RateData rateData = findRate(request);
			if (rateData == null) {
				throw new RateNotFoundException("Nie znaleziono kursu dla danej daty");
			}

			cache.saveData(rateData);

			return currencyCalc.calculateToPln(request.getValue(), rateData.getRate());
		} catch (Exception e) {
			throw new ExchangerException("Wyst¹pi³ b³¹d.", e);
		}
	}

	public BigDecimal exchangeFromPln(Request request) {
		try {
			checkValidation(request);

			RateData rateData = findRate(request);
			if (rateData == null) {
				throw new RateNotFoundException("Nie znaleziono kursu dla danej daty");
			}

			cache.saveData(rateData);

			return currencyCalc.calculateFromPln(request.getValue(), rateData.getRate());
		} catch (Exception e) {
			throw new ExchangerException("Wyst¹pi³ b³¹d.", e);
		}
	}

	private RateData findRate(Request request) throws ReadingRateDataException {
		RateData rateData = findRateForPreciseDate(request);
		if (rateData == null) {
			rateData = findOlderRate(request);
		}
		return rateData;
	}

	private RateData findRateForPreciseDate(Request request) {
		for (DataConnection dataConnection : dataConnections) {
			;
			try {
				RateData rateData = dataConnection.getRateData(request);
				if (rateData != null) {
					return rateData;
				}
			} catch (ReadingRateDataException e) {
				continue;
			}
		}
		return null;
	}

	private RateData findOlderRate(Request request) {
		List<DataConnection> lista = new ArrayList<>();
		for (DataConnection dataConnection : lista) {
			try {
				RateData rateData = null;
				rateData = dataConnection.getOlderRateData(request);
				if (rateData != null) {
					return rateData;
				}
			} catch (ReadingRateDataException e) {
				continue;
			}
		}
		return null;
	}

	private void checkValidation(Request request)
			throws NegativeValueException, InputValueNullException, DateBeforeFirstException, DateAfterTodayException {
		InputValidator.checkDate(request.getDate());
		InputValidator.checkValue(request.getValue());
		InputValidator.checkCurrency(request.getCurrency());
	}

	public void printCache() {
		cache.print();
	}
}

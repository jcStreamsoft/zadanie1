package zadanie1;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
	private static final int MAX_ATTEMPTS = 7;

	public Exchanger(List<DataConnection> dataConnections) {
		this.currencyCalc = new CurrencyCalculator();
		this.dataConnections = dataConnections;

	}

	public BigDecimal exchangeToPln(Request request) {
		try {
			checkValidation(request);

			RateData rateData = findRate(request);
			if (rateData == null) {
				throw new RateNotFoundException("Nie znaleziono kursu dla danej daty");
			}
			saveRateData(rateData);
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
			saveRateData(rateData);
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
			try {
				RateData rateData = dataConnection.getRateData(request);
				if (rateData != null) {
					return rateData;
				}
			} catch (ReadingRateDataException e) {
			}
		}
		return null;
	}

	private RateData findOlderRate(Request request) {
		for (DataConnection dataConnection : dataConnections) {
			try {
				RateData rateData = null;
				for (int i = 0; i < MAX_ATTEMPTS; i++) {
					LocalDate olderDate = request.getDate().minusDays(i);

					rateData = dataConnection.getRateData(request, olderDate);

					if (rateData != null) {
						return rateData;
					}
				}

			} catch (ReadingRateDataException e) {
			}
		}
		return null;
	}

	private void saveRateData(RateData rateData) {
		for (DataConnection dataConnection : dataConnections) {
			dataConnection.saveRateData(rateData);
		}
	}

	private void checkValidation(Request request)
			throws NegativeValueException, InputValueNullException, DateBeforeFirstException, DateAfterTodayException {
		InputValidator.checkDate(request.getDate());
		InputValidator.checkValue(request.getValue());
		InputValidator.checkCurrency(request.getCurrency());
	}
}

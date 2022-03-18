package zadanie1.connectors;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import zadanie1.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie1.interfaces.DataConnection;
import zadanie1.interfaces.Savable;
import zadanie1.model.CacheKey;
import zadanie1.model.RateData;
import zadanie1.model.Request;

public class CachedConnection implements DataConnection, Savable {
	private static final Map<CacheKey, RateData> data = new HashMap<>();
	private static final int MAX_ATTEMPTS = 7;

	@Override
	public void saveData(RateData rateData) {
		CacheKey key = new CacheKey(rateData.getDate(), rateData.getCurrency());
		data.put(key, rateData);
	}

	@Override
	public RateData getRateData(Request request) throws ReadingRateDataException {
		CacheKey key = new CacheKey(request.getDate(), request.getCurrency());
		RateData rateData = data.get(key);
		return rateData;
	}

	@Override
	public RateData getOlderRateData(Request request) throws ReadingRateDataException {
		return findOlderDateRate(request, request.getDate());
	}

	public void print() {
		for (Map.Entry entry : data.entrySet()) {
			System.out.println("key: " + entry.toString());
		}
	}

	public RateData findOlderDateRate(Request request, LocalDate date) {
		for (int i = 1; i < MAX_ATTEMPTS; i++) {
			LocalDate newDate = date.minusDays(i);
			CacheKey key = new CacheKey(newDate, request.getCurrency());
			RateData rateData = data.get(key);
			if (rateData != null) {
				return rateData;
			}
		}
		return null;
	}

}

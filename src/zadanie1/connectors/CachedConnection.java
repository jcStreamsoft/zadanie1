package zadanie1.connectors;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import zadanie1.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie1.interfaces.DataConnection;
import zadanie1.model.RateData;
import zadanie1.model.Request;

public class CachedConnection implements DataConnection {

	private static final Map<String, RateData> data = new HashMap<>();

	@Override
	public void saveRateData(RateData rateData) {
		String key = rateData.getDate() + "/" + rateData.getCurrency();

		data.put(key, rateData);
	}

	@Override
	public RateData getRateData(Request request) throws ReadingRateDataException {
		String key = request.getDate() + "/" + request.getCurrency();
		RateData rateData = data.get(key);
		return rateData;
	}

	@Override
	public RateData getRateData(Request request, LocalDate date) throws ReadingRateDataException {
		String key = date + "/" + request.getCurrency();
		RateData rateData = data.get(key);
		return rateData;
	}

	public void print() {
		for (Map.Entry entry : data.entrySet()) {
			System.out.println("key: " + entry.toString());
		}
	}

//	private RateData findOlderDateRate(Request request, LocalDate date) {
//		for (int i = 1; i < MAX_ATTEMPTS; i++) {
//			LocalDate newDate = date.minusDays(i);
//			String key = newDate + "/" + request.getCurrency();
//			System.out.println(key.toString());
//			RateData rateData = data.get(key);
//			if (rateData != null) {
//				return rateData;
//			}
//		}
//		return null;
//	}
}

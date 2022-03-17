package zadanie1.connectors;

import java.util.Map;
import java.util.TreeMap;

import zadanie1.exceptions.streamInputExceptions.CreatingInputStringException;
import zadanie1.interfaces.DataConnection;
import zadanie1.interfaces.Savable;
import zadanie1.model.RateData;
import zadanie1.model.Request;

public class CachedConnection implements DataConnection, Savable {
	private static final TreeMap<String, RateData> data = new TreeMap<>();

	@Override
	public void saveData(RateData rateData) {
		String key = rateData.getDate() + "/" + rateData.getCurrency();
		data.put(key, rateData);
	}

	@Override
	public RateData getRateData(Request request) throws CreatingInputStringException {
		String key = request.getLocalDate() + "/" + request.getCurrency();
		RateData rateData = data.get(key);
		return rateData;
	}

	@Override
	public RateData findRateData(Request request) throws CreatingInputStringException {
		// TODO Auto-generated method stub
		return null;
	}

	public void print() {
		for (Map.Entry entry : data.entrySet()) {
			System.out.println("key: " + entry.toString());
		}
	}

}

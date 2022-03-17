package zadanie1.connectors;

import java.util.TreeMap;

import zadanie1.exceptions.streamInputExceptions.CreatingInputStringException;
import zadanie1.interfaces.DataConnection;
import zadanie1.interfaces.Savable;
import zadanie1.model.CacheKey;
import zadanie1.model.RateData;
import zadanie1.model.Request;

public class CachedConnection implements DataConnection, Savable {
	private static final TreeMap<CacheKey, RateData> data = new TreeMap<>();

	@Override
	public void saveData(RateData rateData) {
		CacheKey key = new CacheKey(rateData.getDate(), rateData.getCurrency());
		System.out.println(key.toString());
		data.put(key, rateData);
	}

	@Override
	public RateData getRateData(Request request) throws CreatingInputStringException {
		CacheKey key = new CacheKey(request.getLocalDate(), request.getCurrency());
		RateData rateData = data.get(key);
		return rateData;
	}

	@Override
	public RateData findRateData(Request request) throws CreatingInputStringException {
		// TODO Auto-generated method stub
		return null;
	}

}

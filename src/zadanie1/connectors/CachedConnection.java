package zadanie1.connectors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.TreeMap;

import zadanie1.exceptions.streamInputExceptions.CreatingInputStringException;
import zadanie1.interfaces.DataConnection;
import zadanie1.interfaces.Savable;
import zadanie1.model.CacheKey;
import zadanie1.model.Request;

public class CachedConnection implements DataConnection, Savable {
	private static final TreeMap<CacheKey, String> data = new TreeMap<>();

	@Override
	public void saveData(String currencyCode, LocalDate date, String dataString) {
		CacheKey key = new CacheKey(date, currencyCode);
		data.put(key, dataString);
	}

	public BigDecimal getRate(Request request) {

		return new BigDecimal("0");
	}

	@Override
	public String getInputString(Request request) throws CreatingInputStringException {

		return null;
	}

}

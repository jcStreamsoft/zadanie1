package zadanie1.interfaces;

import zadanie1.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie1.model.RateData;
import zadanie1.model.Request;

public interface DataConnection {
	public RateData getRateData(Request request) throws ReadingRateDataException;

	public RateData getOlderRateData(Request request) throws ReadingRateDataException;
}

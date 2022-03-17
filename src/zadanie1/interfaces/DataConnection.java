package zadanie1.interfaces;

import zadanie1.exceptions.streamInputExceptions.CreatingInputStringException;
import zadanie1.model.RateData;
import zadanie1.model.Request;

public interface DataConnection {
	public RateData getRateData(Request request) throws CreatingInputStringException;

	public RateData findRateData(Request request) throws CreatingInputStringException;
}

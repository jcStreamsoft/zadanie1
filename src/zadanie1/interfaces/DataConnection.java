package zadanie1.interfaces;

import java.time.LocalDate;

import zadanie1.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie1.model.RateData;
import zadanie1.model.Request;

public interface DataConnection {
	public RateData getRateData(Request request) throws ReadingRateDataException;

	public RateData getRateData(Request request, LocalDate date) throws ReadingRateDataException;

	default public void saveRateData(RateData rateData) {

	}
}

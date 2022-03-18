package zadanie1.connectors;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import zadanie1.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie1.exceptions.parserExceptions.ParsingException;
import zadanie1.interfaces.DataConnection;
import zadanie1.interfaces.parsers.FileParse;
import zadanie1.model.RateData;
import zadanie1.model.Request;
import zadanie1.model.fileModel.Rate;
import zadanie1.model.fileModel.RatesTable;

public class FileConnection implements DataConnection {

	private FileParse parser;
	private FileReader fileReader;
	private String filePath;
	private static final int MAX_ATTEMPTS = 7;

	public FileConnection(FileParse parser, String filePath) {
		this.fileReader = new FileReader(filePath);
		this.filePath = filePath;
		this.parser = parser;

	}

	@Override
	public RateData getRateData(Request request) throws ReadingRateDataException {
		try {

			List<RatesTable> rates = parser.getRateFromString(fileReader.getStringFromFile());
			Rate rate = findRate(rates, request);
			RateData rateData = null;
			if (rate != null) {
				rateData = new RateData(request.getDate(), rate.getMid(), request.getCurrency());
			}
			return rateData;
		} catch (IOException e) {
			throw new ReadingRateDataException("Nie znaleziono pliku o œcie¿ce : " + filePath, e);
		} catch (ParsingException e) {
			throw new ReadingRateDataException("B³¹d przy parsowaniu : " + filePath, e);
		}
	}

	@Override
	public RateData getOlderRateData(Request request) throws ReadingRateDataException {
		try {
			List<RatesTable> rates = parser.getRateFromString(fileReader.getStringFromFile());

			Rate rate = findOlderDateRate(rates, request, request.getDate());
			RateData rateData = null;
			if (rate != null) {
				rateData = new RateData(request.getDate(), rate.getMid(), request.getCurrency());
			}
			return rateData;
		} catch (IOException e) {
			throw new ReadingRateDataException("Nie znaleziono pliku o œcie¿ce : " + filePath, e);
		} catch (ParsingException e) {
			throw new ReadingRateDataException("B³¹d przy parsowaniu : " + filePath, e);
		}
	}

	private Rate findRate(List<RatesTable> ratesTable, Request request) {
		for (RatesTable rateTable : ratesTable) {
			if (dateEquals(rateTable, request.getDate())) {
				for (Rate rate : rateTable.getRates()) {
					if (currencyCodeEquals(rate, request)) {
						return rate;
					}
				}
			}
		}
		return null;
	}

	private Rate findRate(List<RatesTable> ratesTable, Request request, LocalDate date) {
		for (RatesTable rateTable : ratesTable) {
			if (dateEquals(rateTable, request.getDate())) {
				for (Rate rate : rateTable.getRates()) {
					if (currencyCodeEquals(rate, request)) {
						return rate;
					}
				}
			}
		}
		return null;
	}

	public Rate findOlderDateRate(List<RatesTable> ratesTable, Request request, LocalDate date) {
		for (int i = 1; i < MAX_ATTEMPTS; i++) {
			LocalDate newDate = date.minusDays(i);
			Rate rate = findRate(ratesTable, request, newDate);
			if (rate != null) {
				return rate;
			}
		}
		return null;
	}

	private boolean dateEquals(RatesTable rateTable, LocalDate date) {
		return rateTable.getEffectiveDate().isEqual(date);
	}

	private boolean currencyCodeEquals(Rate rate, Request request) {
		return rate.getCode().equals(request.getCurrencyCode().toUpperCase());
	}
}

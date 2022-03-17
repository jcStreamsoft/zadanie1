package zadanie1.connectors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import zadanie1.exceptions.parserExceptions.ParsingException;
import zadanie1.exceptions.streamInputExceptions.CreatingInputStringException;
import zadanie1.interfaces.DataConnection;
import zadanie1.interfaces.parsers.FileParse;
import zadanie1.model.RateData;
import zadanie1.model.Request;
import zadanie1.model.fileModel.Rate;
import zadanie1.model.fileModel.RatesTable;

public class FileConnection implements DataConnection {

	private String filePath;
	private FileParse parser;

	public FileConnection(FileParse parser, String filePath) {
		this.filePath = filePath;
		this.parser = parser;
	}

	@Override
	public RateData getRateData(Request request) throws CreatingInputStringException {
		try {
			File inputFile = new File(filePath);
			InputStream inputStream = new FileInputStream(inputFile);

			String result = createStringFromStream(inputStream);
			List<RatesTable> rates = parser.getRateFromString(result);

			Rate rate = findRate(rates, request);
			RateData rateData = null;
			if (rate != null) {
				rateData = new RateData(request.getLocalDate(), rate.getMid(), request.getCurrency());
			}
			inputStream.close();
			return rateData;
		} catch (IOException e) {
			throw new CreatingInputStringException("Nie znaleziono pliku o œcie¿ce : " + filePath, e);
		} catch (ParsingException e) {
			throw new CreatingInputStringException("B³¹d przy parsowaniu : " + filePath, e);
		}
	}

	private Rate findRate(List<RatesTable> list, Request request) {
		for (int i = 0; i < list.size(); i++) {
			RatesTable rt = list.get(i);
			if (rt.getEffectiveDate().isEqual(request.getLocalDate())) {
				List<Rate> rates = rt.getRates();
				for (int j = 0; j < rates.size(); j++) {
					Rate r = rates.get(j);
					if (r.getCode().equals(request.getCurrency().getCode().toUpperCase())) {
						return r;
					}
				}
			}
		}

		return null;
	}

	private String createStringFromStream(InputStream inputStream) {
		return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining());
	}

	@Override
	public RateData findRateData(Request request) throws CreatingInputStringException {
		// TODO Auto-generated method stub
		return null;
	}

}

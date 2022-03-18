package zadanie1.connectors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.stream.Collectors;

import zadanie1.exceptions.dataConnectionExceptions.CreatingURLException;
import zadanie1.exceptions.dataConnectionExceptions.ReadingRateDataException;
import zadanie1.exceptions.parserExceptions.ParsingException;
import zadanie1.interfaces.DataConnection;
import zadanie1.interfaces.parsers.ApiParse;
import zadanie1.model.RateData;
import zadanie1.model.Request;
import zadanie1.model.apiModel.Rate;

public class ApiConnection implements DataConnection {

	private HttpURLConnection connection;
	private UrlCreator urlCreator;
	private ApiParse parser;
	private static final int MAX_ATTEMPTS = 7;

	public ApiConnection(ApiParse parser) {
		super();
		this.parser = parser;
	}

	@Override
	public RateData getRateData(Request request) throws ReadingRateDataException {
		this.urlCreator = new UrlCreator(request.getCurrencyCode(), parser.getFormatType());
		try {
			createConnectionFromURL(createURL(request.getDate()));

			String result = createStringFromStream(connection.getInputStream());
			connection.disconnect();

			Rate rate = parser.getRateFromString(result);
			RateData rateData = new RateData(request.getDate(), rate.getMid(), request.getCurrency());

			return rateData;
		} catch (IOException | CreatingURLException e) {
			throw new ReadingRateDataException("B³¹d przy po³¹czeniu z NBP ", e);
		} catch (ParsingException e) {
			throw new ReadingRateDataException("B³¹d przy parsowaniu danych z NBP ", e);
		}
	}

	@Override
	public RateData getOlderRateData(Request request) throws ReadingRateDataException {
		this.urlCreator = new UrlCreator(request.getCurrencyCode(), parser.getFormatType());
		try {
			LocalDate date = findOlderExistingDate(request, request.getDate());
			RateData rateData = null;
			if (date != null) {
				createConnectionFromURL(createURL(date));

				String result = createStringFromStream(connection.getInputStream());
				connection.disconnect();

				Rate rate = parser.getRateFromString(result);
				rateData = new RateData(date, rate.getMid(), request.getCurrency());
			}
			return rateData;
		} catch (IOException | CreatingURLException e) {
			throw new ReadingRateDataException("B³¹d przy po³¹czeniu z NBP ", e);
		} catch (ParsingException e) {
			throw new ReadingRateDataException("B³¹d przy parsowaniu danych z NBP ", e);
		}
	}

	private URL createURL(LocalDate localDate) throws CreatingURLException, IOException {
		return urlCreator.createDateRateUrl(localDate);
	}

	private void createConnectionFromURL(URL url) throws IOException {
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
	}

	private boolean connectionExitst(URL url) throws IOException {
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		if (connection.getResponseCode() != 200) {
			return false;
		} else {
			return true;
		}
	}

	private String createStringFromStream(InputStream inputStream) {
		return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining());
	}

	public LocalDate findOlderExistingDate(Request request, LocalDate date) {
		for (int i = 1; i < MAX_ATTEMPTS; i++) {
			try {
				LocalDate newDate = date.minusDays(i);
				if (connectionExitst(createURL(newDate))) {
					return newDate;
				}
			} catch (IOException e) {
				continue;
			} catch (CreatingURLException e) {
				continue;
			}
		}
		return null;
	}
}

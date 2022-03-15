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

import zadanie1.exceptions.streamInputExceptions.CreatingInputStringException;
import zadanie1.exceptions.streamInputExceptions.CreatingURLException;
import zadanie1.interfaces.ReturnInputString;
import zadanie1.model.Request;

public class ApiConnection implements ReturnInputString {

	private HttpURLConnection connection;
	private UrlCreator urlCreator;
	private final int MAX_ATTEMPTS = 7;

	@Override
	public String getInputString(Request request) throws CreatingInputStringException {
		this.urlCreator = new UrlCreator(request.getCurrency(), request.getDataFormat());
		try {
			createConnection(request.getLocalDate());

			String result = createStringFromStream(connection.getInputStream());
			connection.disconnect();
			return result;
		} catch (IOException | CreatingURLException e) {
			throw new CreatingInputStringException("B³¹d przy tworzeniu inputStreama ", e);
		}
	}

	private void createConnection(LocalDate localDate) throws CreatingURLException, IOException {
		URL url = findExistingUrl(localDate);

		createConnectionFromURL(url);
	}

	private void createConnectionFromURL(URL url) throws IOException {
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
	}

	private URL findExistingUrl(LocalDate localDate) throws IOException, CreatingURLException {
		URL url = urlCreator.createDateRateUrl(localDate);
		for (int i = 0; i < MAX_ATTEMPTS; i++) {
			if (connectionExitst(url)) {
				return url;
			} else {
				url = urlCreator.createDateRateUrl(localDate.minusDays(1));
			}
		}
		return urlCreator.createLastRateUrl();
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
}

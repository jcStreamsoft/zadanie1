package zadanie1.connectors;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

import zadanie1.enums.Currency;
import zadanie1.exceptions.streamInputExceptions.ClosingInputStreamException;
import zadanie1.exceptions.streamInputExceptions.CreatingInputStreamException;
import zadanie1.exceptions.streamInputExceptions.CreatingURLException;
import zadanie1.interfaces.Streams;
import zadanie1.model.Request;

public class ApiConnection implements Streams {

	private HttpURLConnection connection;
	private UrlCreator urlCreator;
	private final int MAX_ATTEMPTS = 7;

	@Override
	public void close() throws ClosingInputStreamException {
		connection.disconnect();
	}

	@Override
	public InputStream getInputStream(Request request) throws CreatingInputStreamException {
		this.urlCreator = new UrlCreator(request.getCurrency(), request.getDataFormat());
		try {
			createConnection(request.getLocalDate());
			return connection.getInputStream();
		} catch (IOException | CreatingURLException e) {
			throw new CreatingInputStreamException("B³¹d przy tworzeniu inputStreama ->" + e.toString());
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
}

package zadanie1.connectors;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

import zadanie1.enums.Currency;
import zadanie1.interfaces.Streams;
import zadanie1.model.Request;

public class ApiConnection implements Streams {

	private HttpURLConnection connection;
	private UrlCreator urlCreator;
	private final int MAX_ATTEMPTS = 7;


	public void createConnection(LocalDate localDate) {
		try {
			URL url = findExistingUrl(localDate);
			createConnectionFromURL(url);
		} catch (IOException e) {
			connection.disconnect();
			// TODO
		}
	}

	private void createConnectionFromURL(URL url) throws IOException {
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
	}

	private URL findExistingUrl(LocalDate localDate) throws IOException {
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

	@Override
	public void close() throws IOException{
		connection.disconnect();
	}

	@Override
	public InputStream getInputStream(Request request) {
		this.urlCreator = new UrlCreator(request.getCurrency(), request.getDataFormat());
		try {
			createConnection(request.getLocalDate());
			return connection.getInputStream();
		} catch (IOException e) {
			// TODO
			return null;
		}

	}
}

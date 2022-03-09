package zadanie1;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

import zadanie1.enums.Currency;

public class Connection {

	private HttpURLConnection connection;
	private UrlCreator urlCreator;
	private final int MAX_ATTEMPTS = 7;

	public Connection(Currency currency,String dataFormat) {
		this.urlCreator = new UrlCreator(currency,dataFormat);
	}

	public void createConnection() {
		createConnection(LocalDate.now());
	}

	public void createConnection(LocalDate localDate) {
		try {
			URL url = findExistingUrl(localDate);
			createConnectionFromURL(url);
		} catch (IOException e) {
			connection.disconnect();
			//TODO
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

	public InputStream getInputStream() {
		try {
			return connection.getInputStream();
		} catch (IOException e) {
			//TODO
			return null;
		}
	}

	public void disconnectConnection() {
		connection.disconnect();
	}
}

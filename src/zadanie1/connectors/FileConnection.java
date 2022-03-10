package zadanie1.connectors;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import zadanie1.enums.Currency;

public class FileConnection {

	private File file;

	public FileConnection(Currency currency, String dataFormat) {

	}

	public InputStream getInputStream() {
		try {
			return new FileInputStream(file);
		} catch (IOException e) {
			// TODO
			return null;
		}
	}

}

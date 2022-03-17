package zadanie1.connectors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import zadanie1.exceptions.streamInputExceptions.CreatingInputStringException;
import zadanie1.interfaces.DataConnection;
import zadanie1.model.Request;

public class FileConnection implements DataConnection {
	private String filePath;

	public FileConnection(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String getInputString(Request request) throws CreatingInputStringException {
		try {
			File inputFile = new File(filePath);
			InputStream inputStream = new FileInputStream(inputFile);
			String result = createStringFromStream(inputStream);
			inputStream.close();
			return result;
		} catch (IOException e) {
			throw new CreatingInputStringException("Nie znaleziono pliku o œcie¿ce : " + filePath, e);
		}
	}

	private String createStringFromStream(InputStream inputStream) {
		return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
				.collect(Collectors.joining());
	}
}

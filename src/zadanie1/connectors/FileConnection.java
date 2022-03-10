package zadanie1.connectors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import zadanie1.enums.Currency;
import zadanie1.exceptions.streamInputExceptions.ClosingInputStreamException;
import zadanie1.exceptions.streamInputExceptions.CreatingInputStreamException;
import zadanie1.interfaces.Streams;
import zadanie1.model.Request;

public class FileConnection implements Streams {

	private InputStream inputStream;

	@Override
	public void close() throws ClosingInputStreamException {
		try {
			inputStream.close();
		} catch (IOException e) {
			throw new ClosingInputStreamException("B³¹d przy zamykaniu InputStreama");
		}
	}

	@Override
	public InputStream getInputStream(Request request) throws CreatingInputStreamException {
		try {
			File filePath = new File(request.getFilePath());
			inputStream = new FileInputStream(filePath);
			return inputStream;
		} catch (FileNotFoundException e) {
			throw new CreatingInputStreamException("Nie znaleziono pliku o œcie¿ce : " + request.getFilePath());
		}
	}
}

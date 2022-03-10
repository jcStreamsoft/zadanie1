package zadanie1.interfaces;

import java.io.InputStream;

import zadanie1.exceptions.streamInputExceptions.ClosingInputStreamException;
import zadanie1.exceptions.streamInputExceptions.CreatingInputStreamException;
import zadanie1.model.Request;

public interface Streams {
	public InputStream getInputStream(Request request) throws CreatingInputStreamException;

	public void close() throws ClosingInputStreamException;

}

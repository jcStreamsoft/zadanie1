package zadanie1.interfaces;

import java.io.Closeable;
import java.io.InputStream;

import zadanie1.model.Request;

public interface Streams extends Closeable{
	public InputStream getInputStream(Request request);
}

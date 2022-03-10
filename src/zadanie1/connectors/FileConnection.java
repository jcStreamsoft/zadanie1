package zadanie1.connectors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import zadanie1.enums.Currency;
import zadanie1.exceptions.streamInputExceptions.ClosingInputStreamException;
import zadanie1.interfaces.Streams;
import zadanie1.model.Request;

public class FileConnection implements Streams{

	private File file;
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
	public InputStream getInputStream(Request request) {
		 File initialFile = new File("src/main/resources/sample.txt");
		    try {
		    	 inputStream = new FileInputStream(initialFile);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}

}

package zadanie1.interfaces;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import zadanie1.enums.ResponseType;

public interface ResponseParser {
	
	public BigDecimal getCurrencyRate(ResponseType responseType,HttpURLConnection currentConnection)throws StreamReadException, DatabindException, IOException;	
	
}

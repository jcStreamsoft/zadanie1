package zadanie1.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import zadanie1.exceptions.parserExceptions.ParsingException;
import zadanie1.exceptions.parserExceptions.ReadingCurrencyRateException;
import zadanie1.model.Response;

public interface NbpApiParser {
	
	public String getFormatType();

	public BigDecimal getRateFromStream(InputStream stream)throws ParsingException;

}

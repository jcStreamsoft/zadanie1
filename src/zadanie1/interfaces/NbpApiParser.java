package zadanie1.interfaces;

import java.io.InputStream;
import java.math.BigDecimal;
import zadanie1.exceptions.parserExceptions.ParsingException;

public interface NbpApiParser {
	
	public String getFormatType();

	public BigDecimal getRateFromStream(InputStream stream)throws ParsingException;
}

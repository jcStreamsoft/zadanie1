package zadanie1.parsers;

import java.io.InputStream;
import java.math.BigDecimal;
import zadanie1.enums.ResponseType;
import zadanie1.interfaces.StreamParser;

public class DataParser {
	StreamParser parser;
	public BigDecimal getCurrencyRate(InputStream stream) {
		return parser.getRateFromStream(stream);
	}
	public String getDataParserFormat() {
		return parser.getFormatType();
	}
	public DataParser(StreamParser parser){
		this.parser = parser;
	}
	
}

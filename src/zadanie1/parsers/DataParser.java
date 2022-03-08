package zadanie1.parsers;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import zadanie1.enums.ResponseType;
import zadanie1.interfaces.NbpParser;
import zadanie1.interfaces.StreamParser;


public class DataParser {
	
	public BigDecimal getCurrencyRate(ResponseType responseType,InputStream stream) {
		StreamParser parser;
			switch (responseType) {
			case JSON:
				parser=new JsonParser();
				return parser.getRateFromStream(stream);
			case XML:
				parser=new XmlParser();
				return parser.getRateFromStream(stream);
				
			}
		
		return null;
	}
	
	
	
	
}

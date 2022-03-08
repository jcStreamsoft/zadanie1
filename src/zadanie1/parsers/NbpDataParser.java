package zadanie1.parsers;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import zadanie1.enums.ResponseType;
import zadanie1.interfaces.NbpParser;


public class NbpDataParser {
	
	public BigDecimal getCurrencyRate(ResponseType responseType,HttpURLConnection connection) {
		NbpParser parser;
			switch (responseType) {
			case JSON:
				parser=new NbpJsonParser();
				return parser.readRateFromResponse(connection);
			case XML:
				parser=new NbpXmlParser();
				return parser.readRateFromResponse(connection);
				
			}
		
		return new BigDecimal(1);
	}
	
	
	
}

package zadanie1.parsers;

import java.io.InputStream;
import java.math.BigDecimal;
import zadanie1.enums.ResponseType;
import zadanie1.interfaces.StreamParser;

public class DataParser {
	StreamParser parser;
	public BigDecimal getCurrencyRate(InputStream stream) {
		//StreamParser parser= choseParserType(responseType);
		return parser.getRateFromStream(stream);
	}
	public String getDataParserFormat() {
		return parser.getFormatType();
	}
//	public StreamParser choseParserType(ResponseType responseType) {
//		switch (responseType) {
//		case JSON:
//			return new JsonParser();
//		case XML:
//			return new XmlParser();
//			
//		}
//		return null;
//	}
	public DataParser(StreamParser parser){
		this.parser = parser;
	}
	
	
	
	
}

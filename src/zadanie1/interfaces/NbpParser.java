package zadanie1.interfaces;

import java.math.BigDecimal;
import java.net.HttpURLConnection;

public interface NbpParser {
	public BigDecimal readRateFromResponse(HttpURLConnection connection);
}	

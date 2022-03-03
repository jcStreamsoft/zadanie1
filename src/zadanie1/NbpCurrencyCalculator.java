package zadanie1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import zadanie1.model.Currency;
import zadanie1.model.Response;

public class NbpCurrencyCalculator {
	final  String nbpAdress= "http://api.nbp.pl/api/exchangerates/rates/a/";
	
	public  BigDecimal getCurrencyRate(String currencyCode)  throws IOException {
		BigDecimal currencyRate=new BigDecimal(0);
		URL url = new URL(nbpAdress + currencyCode );
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		try {
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			
			if(responseCode != 200) {
				throw new CurrencyNotFoundException();
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			
			ObjectMapper mapper = new ObjectMapper();
			
			Response rs = mapper.readValue(con.getInputStream(), Response.class);
			System.out.println(rs.toString());
			currencyRate=rs.getRates().get(0).getMid();
							
		}catch(CurrencyNotFoundException e){
			e.printStackTrace();
		}finally {
			con.disconnect();
		}
		return currencyRate;
	}
	public  BigDecimal calculateCurrency(BigDecimal value,String oldCurrencyCode,String newCurrencyCode) throws IOException,CurrencyNotFoundException {
		BigDecimal result;
		
		if(oldCurrencyCode.equalsIgnoreCase(newCurrencyCode)) {
			return value;
		}else if(oldCurrencyCode.equalsIgnoreCase("pln")) {
			BigDecimal newRate = getCurrencyRate(newCurrencyCode);
			result = value.divide(newRate, 5,RoundingMode.HALF_UP);
			
		}else if(newCurrencyCode.equalsIgnoreCase("pln")){
			BigDecimal oldRate = getCurrencyRate(oldCurrencyCode);
			result = value.multiply(oldRate);
			result = result.setScale(5, RoundingMode.HALF_UP);
		}else {
			BigDecimal newRate = getCurrencyRate(newCurrencyCode);
			BigDecimal oldRate = getCurrencyRate(oldCurrencyCode);
			result = value.multiply(oldRate);
			result = result.divide(newRate, 5,RoundingMode.HALF_UP);
		}
		return result;
	}
	public BigDecimal calculateToPln(BigDecimal value,Currency currencyCode) {
		//get value
			// connect  * no connection,
			// get json/xml
			// read currency rate
			// return currency rate
		
		// calculate
			// calculate and return value
		
		return null;
	}
	public BigDecimal calculateFromPln(BigDecimal value,Currency currencyCode) {
		return null;
	}
	
}

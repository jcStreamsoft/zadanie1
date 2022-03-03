package zadanie1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;




public class NbpCurrencyCalculator {
	final static String nbpAdress= "http://api.nbp.pl/api/exchangerates/rates/a/";
	
	public static BigDecimal getCurrencyRate(String currencyCode)  throws IOException {
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
	
			String currentLine;				
			while((currentLine=br.readLine())!=null) {
				JSONObject jsonResponse = new JSONObject(new JSONTokener(currentLine.toString()));
				JSONArray jsonArray = jsonResponse.getJSONArray("rates");
				JSONObject jsonRate = jsonArray.getJSONObject(0);
				currencyRate = jsonRate.getBigDecimal("mid");
				
			}
							
		}catch(CurrencyNotFoundException e){
			e.printStackTrace();
		}finally {
			con.disconnect();
		}
		return currencyRate;
	}
	public static BigDecimal calculateCurrency(BigDecimal value,String oldCurrencyCode,String newCurrencyCode) throws IOException,CurrencyNotFoundException {
		BigDecimal result;
		
		if(oldCurrencyCode.equalsIgnoreCase(newCurrencyCode)) {
			return value;
		}else if(oldCurrencyCode.equalsIgnoreCase("pln")) {
			BigDecimal newRate = getCurrencyRate(newCurrencyCode);
			result = value.divide(newRate, 5,RoundingMode.HALF_UP);
			
		}else if(newCurrencyCode.equalsIgnoreCase("pln")){
			BigDecimal oldRate = getCurrencyRate(oldCurrencyCode);
			result = value.multiply(oldRate);
			result = result.setScale(4, RoundingMode.HALF_UP);
		}else {
			BigDecimal newRate = getCurrencyRate(newCurrencyCode);
			BigDecimal oldRate = getCurrencyRate(oldCurrencyCode);
			result = value.multiply(oldRate);
			result = result.divide(newRate, 5,RoundingMode.HALF_UP);
		}
		return result;
	}
	

}

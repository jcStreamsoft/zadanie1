package zadanie1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;

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
				currencyRate=tmpParser(currentLine);
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
		
		if(oldCurrencyCode.equalsIgnoreCase("pln")) {
			BigDecimal newRate = getCurrencyRate(newCurrencyCode);
			result = value.divide(newRate, 5,RoundingMode.HALF_UP);
			
		}else if(newCurrencyCode.equalsIgnoreCase("pln")){
			BigDecimal oldRate = getCurrencyRate(oldCurrencyCode);
			result = value.multiply(oldRate);
		}else {
			BigDecimal newRate = getCurrencyRate(newCurrencyCode);
			BigDecimal oldRate = getCurrencyRate(oldCurrencyCode);
			result = value.multiply(oldRate);
			result = result.divide(newRate, 5,RoundingMode.HALF_UP);
		}
		return result;
	}
	public static BigDecimal tmpParser(String line) {
		String value="0";
		String str[]=line.split(":");
		value = str[str.length-1].substring(0, str[str.length-1].length() - 3);
		BigDecimal result= new BigDecimal(value);
		return result;
	}

}

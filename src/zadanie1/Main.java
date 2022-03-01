package zadanie1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

	public static void main(String[] args) throws IOException {
		
		System.out.println(calculateCurrency(new BigDecimal(1),"pln","thb"));
	}
	public static BigDecimal getCurrencyRate(String currencyCode) throws IOException {
		
		URL url = new URL("http://api.nbp.pl/api/exchangerates/rates/a/"+ currencyCode );
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		int responseCode = con.getResponseCode();
		System.out.println("kod " + responseCode);
		if(responseCode == 404) {
			System.out.println("error");
			return new BigDecimal(0);
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		String currentLine;
		BigDecimal bd=new BigDecimal(0);
		while((currentLine=br.readLine())!=null) {
			bd=tmpParser(currentLine);
		}
		
		con.disconnect();
		return bd;
	}
	public static BigDecimal calculateCurrency(BigDecimal value,String oldCurrency,String newCurrency) throws IOException {
		BigDecimal result;
		
		BigDecimal newRate = getCurrencyRate(newCurrency);
		if(oldCurrency.equalsIgnoreCase("pln")) {
			result = value.divide(newRate, 5,RoundingMode.HALF_UP);
			
		}else {
			BigDecimal oldRate = getCurrencyRate(oldCurrency);
			result = value.multiply(oldRate);
			result = result.divide(newRate, 5,RoundingMode.HALF_UP);
		}
	
		return result;
	}
	public static BigDecimal tmpParser(String line) {
		String value="NAN";
		String str[]=line.split(":");
		value = str[str.length-1].substring(0, str[str.length-1].length() - 3);
		BigDecimal result= new BigDecimal(value);
		return result;
	}
	
}
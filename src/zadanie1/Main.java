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

	public static void main(String[] args) throws IOException,CurrencyNotFoundException {
		
		BigDecimal result=NbpCurrencyCalculator.calculateCurrency(new BigDecimal(1),"eur","pln");
		System.out.println(result);
	}
	
	
}
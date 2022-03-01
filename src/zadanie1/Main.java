package zadanie1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {

	public static void main(String[] args) throws IOException {
		
		System.out.println("cos");
		getTest();
	
	}
	public static void getTest() throws IOException {
		
		URL url = new URL("http://api.nbp.pl/api/exchangerates/rates/c/usd/today/");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		if(responseCode == 404) {
			System.out.println("error");
			return;
		}
		System.out.println("kod " + responseCode);
		
		
		BufferedReader br= null;
		br=new BufferedReader(new InputStreamReader(con.getInputStream()));
		String currentLine;
		while((currentLine=br.readLine())!=null) {
			System.out.println(currentLine);
		}
		
		con.disconnect();
	}
	
	

}

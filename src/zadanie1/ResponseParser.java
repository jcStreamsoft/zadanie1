package zadanie1;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

import zadanie1.exceptions.CurrencyNotFoundException;
import zadanie1.model.Response;

public abstract class ResponseParser {
	private static HttpURLConnection con;
	private final String nbpAdress = "http://api.nbp.pl/api/exchangerates/rates/a/";
	public void getConnection(String currencyCode)throws IOException {
		BigDecimal currencyRate=new BigDecimal(0);
		URL url = new URL(nbpAdress + currencyCode );
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		try {
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			
			if(responseCode != 200) {
				throw new CurrencyNotFoundException();
			}
			
			
			ObjectMapper mapper = new ObjectMapper();
			
			Response rs = mapper.readValue(con.getInputStream(), Response.class);
			System.out.println(rs.toString());
			currencyRate=rs.getRates().get(0).getMid();
							
		}catch(CurrencyNotFoundException e){
			e.printStackTrace();
		}finally {
			con.disconnect();
		}
		
	}
	public HttpURLConnection getConn() {
		return null;
	}
}

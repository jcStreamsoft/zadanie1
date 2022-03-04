package zadanie1.model;

import java.util.ArrayList;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Response {
	@JacksonXmlProperty(localName = "Table")
	char table;
	@JacksonXmlProperty( localName = "Currency")
	String currency;
	@JacksonXmlProperty( localName = "Code")
	String code;
	@JacksonXmlProperty( localName = "Rates")
	ArrayList<Rate> rates;
	
	public Response() {
		super();
	}
	
	public char getTable(){
		return table;
	}

	public void setTable(char table) {
		this.table = table;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ArrayList<Rate> getRates() {
		return rates;
	}

	public void setRates(ArrayList<Rate> rates) {
		this.rates = rates;
	}

	@Override
	public String toString() {
		rates.stream().forEach(x->System.out.println(x.toString())); 
		return "Response [table=" + table + ", currency=" + currency + ", code=" + code + ", rates=" +  "]";
	}
	
	

}

package zadanie1.model.fileModel;

import java.math.BigDecimal;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Rate {

	@JacksonXmlProperty(localName = "Code")
	String code;
	@JacksonXmlProperty(localName = "Currency")
	String currency;
	@JacksonXmlProperty(localName = "Mid")
	BigDecimal mid;

	public Rate() {
		super();
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = new BigDecimal(mid);
	}
}

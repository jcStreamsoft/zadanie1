package zadanie1.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Rate {
	@JacksonXmlProperty( localName = "No")
	String no;
	@JacksonXmlProperty( localName = "EffectiveDate")
	LocalDate effectiveDate;
	@JacksonXmlProperty( localName = "Mid")
	BigDecimal mid;
	
	public Rate() {
		super();
	}
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public LocalDate getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String efectiveDate) {
		this.effectiveDate =  LocalDate.parse(efectiveDate);;
	}

	public BigDecimal getMid() {
		return mid;
	}

	public void setMid(BigDecimal mid) {
		this.mid = mid;
	}

	@Override
	public String toString() {
		return "Rate [no=" + no + ", efectiveDate=" + effectiveDate + ", mid=" + mid + "]";
	}
	
}

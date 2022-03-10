package zadanie1.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import zadanie1.enums.Currency;

public class Request {
	private LocalDate localDate;
	private BigDecimal value;
	private Currency currency;
	private String dataFormat;
	private String path;
	
	public Request(LocalDate localDate, BigDecimal value, Currency currency) {
		super();
		this.localDate = localDate;
		this.value = value;
		this.currency = currency;
	}
	public Request(BigDecimal value, Currency currency) {
		super();
		this.localDate = LocalDate.now();
		this.value = value;
		this.currency = currency;
	}
	public LocalDate getLocalDate() {
		return localDate;
	}
	public BigDecimal getValue() {
		return value;
	}
	public Currency getCurrency() {
		return currency;
	}
	public String getDataFormat() {
		return dataFormat;
	}
	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}
}

package zadanie1.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import zadanie1.enums.Currency;

public class RateData {
	private LocalDate date;
	private BigDecimal rate;
	private Currency currency;

	public RateData(LocalDate date, BigDecimal rate, Currency currency) {
		super();
		this.date = date;
		this.rate = rate;
		this.currency = currency;
	}

	public LocalDate getDate() {
		return date;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public Currency getCurrency() {
		return currency;
	}
}

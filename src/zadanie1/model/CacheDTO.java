package zadanie1.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CacheDTO {
	private String code;
	private BigDecimal rate;
	private LocalDate date;

	public CacheDTO(String code, BigDecimal rate, LocalDate date) {
		super();
		this.code = code;
		this.rate = rate;
		this.date = date;
	}

	public String getCode() {
		return code;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public LocalDate getDate() {
		return date;
	}

}

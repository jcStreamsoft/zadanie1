package zadanie1.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import zadanie1.enums.Currency;

public class Request {

	private LocalDate localDate;
	private BigDecimal value;
	private Currency currency;
	private String dataFormat;

	private Request() {
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

	public static Builder getBuilder(BigDecimal value, Currency currency) {
		return new Builder(value, currency);
	}

	public static class Builder {
		private LocalDate localDate;
		private BigDecimal value;
		private Currency currency;

		public Builder(BigDecimal value, Currency currency) {
			this.value = value;
			this.currency = currency;
		}

		public Builder localDate(LocalDate localDate) {
			this.localDate = localDate;
			return this;
		}

		public Request build() {
			Request request = new Request();
			request.value = this.value;
			request.currency = this.currency;
			if (localDate == null) {
				request.localDate = LocalDate.now();
			} else {
				request.localDate = this.localDate;
			}

			return request;
		}

	}
}

package zadanie1.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import zadanie1.enums.Currency;

public class Request {

	private LocalDate localDate;
	private BigDecimal value;
	private Currency currency;
	private String dataFormat;
	private String filePath;

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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public static class Builder {
		private LocalDate localDate;
		private BigDecimal value;
		private Currency currency;
		private String filePath;

		public Builder(BigDecimal value, Currency currency) {
			this.value = value;
			this.currency = currency;
		}

		public Builder localDate(LocalDate localDate) {
			this.localDate = localDate;
			return this;
		}

		public Builder filePath(String filePath) {
			this.filePath = filePath;
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
			if (filePath != null) {
				request.filePath = this.filePath;
			}
			return request;
		}

	}
}

package zadanie1.model;

public enum Currency {
	USD("usd"),
	PLN("pln"),
	EUR("eur");

	
	private String code;
	Currency(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}
}

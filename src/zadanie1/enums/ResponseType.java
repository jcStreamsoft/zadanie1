package zadanie1.enums;

public enum ResponseType{
	JSON("json"),
	XML("xml");
	private String type;

	private ResponseType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}

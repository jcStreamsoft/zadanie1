package zadanie1.connectors;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import zadanie1.enums.Currency;

public class UrlCreator {
	private final static String NBP_ADRESS = "http://api.nbp.pl/api/exchangerates/rates/a/";
	private Currency currency;
	private String responseType;
	private String newUrl;

	public UrlCreator(Currency currency, String reponseType) {
		super();
		this.currency = currency;
		this.responseType = reponseType;
	}

	public URL createLastRateUrl() {
		try {
			newUrl = new String(NBP_ADRESS + currency.getCode() + "/last/1/?format=" + responseType);
			return new URL(newUrl);
		} catch (MalformedURLException e) {
			return null;
		}
	}

	public URL createDateRateUrl(LocalDate localDate) {

		try {
			newUrl = new String(NBP_ADRESS + currency.getCode() + "/" + localDate + "/?format=" + responseType);
			return new URL(newUrl);
		} catch (MalformedURLException e) {
			return null;
		}
	}

}

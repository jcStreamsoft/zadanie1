package zadanie1.connectors;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import zadanie1.enums.Currency;
import zadanie1.exceptions.streamInputExceptions.CreatingURLException;

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

	public URL createLastRateUrl() throws CreatingURLException {
		try {
			checkResponseTypeNotNull();
			newUrl = new String(NBP_ADRESS + currency.getCode() + "/last/1/?format=" + responseType);
			return new URL(newUrl);
		} catch (MalformedURLException e) {
			throw new CreatingURLException("B³¹d linku " + newUrl, e);
		}
	}

	public URL createDateRateUrl(LocalDate localDate) throws CreatingURLException {

		try {
			checkResponseTypeNotNull();
			newUrl = new String(NBP_ADRESS + currency.getCode() + "/" + localDate + "/?format=" + responseType);
			return new URL(newUrl);
		} catch (MalformedURLException | NullPointerException e) {
			throw new CreatingURLException("B³¹d linku " + newUrl, e);
		}
	}

	private void checkResponseTypeNotNull() {
		if (responseType == null) {
			throw new NullPointerException();
		}
	}
}

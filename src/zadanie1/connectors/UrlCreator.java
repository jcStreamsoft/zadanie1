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

	public UrlCreator(Currency currency, String reponseType) {
		super();
		this.currency = currency;
		this.responseType = reponseType;
	}

	public URL createLastRateUrl() throws CreatingURLException {
		try {
			return new URL(NBP_ADRESS + currency.getCode() + "/last/1/?format=" + responseType);
		} catch (MalformedURLException e) {
			throw new CreatingURLException("Elementami linku nie mog¹ byæ wartoœci null");
		}
	}

	public URL createDateRateUrl(LocalDate localDate) throws CreatingURLException {
		try {
			return new URL(NBP_ADRESS + currency.getCode() + "/" + localDate + "/?format=" + responseType);
		} catch (MalformedURLException e) {
			throw new CreatingURLException("Elementami linku nie mog¹ byæ wartoœci null");
		}
	}
}

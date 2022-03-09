package zadanie1;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import zadanie1.enums.Currency;

public class UrlCreator {
	private final String NBP_ADRESS = "http://api.nbp.pl/api/exchangerates/rates/a/";
	private Currency currency;
	private String responseType;
	public UrlCreator(Currency currency, String reponseType) {
		super();
		this.currency = currency;
		this.responseType = reponseType;
	}
	public URL createLastRateUrl()throws MalformedURLException  {
		return new URL(NBP_ADRESS + currency.getCode() + "/last/1/?format=" + responseType);
	}
	public URL createDateRateUrl( LocalDate localDate)throws MalformedURLException  {
		return new URL(NBP_ADRESS + currency.getCode() + "/" + localDate + "/?format=" + responseType);

	}
	
}

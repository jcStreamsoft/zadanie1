package zadanie1.interfaces;

import java.time.LocalDate;

public interface Savable {
	public void saveData(String currencyCode, LocalDate date, String dataString);
}

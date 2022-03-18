package zadanie1;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import zadanie1.connectors.ApiConnection;
import zadanie1.connectors.CachedConnection;
import zadanie1.connectors.FileConnection;
import zadanie1.enums.Currency;
import zadanie1.interfaces.DataConnection;
import zadanie1.model.Request;
import zadanie1.parsers.apiParsers.ApiJsonParser;
import zadanie1.parsers.fileParsers.FileJsonParser;

public class Main {
	public static void main(String[] args) throws IOException {
		test1();

	}

	public static void test1() throws IOException {
		BigDecimal value = new BigDecimal(2);
		LocalDate date = LocalDate.parse("2002-01-02");

		List<DataConnection> connections = List.of(new ApiConnection(new ApiJsonParser()),
				new FileConnection(new FileJsonParser(), "fileOldArrayJson.txt"), new CachedConnection());
		Exchanger nbp = new Exchanger(connections);
		Request request = Request.getBuilder(value, Currency.EUR).date(date).build();

		BigDecimal result = nbp.exchangeToPln(request);
		System.out.println(result);

		Request request2 = Request.getBuilder(value, Currency.EUR).date(date.plusDays(1)).build();
		// BigDecimal result2 = nbp1.exchangeToPln(request2);
		// System.out.println(result2);
		nbp.printCache();
		// nbp1.printCache();
	}
}
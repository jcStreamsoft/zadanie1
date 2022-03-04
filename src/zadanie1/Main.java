package zadanie1;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.stream.Collectors;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import zadanie1.enums.Currency;
import zadanie1.enums.ResponseType;
import zadanie1.model.Response;

public class Main {
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		BigDecimal value = new BigDecimal(123.24);
		NbpCurrencyCalculator npb = new NbpCurrencyCalculator();
		BigDecimal result = npb.calculateFromPln(value, Currency.EUR, ResponseType.JSON, LocalDate.parse("2000-03-03"));
		BigDecimal result2 = npb.calculateFromPln(value, Currency.EUR, ResponseType.XML);
		System.out.println(result + " " + result2);
		
	}

}
package zadanie1.model;

import java.time.LocalDate;

import zadanie1.enums.Currency;

public class CacheKey {

	public LocalDate key1;
	public Currency key2;

	public CacheKey(LocalDate key1, Currency key2) {
		this.key1 = key1;
		this.key2 = key2;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

}

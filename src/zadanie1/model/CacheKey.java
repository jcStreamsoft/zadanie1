package zadanie1.model;

import java.time.LocalDate;
import java.util.Objects;

import zadanie1.enums.Currency;

public class CacheKey {

	public final LocalDate key1;
	public final Currency key2;
	private int hashCode;

	public CacheKey(LocalDate key1, Currency key2) {
		this.key1 = key1;
		this.key2 = key2;
		this.hashCode = Objects.hash(key1, key2);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		CacheKey that = (CacheKey) o;
		return key1 == that.key1 && key2 == that.key2;
	}

	@Override
	public int hashCode() {
		return this.hashCode;
	}

	@Override
	public String toString() {
		return "CacheKey [key1=" + key1 + ", key2=" + key2 + ", hashCode=" + hashCode + "]";
	}

}

package zadanie1.model;

import java.time.LocalDate;

public class CacheKey {

	public LocalDate key1;
	public String key2;

	public CacheKey(LocalDate key1, String key2) {
		this.key1 = key1;
		this.key2 = key2;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		CacheKey key = (CacheKey) o;
		if (key1 != null ? !key1.equals(key.key1) : key.key1 != null) {
			return false;
		}

		if (key2 != null ? !key2.equals(key.key2) : key.key2 != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = key1 != null ? key1.hashCode() : 0;
		result = 31 * result + (key2 != null ? key2.hashCode() : 0);
		return result;
	}

}

package zadanie1.interfaces;

import java.time.LocalDate;

public interface DateAlgorithm {
	static final int MAX_ATTEMPTS = 7;

	default void findExistingUrl(LocalDate localDate) {

		for (int i = 0; i < MAX_ATTEMPTS; i++) {

		}

	}

	public void checkIfExist();

	public void test();

}

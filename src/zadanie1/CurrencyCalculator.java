package zadanie1;

import java.math.BigDecimal;
import java.time.LocalDate;

import zadanie1.enums.Currency;

public class CurrencyCalculator {
	
	private BigDecimal value;
	private LocalDate time;
	private Currency currency;
	private boolean toPLN;
	private CurrencyCalculator() {};
	public static final class Builder{
		private BigDecimal value;
		private LocalDate time;
		private Currency currency;
		
		
		public Builder value(BigDecimal value) {
			this.value = value;
			return this;
		}
		public Builder time(LocalDate time) {
			this.time = time;
			return this;
		}
		public Builder currency(Currency currency){
			this.currency = currency;
			return this;
		}
		public CurrencyCalculator build() {
            if(value.compareTo(new BigDecimal(0.0)) < 0){
               value = new BigDecimal(1.0);
            }
            if(time == null || time.isBefore(LocalDate.now())){	
                time = LocalDate.now();
            }

            CurrencyCalculator calc = new CurrencyCalculator();
            calc.value = this.value;
            calc.time = this.time;
            calc.currency = this.currency;
            
            return calc;
        }
	}
}

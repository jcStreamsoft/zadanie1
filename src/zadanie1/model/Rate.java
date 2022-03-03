package zadanie1.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Rate {
	String no;
	String effectiveDate;
	BigDecimal mid;
	
	public Rate() {
		super();
	}
	
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String efectiveDate) {
		this.effectiveDate = efectiveDate;
	}

	public BigDecimal getMid() {
		return mid;
	}

	public void setMid(BigDecimal mid) {
		this.mid = mid;
	}

	@Override
	public String toString() {
		return "Rate [no=" + no + ", efectiveDate=" + effectiveDate + ", mid=" + mid + "]";
	}
	
}

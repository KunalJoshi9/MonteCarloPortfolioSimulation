package simulator;

import java.math.BigDecimal;

/**
 * This is an entity class that holds a projected portfolio value for a given
 * year at the given rate of return.
 * 
 * @author kunaljoshi
 *
 */
public class ProjectedPortfolioValue {
	private int yearNo;
	private BigDecimal rateOfReturn;
	private BigDecimal finalEndingValue;

	public ProjectedPortfolioValue(int yearNo, BigDecimal rateOfReturn, BigDecimal finalEndingValue) {
		super();
		this.yearNo = yearNo;
		this.rateOfReturn = rateOfReturn;
		this.finalEndingValue = finalEndingValue;
	}

	public int getYearNo() {
		return yearNo;
	}

	public void setYearNo(int yearNo) {
		this.yearNo = yearNo;
	}

	public BigDecimal getRateOfReturn() {
		return rateOfReturn;
	}

	public void setRateOfReturn(BigDecimal rateOfReturn) {
		this.rateOfReturn = rateOfReturn;
	}

	public BigDecimal getFinalEndingValue() {
		return finalEndingValue;
	}

	public void setFinalEndingValue(BigDecimal finalEndingValue) {
		this.finalEndingValue = finalEndingValue;
	}

	@Override
	public String toString() {
		return "Year No : " + yearNo + ", Rate of Return : " + rateOfReturn + ", Ending value : " + finalEndingValue;
	}
}

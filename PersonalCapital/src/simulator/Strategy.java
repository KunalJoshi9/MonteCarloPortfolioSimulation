package simulator;

/**
 * This class defines a strategy or style of investment with an input median
 * return and median risk (standard deviation) for the strategy
 * 
 * @author kunaljoshi
 *
 */
public class Strategy {
	private String name;
	private double meanReturn;
	private double risk;

	public Strategy(String name, double meanReturn, double risk) {
		super();
		this.name = name;
		this.meanReturn = meanReturn;
		this.risk = risk;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMeanReturn() {
		return meanReturn;
	}

	public void setMeanReturn(double meanReturn) {
		this.meanReturn = meanReturn;
	}

	public double getRisk() {
		return risk;
	}

	public void setRisk(double risk) {
		this.risk = risk;
	}

}

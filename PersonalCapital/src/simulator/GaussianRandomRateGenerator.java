package simulator;

import java.math.BigDecimal;
import java.util.Random;

/**
 * This class serves as a gaussian random rate generator.
 * 
 * @author kjoshi
 *
 */
public class GaussianRandomRateGenerator {
	public static BigDecimal PERCENT = BigDecimal.valueOf(100);
	private Random random = new Random();
	private BigDecimal mean;
	private BigDecimal standardDeviation;

	public GaussianRandomRateGenerator() {
		this(0.0, 1.0);
	}

	public GaussianRandomRateGenerator(Strategy strategy) {
		this(strategy.getMeanReturn(), strategy.getRisk());
	}

	public GaussianRandomRateGenerator(double mean, double standardDeviation) {
		this.mean = BigDecimal.valueOf(mean).divide(PERCENT, MonteCarloSimulator.MATH_CONTEXT);
		this.standardDeviation = BigDecimal.valueOf(standardDeviation).divide(PERCENT,
				MonteCarloSimulator.MATH_CONTEXT);
	}

	public BigDecimal getRandomSeed() {
		return BigDecimal.valueOf(random.nextGaussian());
	}

	public BigDecimal getMean() {
		return mean;
	}

	public void setMean(BigDecimal mean) {
		this.mean = mean;
	}

	public BigDecimal getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(BigDecimal standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	public BigDecimal getRandomRateOfReturn() {
		return getRandomSeed().multiply(getStandardDeviation(), MonteCarloSimulator.MATH_CONTEXT).add(getMean(),
				MonteCarloSimulator.MATH_CONTEXT);
	}
}

package simulator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

/**
 * This is the meat of the MonteCarloSimulation This class is reponsible for
 * performing the given number of simulations over a given tenure with an intial
 * amount and an inflation rate All these parameters are adjustable
 * 
 * @author kunaljoshi
 *
 */
public class MonteCarloSimulator {

	public static final MathContext MATH_CONTEXT = new MathContext(23, RoundingMode.HALF_EVEN);
	private GaussianRandomRateGenerator generator;

	private List<ProjectedPortfolioValue> simulatedValues = Lists.newArrayList();

	public MonteCarloSimulator(GaussianRandomRateGenerator generator) {
		this.generator = generator;
	}

	public GaussianRandomRateGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(GaussianRandomRateGenerator generator) {
		this.generator = generator;
	}

	public List<ProjectedPortfolioValue> getSimulatedValues() {
		return simulatedValues;
	}

	public void addSimlulatedValue(ProjectedPortfolioValue value) {
		this.simulatedValues.add(value);
	}

	public void addSimlulatedValues(List<ProjectedPortfolioValue> values) {
		this.simulatedValues.addAll(values);
	}

	public List<ProjectedPortfolioValue> simulateValues(int years, int simulations, int initialVal,
			double infaltionVal) {
		BigDecimal originalInitialValue = BigDecimal.valueOf(initialVal);
		BigDecimal inflation = BigDecimal.valueOf(infaltionVal);
		inflation = BigDecimal.ONE.add(inflation.divide(BigDecimal.valueOf(100)));
		GaussianRandomRateGenerator generator = getGenerator();
		Multimap<Integer, ProjectedPortfolioValue> map = LinkedListMultimap.create();
		for (int simulation = 0; simulation < simulations; simulation++) {
			BigDecimal initialValue = originalInitialValue;
			for (int year = 1; year <= years; year++) {
				BigDecimal rate = generator.getRandomRateOfReturn();
				BigDecimal endingValue = initialValue
						.multiply(BigDecimal.ONE.add(rate), MonteCarloSimulator.MATH_CONTEXT)
						.multiply(inflation, MonteCarloSimulator.MATH_CONTEXT);
				ProjectedPortfolioValue value = new ProjectedPortfolioValue(year, rate, endingValue);
				map.put(simulation, value);
				initialValue = endingValue;
			}
			ProjectedPortfolioValue simulatedValue = Lists.newArrayList(map.get(simulation)).get(years - 1);
			getSimulatedValues().add(simulatedValue);
		}
		return getSimulatedValues();
	}

	public BigDecimal median(List<ProjectedPortfolioValue> values) {
		double[] arr = new double[values.size()];
		int i = 0;
		for (ProjectedPortfolioValue value : values) {
			arr[i++] = value.getFinalEndingValue().doubleValue();
		}

		Median median = new Median();
		return BigDecimal.valueOf(median.evaluate(arr));
	}

	public BigDecimal percentile(List<ProjectedPortfolioValue> values, double percentile) {
		double[] arr = new double[values.size()];
		int i = 0;
		for (ProjectedPortfolioValue value : values) {
			arr[i++] = value.getFinalEndingValue().doubleValue();
		}

		Percentile percentileVal = new Percentile(percentile);
		return BigDecimal.valueOf(percentileVal.evaluate(arr));
	}
}

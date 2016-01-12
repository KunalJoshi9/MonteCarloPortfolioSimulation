package simulator;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

/**
 * This is the main class which performs MonteCarloSimulation using the
 * simulator for aggressive and conservative strategies and prints out the
 * median, best and worst case
 * 
 * @author kunaljoshi
 *
 */
public class MonteCarloSimulation {
	public static NumberFormat FORMAT = new DecimalFormat("$#,###,###,###.####");
	public static int YEARS = 20;
	public static int INITIAL_INVESTMENT = 100000;
	public static int NO_OF_SIMULATIONS = 10000;
	public static double inflation = 3.5;

	public static void main(String[] args) {
		Strategy aggressive = new Strategy("Aggressive", 9.4324, 15.675);
		GaussianRandomRateGenerator generator = new GaussianRandomRateGenerator(aggressive);
		MonteCarloSimulator simulator = new MonteCarloSimulator(generator);
		List<ProjectedPortfolioValue> aggressiveValues = simulator.simulateValues(YEARS, NO_OF_SIMULATIONS,
				INITIAL_INVESTMENT, inflation);
		BigDecimal aggressiveMedian = simulator.median(aggressiveValues);
		BigDecimal aggressiveBestCase = simulator.percentile(aggressiveValues, 90);
		BigDecimal aggressiveWorstCase = simulator.percentile(aggressiveValues, 10);

		print(aggressive, simulator, aggressiveValues, aggressiveMedian, aggressiveBestCase, aggressiveWorstCase);

		Strategy conservative = new Strategy("Conservative", 6.189, 6.3438);
		generator = new GaussianRandomRateGenerator(conservative);
		simulator = new MonteCarloSimulator(generator);
		List<ProjectedPortfolioValue> conservativeValues = simulator.simulateValues(YEARS, NO_OF_SIMULATIONS,
				INITIAL_INVESTMENT, inflation);
		BigDecimal conservativeMedian = simulator.median(conservativeValues);
		BigDecimal conservativeBestCase = simulator.percentile(conservativeValues, 90);
		BigDecimal conservativeWorstCase = simulator.percentile(conservativeValues, 10);

		print(conservative, simulator, conservativeValues, conservativeMedian, conservativeBestCase,
				conservativeWorstCase);
	}

	private static void print(Strategy strategy, MonteCarloSimulator simulator, List<ProjectedPortfolioValue> values,
			BigDecimal median, BigDecimal bestCase, BigDecimal worstCase) {
		System.out.println(strategy.getName().toUpperCase() + " :-");
		System.out.println("Median 20th Year : " + FORMAT.format(median));
		System.out.println("10% Best Case : " + FORMAT.format(bestCase));
		System.out.println("10% Worst Case : " + FORMAT.format(worstCase));
		System.out.println("*********************************************");
	}
}

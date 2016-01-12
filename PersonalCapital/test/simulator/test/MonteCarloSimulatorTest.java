package simulator.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import simulator.GaussianRandomRateGenerator;
import simulator.MonteCarloSimulator;
import simulator.ProjectedPortfolioValue;

/**
 * Tests for the simulator using mocks so that we can uniquely predict the
 * returns
 * 
 * @author kunaljoshi
 *
 */
public class MonteCarloSimulatorTest {

	private static MonteCarloSimulator simulator;

	private static GaussianRandomRateGenerator generator;

	@Before
	public void setup() {
		generator = EasyMock.createMock(GaussianRandomRateGenerator.class);
		simulator = new MonteCarloSimulator(generator);
	}

	/* Test Basics */
	@Test
	public void testBasic() {
		simulator.setGenerator(new GaussianRandomRateGenerator());
		Assert.assertEquals(new GaussianRandomRateGenerator().getMean(), simulator.getGenerator().getMean());
		Assert.assertEquals(new GaussianRandomRateGenerator().getStandardDeviation(),
				simulator.getGenerator().getStandardDeviation());
	}

	/*
	 * Test for single year with a single simulation at a fixed rate of return
	 */
	@Test
	public void testSimulatorForOneYear() {
		EasyMock.expect(generator.getRandomRateOfReturn()).andReturn(BigDecimal.valueOf(5));
		EasyMock.replay(generator);
		List<ProjectedPortfolioValue> values = simulator.simulateValues(1, 1, 100, 1);
		Assert.assertEquals(1, values.size());
		Assert.assertEquals(1, values.get(0).getYearNo());
		Assert.assertEquals(5, values.get(0).getRateOfReturn().intValue());
		Assert.assertEquals(606, values.get(0).getFinalEndingValue().intValue());
		EasyMock.verify(generator);
	}

	/*
	 * Test for multiple years with a multiple simulations at a fixed rate of
	 * return
	 */
	@Test
	public void testSimulatorForMultipleYears() {
		EasyMock.expect(generator.getRandomRateOfReturn()).andReturn(BigDecimal.valueOf(5)).times(10);
		EasyMock.replay(generator);
		List<ProjectedPortfolioValue> values = simulator.simulateValues(2, 5, 100, 1);
		Assert.assertEquals(5, values.size());
		for (int i = 0; i < 5; i++) {
			Assert.assertEquals(2, values.get(i).getYearNo());
			Assert.assertEquals(5, values.get(i).getRateOfReturn().intValue());
			Assert.assertEquals(3672, values.get(i).getFinalEndingValue().intValue());
		}
		simulator.addSimlulatedValue(new ProjectedPortfolioValue(10, BigDecimal.valueOf(10), BigDecimal.valueOf(1000)));
		Assert.assertEquals(6, simulator.getSimulatedValues().size());
		simulator.addSimlulatedValues(getProjectedPortfolioValues());
		Assert.assertEquals(11, simulator.getSimulatedValues().size());
		EasyMock.verify(generator);
	}

	/* Simple Test to calculate median */
	@Test
	public void testMedian() {
		List<ProjectedPortfolioValue> values = getProjectedPortfolioValues();
		BigDecimal median = simulator.median(values);
		Assert.assertEquals(100, median.intValue());
	}

	/* Simple Test to calculate percentile */
	@Test
	public void testPercentile() {
		List<ProjectedPortfolioValue> values = getProjectedPortfolioValues();
		BigDecimal bestCase = simulator.percentile(values, 90);
		Assert.assertEquals(115, bestCase.intValue());
	}

	private List<ProjectedPortfolioValue> getProjectedPortfolioValues() {
		List<ProjectedPortfolioValue> values = new ArrayList<ProjectedPortfolioValue>();
		values.add(new ProjectedPortfolioValue(1, BigDecimal.valueOf(5), BigDecimal.valueOf(100)));
		values.add(new ProjectedPortfolioValue(1, BigDecimal.valueOf(3), BigDecimal.valueOf(70)));
		values.add(new ProjectedPortfolioValue(1, BigDecimal.valueOf(8), BigDecimal.valueOf(115)));
		values.add(new ProjectedPortfolioValue(1, BigDecimal.valueOf(2), BigDecimal.valueOf(45)));
		values.add(new ProjectedPortfolioValue(1, BigDecimal.valueOf(7), BigDecimal.valueOf(110)));
		return values;
	}
}

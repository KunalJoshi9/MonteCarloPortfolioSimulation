package simulator.test;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import simulator.GaussianRandomRateGenerator;
import simulator.ProjectedPortfolioValue;
import simulator.Strategy;

/**
 * Tests for the GaussianRandomRateGenerator
 * 
 * @author kunaljoshi
 *
 */
public class GaussianRandomRateGeneratorTest {

	private static GaussianRandomRateGenerator generator = null;

	/* Testing Gaussian rate generator by itself with Mean=0 and SD=1 */
	@Test
	public void testRandomGenerator() {
		generator = new GaussianRandomRateGenerator();
		Assert.assertEquals(0, generator.getMean().intValue());
		Assert.assertEquals(1,
				generator.getStandardDeviation().multiply(GaussianRandomRateGenerator.PERCENT).intValue());
	}

	/* Testing Gaussian rate generator with a given strategy */
	@Test
	public void testGeneratorWithStrategy() {
		Strategy strategy = new Strategy("strategy", 15.0, 10.0);
		generator = new GaussianRandomRateGenerator(strategy);
		Assert.assertEquals("strategy", strategy.getName());
		Assert.assertEquals(15, (int) strategy.getMeanReturn());
		Assert.assertEquals(10, (int) strategy.getRisk());
		Assert.assertEquals(15, generator.getMean().multiply(GaussianRandomRateGenerator.PERCENT).intValue());
		Assert.assertEquals(10,
				generator.getStandardDeviation().multiply(GaussianRandomRateGenerator.PERCENT).intValue());
		Assert.assertNotEquals(generator.getRandomSeed(), generator.getRandomSeed());
		Assert.assertNotEquals(generator.getRandomRateOfReturn(), generator.getRandomRateOfReturn());
	}

	@Test
	public void testBasics() {
		GaussianRandomRateGenerator gen = new GaussianRandomRateGenerator();
		gen.setMean(BigDecimal.ONE);
		Assert.assertEquals(BigDecimal.ONE, gen.getMean());
		gen.setStandardDeviation(BigDecimal.TEN);
		Assert.assertEquals(BigDecimal.TEN, gen.getStandardDeviation());
	}
}

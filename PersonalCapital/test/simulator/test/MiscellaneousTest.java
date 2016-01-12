package simulator.test;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import simulator.ProjectedPortfolioValue;
import simulator.Strategy;

/**
 * Basic set of tests to check object sanity
 * 
 * @author kunaljoshi
 *
 */
public class MiscellaneousTest {

	@Test
	public void testStrategy() {
		Strategy strategy = new Strategy("name", 1.0, 9.0);
		strategy.setName("NameChanged");
		Assert.assertEquals("NameChanged", strategy.getName());
		strategy.setMeanReturn(5.0);
		Assert.assertEquals(new Double(5.0).doubleValue(), strategy.getMeanReturn(), 0);
		strategy.setRisk(7.0);
		Assert.assertEquals(new Double(7.0).doubleValue(), strategy.getRisk(), 0);
	}

	@Test
	public void testProjectedPortfolioValue() {
		ProjectedPortfolioValue value = new ProjectedPortfolioValue(1, BigDecimal.valueOf(5), BigDecimal.valueOf(100));
		value.setYearNo(2);
		Assert.assertEquals(2, value.getYearNo());
		value.setRateOfReturn(BigDecimal.ONE);
		Assert.assertEquals(BigDecimal.ONE, value.getRateOfReturn());
		value.setFinalEndingValue(BigDecimal.valueOf(200));
		Assert.assertEquals(BigDecimal.valueOf(200), value.getFinalEndingValue());
		String toString = "Year No : 2, Rate of Return : 1, Ending value : 200";
		Assert.assertArrayEquals(toString.toCharArray(), value.toString().toCharArray());
	}
}

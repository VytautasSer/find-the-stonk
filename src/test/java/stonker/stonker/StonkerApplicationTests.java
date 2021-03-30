package stonker.stonker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class StonkerApplicationTests {

	@Test
	void test() {
		System.out.println("55652222950004".length());


		ArrayList<Bar> bars = CSVparser.parse("TSLA.csv", YYYY_MM_DD);
		BarSeries series = new BaseBarSeriesBuilder().withBars(bars).withName("TSLA").build();

		ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
		// Here is the same close price:

		// Getting the simple moving average (SMA) of the close price over the last 5 ticks
		SMAIndicator shortSma = new SMAIndicator(closePrice, 5);

		// Getting a longer SMA (e.g. over the 30 last ticks)
		SMAIndicator longSma = new SMAIndicator(closePrice, 30);

		// Buying rules
		// We want to buy:
		//  - if the 5-ticks SMA crosses over 30-ticks SMA
		//  - or if the price goes below a defined price (e.g $800.00)
		Rule buyingRule = new CrossedUpIndicatorRule(shortSma, longSma);
//                .or(new CrossedDownIndicatorRule(closePrice, 800d));

		// Selling rules
		// We want to sell:
		//  - if the 5-ticks SMA crosses under 30-ticks SMA
		//  - or if the price loses more than 3%
		//  - or if the price earns more than 2%
		Rule sellingRule = new CrossedDownIndicatorRule(shortSma, longSma)
				.or(new StopLossRule(closePrice, 3.0))
				.or(new StopGainRule(closePrice, 2.0));

		Strategy strategy = new BaseStrategy(buyingRule, sellingRule);

		BarSeriesManager manager = new BarSeriesManager(series);
		TradingRecord tradingRecord = manager.run(strategy);
		System.out.println("Number of trades for our strategy: " + tradingRecord.getTradeCount());

		// Getting the profitable trades ratio
		AnalysisCriterion profitTradesRatio = new AverageProfitableTradesCriterion();
		System.out.println("Profitable trades ratio: " + profitTradesRatio.calculate(series, tradingRecord));
		// Getting the reward-risk ratio
		AnalysisCriterion rewardRiskRatio = new RewardRiskRatioCriterion();
		System.out.println("Reward-risk ratio: " + rewardRiskRatio.calculate(series, tradingRecord));

		// Total profit of our strategy
		// vs total profit of a buy-and-hold strategy
		AnalysisCriterion vsBuyAndHold = new VersusBuyAndHoldCriterion(new TotalProfitCriterion());
		System.out.println("Our profit vs buy-and-hold profit: " + vsBuyAndHold.calculate(series, tradingRecord));

	}

	void pitfalls() {
//        ~~Common errors I found which could result in loss:
//
//        *Overloads - this is when the brokers engine can't handle a huge influx of order requests, results in yours
//        being denied. Typically occurs where huge price drops/increases occur. You need to properly handle this in
//        your code.
//
//        *Random disconnection of websockets - again handling this correctly, to reconnect or not.
//
//        *Broker written code faults - I used to use the bitmex API, they offer a piece of market trader code on
//        GitHub which includes the websockets. Your broker may offer something similar but be wary as the code is
//        usually not maintained, the bitmex code was poor and commonly have delayed trade info (more than 20minutes behind.)
//
//        *Fees/hidden fees/leverage - if there want fees, the crypto market would be very different. If you think
//        you're on a fee free site for crypto trading, you're not. If you actually are then I doubt the volume is there
//        for succesful trading. If you do move to using leverage, do keep in mind that the fees will increase due to
//        the leverage as it's position size not margin that fees are calculated from.
//
//        *Volume - testing is great because the volumes there, live is a different story.
//        This is usually fine (no losses) but adds more work because now you need to have the code handle partial
//        orders and when to close partially filled orders.
//
//        *Hardware - if you're running a bit that checks every price then make sure to take advantage of asyncio or
//        threading so that your hardware doesn't burn out or your program doesn't hang. Google what asyncio is, there's
//        loads of info out there.
//
//        *Request limit - backtesting you have huge csv files to consolidate and test your models against. Live testing
//        you need to make requests for historical data and you're limited to a certain number of requests in a time frame.
//        Check this on your brokers website prior otherwise you'll write a tonne of code and then find its not
//        efficient enough for the request limiter.
	}

}

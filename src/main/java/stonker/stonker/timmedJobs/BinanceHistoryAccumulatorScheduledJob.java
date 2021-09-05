package stonker.stonker.timmedJobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import stonker.stonker.entities.Candlestick;
import stonker.stonker.historyProviders.BinanceHistoryProvider;

import java.util.List;

@Component
public class BinanceHistoryAccumulatorScheduledJob {

    @Autowired
    BinanceHistoryProvider binanceProvider;

    Logger log = LoggerFactory.getLogger(BinanceHistoryAccumulatorScheduledJob.class);

    @Scheduled(fixedRate = 100000)
    public void fetchHistory() {

        List<Candlestick> last500MinuteCandles = binanceProvider.getLastFiveHundredOneMinuteCandles(BinanceHistoryProvider.HistoryPairs.BTC);

        System.out.println(last500MinuteCandles.get(0));
    }

}

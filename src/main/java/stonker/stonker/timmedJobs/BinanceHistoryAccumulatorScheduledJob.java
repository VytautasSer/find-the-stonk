package stonker.stonker.timmedJobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import stonker.stonker.historyProviders.BinanceHistoryProvider;
import stonker.stonker.repositories.CandlestickRepository;

@Component
public class BinanceHistoryAccumulatorScheduledJob {

    @Autowired
    BinanceHistoryProvider binanceProvider;

    @Autowired
    CandlestickRepository repository;

    Logger log = LoggerFactory.getLogger(BinanceHistoryAccumulatorScheduledJob.class);

    @Scheduled(fixedRate = 100000)
    public void fetchHistory() {
        System.out.println("DB Test " + repository.findById(1L));

//        List<Candlestick> last500MinuteCandles = binanceProvider.getLastFiveHundredOneMinuteCandles(BinanceHistoryProvider.HistoryPairs.BTC);
    }

}

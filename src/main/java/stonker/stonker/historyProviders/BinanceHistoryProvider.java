package stonker.stonker.historyProviders;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.CandlestickInterval;
import org.springframework.stereotype.Service;
import stonker.stonker.entities.Candlestick;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BinanceHistoryProvider {

    public enum HistoryPairs {
        BTC,
        ETH,
        XRP
    }

    //TODO: add to secrets
    private final BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance("API-KEY", "TgIGN2f81ctAMrKocr7SzSEtQFwQN6OGt6gqBqrwvFPb8ToOf9W1X9mhPktB5ioo");

    /**
     * @return 500 last candles, 8,3 hours ~
     */
    public List<Candlestick> getLastFiveHundredOneMinuteCandles(HistoryPairs ticker) {
        BinanceApiRestClient client = factory.newRestClient();

        return client.getCandlestickBars(ticker + "USDT", CandlestickInterval.ONE_MINUTE)
                .stream()
                .map(candlestick -> mapToEntity(candlestick, ticker.toString()))
                .collect(Collectors.toList());
    }

    private static Candlestick mapToEntity(com.binance.api.client.domain.market.Candlestick apiDataStick, String ticker) {

        return new Candlestick(
                ticker,
                apiDataStick.getOpenTime(),
                Double.parseDouble(apiDataStick.getOpen()),
                Double.parseDouble(apiDataStick.getHigh()),
                Double.parseDouble(apiDataStick.getLow()),
                Double.parseDouble(apiDataStick.getClose()),
                Double.parseDouble(apiDataStick.getVolume()),
                Double.parseDouble(apiDataStick.getQuoteAssetVolume())
        );
    }

}

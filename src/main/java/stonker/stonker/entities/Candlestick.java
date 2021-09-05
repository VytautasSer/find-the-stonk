package stonker.stonker.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter @Setter @ToString @NoArgsConstructor
@Entity
public class Candlestick {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String ticker;
    private long openTime;
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;
    private double quoteAssetVolume;

    public Candlestick(String ticker, long openTime, double open, double high, double low, double close, double volume, double quoteAssetVolume) {
        this.ticker = ticker;
        this.openTime = openTime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.quoteAssetVolume = quoteAssetVolume;
    }
}



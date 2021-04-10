package stonker.stonker.services.utils;

import com.opencsv.CSVReader;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CsvParser {

    public static DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static ArrayList<Bar> parse(String fileName, DateTimeFormatter dateFormat) {
        InputStream stream = CsvParser.class.getClassLoader().getResourceAsStream(fileName);
        CSVReader csvReader = new CSVReader(new InputStreamReader(stream, StandardCharsets.UTF_8), ',', '"', 1);

        ArrayList<Bar> bars = new ArrayList<>();
        try {
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                ZonedDateTime date = LocalDate.parse(line[0], dateFormat).atStartOfDay(ZoneId.systemDefault());
                double open = Double.parseDouble(line[1]);
                double high = Double.parseDouble(line[2]);
                double low = Double.parseDouble(line[3]);
                double close = Double.parseDouble(line[4]);
                double volume = Double.parseDouble(line[5]);

                bars.add(new BaseBar(Duration.ofDays(1), date, open,high,low,close,volume));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return bars;
    }
}

package stonker.stonker.services.utils;

import stonker.stonker.entities.Tick;

import java.io.File;
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
import java.util.List;

public abstract class CsvParser {

    public static DateTimeFormatter YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static ArrayList<Bar> parse(String fileName, DateTimeFormatter dateFormat) {
        InputStream stream = CSVparser.class.getClassLoader().getResourceAsStream(fileName);
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

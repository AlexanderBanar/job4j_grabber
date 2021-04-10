package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {
    @Override
    public LocalDateTime parse(String parse) {
        return (parse.contains("сегодня") || parse.contains("вчера"))
                ? parseFromNames(parse)
                : LocalDateTime.parse(parse, formatterSupply());
    }

    private LocalDateTime parseFromNames(String parse) {
        LocalDateTime result = null;
        String[] splitString = parse.split(", |:");
        int hours = Integer.parseInt(splitString[1]);
        int minutes = Integer.parseInt(splitString[2]);
        LocalTime timing = LocalTime.of(hours, minutes);
        if (parse.contains("сегодня")) {
            result = LocalDateTime.of(LocalDate.now(), timing);
        }
        if (parse.contains("вчера")) {
            result = LocalDateTime.of(LocalDate.now().minusDays(1), timing);
        }
        return result;
    }

    private DateTimeFormatter formatterSupply() {
        return new DateTimeFormatterBuilder()
                .appendPattern("d ")
                .appendText(ChronoField.MONTH_OF_YEAR, monthsMapSupply())
                .appendPattern(" yy, ")
                .appendPattern("HH:mm")
                .toFormatter();
    }

    private Map<Long, String> monthsMapSupply() {
        Map<Long, String> rusMonCompMap = new HashMap<>();
        rusMonCompMap.put(1L, "янв");
        rusMonCompMap.put(2L, "фев");
        rusMonCompMap.put(3L, "мар");
        rusMonCompMap.put(4L, "апр");
        rusMonCompMap.put(5L, "май");
        rusMonCompMap.put(6L, "июн");
        rusMonCompMap.put(7L, "июл");
        rusMonCompMap.put(8L, "авг");
        rusMonCompMap.put(9L, "сен");
        rusMonCompMap.put(10L, "окт");
        rusMonCompMap.put(11L, "ноя");
        rusMonCompMap.put(12L, "дек");
        return rusMonCompMap;
    }
}

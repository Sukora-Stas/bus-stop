package util;

import model.BusCompany;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {

    public static final String COMPANY_POSH = "Posh";
    public static final String COMPANY_GROTTY = "Grotty";

    public static LocalTime parseTime(String dateText) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("HH:mm", Locale.ENGLISH);
        return LocalTime.parse(dateText, parser);
    }

    public static List<BusCompany> parseToObjects(Stream<String> lines) {
        return lines
                .map(row -> row.split("\n"))
                .map(BusCompany::new)
                .filter(Util::isNoLongerThenOneHour)
                .sorted(Comparator.comparing(BusCompany::getTimeDeparture))
                .collect(Collectors.toList());
    }

    public static boolean isNoLongerThenOneHour(BusCompany busCompany) {
        return Long.compare(Duration.between(busCompany.getTimeDeparture(), busCompany.getTimeArrival()).toMillis(),
                LocalTime.of(1, 0).toNanoOfDay() / 1000000) == -1;
    }

    public static List<BusCompany> getBusCompaniesByName(List<BusCompany> busCompanies, String name) {
        return busCompanies.stream()
                .filter(busCompany -> name.equals(busCompany.getCompanyName()))
                .sorted(Comparator.comparing(BusCompany::getTimeDeparture))
                .collect(Collectors.toList());
    }

    public static Stream<String> readFile(String path) throws IOException {
        return Files.lines(Paths.get(path), StandardCharsets.UTF_8);
    }

    public static String getPathToOutput(String path) {
        return Paths.get(path).getParent().toString();
    }
}

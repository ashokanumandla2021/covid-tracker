package io.ashok.covidtracker.services;

import io.ashok.covidtracker.models.LocationStat;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CovidCasesService {
    private static final String COVID_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<LocationStat> locationsStat = new ArrayList<>();

    public List<LocationStat> getLocationsStat() {
        return locationsStat;
    }

    public int getCurrentDayTotalCases() {
        return locationsStat.stream().mapToInt(LocationStat::getCurrentDayTotalCases).sum();
    }

    public int getTotalCasesIncreasedPerDay() {
        return locationsStat.stream().mapToInt(LocationStat::getIncreasedCasesInADay).sum();
    }

    @PostConstruct
    @Scheduled( cron = "* * 1 * * *" )
    public void getCovidData() throws IOException, InterruptedException {
        List<LocationStat> localLocationsStat = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(COVID_URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader reader = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
        for (CSVRecord record : records) {
            LocationStat locationStat = new LocationStat();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int currentDayTotalCases = Integer.parseInt(record.get(record.size() - 1));
            locationStat.setCurrentDayTotalCases(currentDayTotalCases);
            locationStat.setIncreasedCasesInADay(currentDayTotalCases - Integer.parseInt(record.get(record.size() - 2)));
            localLocationsStat.add(locationStat);
        }
        locationsStat = localLocationsStat;
    }
}

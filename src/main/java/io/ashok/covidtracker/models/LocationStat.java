package io.ashok.covidtracker.models;

public class LocationStat {
    private String state;
    private String country;
    private int currentDayTotalCases;
    private int increasedCasesInADay;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCurrentDayTotalCases() {
        return currentDayTotalCases;
    }

    public void setCurrentDayTotalCases(int currentDayTotalCases) {
        this.currentDayTotalCases = currentDayTotalCases;
    }

    public int getIncreasedCasesInADay() {
        return increasedCasesInADay;
    }

    public void setIncreasedCasesInADay(int increasedCasesInADay) {
        this.increasedCasesInADay = increasedCasesInADay;
    }
}

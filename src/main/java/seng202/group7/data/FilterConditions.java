package seng202.group7.data;

import java.io.Serializable;
import java.time.LocalDate;

public class FilterConditions implements Serializable {
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final String primaryDescription;
    private final String locationDescription;
    private final Integer ward;
    private final Integer beat;
    private final Boolean arrest;
    private final Boolean domestic;

    public FilterConditions(LocalDate dateFrom, LocalDate dateTo, String primaryDescription, String locationDescription,
                            Integer ward, Integer beat, Boolean arrest, Boolean domestic) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.primaryDescription = primaryDescription;
        this.locationDescription = locationDescription;
        this.ward = ward;
        this.beat = beat;
        this.arrest = arrest;
        this.domestic = domestic;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public String getPrimaryDescription() {
        return primaryDescription;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public Integer getWard() {
        return ward;
    }

    public Integer getBeat() {
        return beat;
    }

    public Boolean getArrest() {
        return arrest;
    }

    public Boolean getDomestic() {
        return domestic;
    }
}

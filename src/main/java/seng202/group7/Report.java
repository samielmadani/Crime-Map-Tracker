package seng202.group7;

import java.time.LocalDateTime;
import java.util.Objects;

abstract class Report {
    private LocalDateTime date = null;
    private String primaryDescription = null;
    private String secondaryDescription = null;
    private String locationDescription = null;
    private Boolean domestic = null;
    private Integer xCoord = null;
    private Integer yCoord = null;
    private Double latitude = null;
    private Double longitude = null;
    
    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPrimaryDescription() {
        return this.primaryDescription;
    }

    public void setPrimaryDescription(String primaryDescription) {
        if (Objects.equals(primaryDescription, "")) {
            this.primaryDescription = null;
        } else {
            this.primaryDescription = primaryDescription;
        }
    }

    public String getSecondaryDescription() {
        return this.secondaryDescription;
    }
    
    public void setSecondaryDescription(String secondaryDescription) {
        if (Objects.equals(secondaryDescription, "")) {
            this.secondaryDescription = null;
        } else {
            this.secondaryDescription = secondaryDescription;
        }
    }

    public String getLocationDescription() {
        return this.locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        if (Objects.equals(locationDescription, "")) {
            this.locationDescription = null;
        } else {
            this.locationDescription = locationDescription;
        }
    }

    public Boolean getDomestic() {
        return this.domestic;
    }

    public void setDomestic(Boolean domestic) {
        this.domestic = domestic;
    }

    public Integer getXCoord() {
        return this.xCoord;
    }

    public void setXCoord(Integer xCoord) {
        this.xCoord = xCoord;
    }

    public Integer getYCoord() {
        return this.yCoord;
    }

    public void setYCoord(Integer yCoord) {
        this.yCoord = yCoord;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double Longitude) {
        this.longitude = Longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(date, report.date) 
            && Objects.equals(primaryDescription, report.primaryDescription) 
            && Objects.equals(secondaryDescription, report.secondaryDescription) 
            && Objects.equals(locationDescription, report.locationDescription) 
            && Objects.equals(domestic, report.domestic) 
            && Objects.equals(xCoord, report.xCoord) 
            && Objects.equals(yCoord, report.yCoord) 
            && Objects.equals(latitude, report.latitude) 
            && Objects.equals(longitude, report.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, primaryDescription, secondaryDescription, locationDescription, domestic, xCoord, yCoord, latitude, longitude);
    }

    @Override
    public String toString() {
        return "date=" + date +
                ", primaryDescription='" + primaryDescription + '\'' +
                ", secondaryDescription='" + secondaryDescription + '\'' +
                ", locationDescription='" + locationDescription + '\'' +
                ", domestic=" + domestic +
                ", xCoord=" + xCoord +
                ", yCoord=" + yCoord +
                ", latitude=" + latitude +
                ", longitude=" + longitude
                ;
    }
}
package seng202.group7;

import java.time.LocalDateTime;

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
        this.primaryDescription = primaryDescription;
    }

    public String getSecondaryDescription() {
        return this.secondaryDescription;
    }
    
    public void setSecondaryDescription(String secondaryDescription) {
        this.secondaryDescription = secondaryDescription;
    }

    public String getLocationDescription() {
        return this.locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
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
}
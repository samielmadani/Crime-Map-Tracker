package seng202.group7;

import java.util.Date;

abstract class Report {
    private Date date = null;
    private String primaryDescription = null;
    private String secondaryDescription = null;
    private String locationDescription = null;
    private Boolean domestic = null; // Probably not used by user
    private int xCoord = -1;
    private int yCoord = -1;
    private Double latitude = null;
    private Double longitude = null;
    
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
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

    public int getXCoord() {
        return this.xCoord;
    }

    public void setXCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getYCoord() {
        return this.yCoord;
    }

    public void setYCoord(int yCoord) {
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
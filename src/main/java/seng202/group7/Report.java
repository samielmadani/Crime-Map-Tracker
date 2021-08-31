package seng202.group7;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

import javax.naming.directory.InvalidAttributeValueException;

abstract class Report {
    private LocalDateTime date;
    private String primaryDescription;
    private String secondaryDescription;
    private String locationDescription;
    private Boolean domestic = null;
    private Integer xCoord = null;
    private Integer yCoord = null;
    private Double latitude = null;
    private Double longitude = null;

    /**
     * Sets the data for all attributes in the report
     * @param date
     * @param primaryDescription
     * @param secondaryDescription
     * @param locationDescription
     * @param domestic
     * @param xCoord
     * @param yCoord
     * @param latitude
     * @param longitude
     */
    public Report(LocalDateTime date, String primaryDescription, String secondaryDescription, String locationDescription,
            Boolean domestic, Integer xCoord, Integer yCoord, Double latitude, Double longitude) {
        this.date = date;
        this.primaryDescription = primaryDescription;
        this.secondaryDescription = secondaryDescription;
        this.locationDescription = locationDescription;
        this.domestic = domestic;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * 
     * @return An ArrayList containing what types of attributes are in the report
     */
    public abstract ArrayList<String> getSchema();
    
    /**
     * @return An ArrayList containing all of the report's attributes
     */
    public abstract ArrayList<String> getAttributes();
    
    /**
     * 
     * @return The date the report was submitted
     */
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Sets the date the report was submitted
     * @param date A required LocalDateTime attribute which must be unique
     * @throws InvalidAttributeValueException If the attribute is null
     */
    public void setDate(LocalDateTime date) throws InvalidAttributeValueException {
        if (date == null) {
            throw new InvalidAttributeValueException("Date cannot be null");
        }
        this.date = date;
    }

    /**
     * 
     * @return The primary description of the report
     */
    public String getPrimaryDescription() {
        return this.primaryDescription;
    }

    /**
     * Sets the primary description of the report
     * @param primaryDescription A required String attribute which must be unique
     * @throws InvalidAttributeValueException If the attribute is null
     */
    public void setPrimaryDescription(String primaryDescription) throws InvalidAttributeValueException {
        if (Objects.equals(primaryDescription, "") || (primaryDescription == null)) {
            throw new InvalidAttributeValueException("Primary description cannot be null");
        } else {
            this.primaryDescription = primaryDescription;
        }
    }

    /**
     * 
     * @return The secondary description of the report
     */
    public String getSecondaryDescription() {
        return this.secondaryDescription;
    }
    
    /**
     * Sets the secondary description of the report
     * @param secondaryDescription A required String attribute which must be unique
     * @throws InvalidAttributeValueException If the attribute is null
     */
    public void setSecondaryDescription(String secondaryDescription) throws InvalidAttributeValueException {
        if (Objects.equals(secondaryDescription, "") || (secondaryDescription == null)) {
            throw new InvalidAttributeValueException("Secondary description cannot be null");
        } else {
            this.secondaryDescription = secondaryDescription;
        }
    }

    /**
     * 
     * @return The description of the location for the report
     */
    public String getLocationDescription() {
        return this.locationDescription;
    }

    /**
     * Set the locations description for the report
     * @param locationDescription
     */
    public void setLocationDescription(String locationDescription) {
        if (Objects.equals(locationDescription, "")) {
            this.locationDescription = null;
        } else {
            this.locationDescription = locationDescription;
        }
    }

    /**
     * 
     * @return If the report was domestic
     */
    public Boolean getDomestic() {
        return this.domestic;
    }

    /**
     * Sets if the report was domestic
     * @param domestic
     */
    public void setDomestic(Boolean domestic) {
        this.domestic = domestic;
    }

    /**
     * 
     * @return The x-coordinate for the location the report took place
     */
    public Integer getXCoord() {
        return this.xCoord;
    }

    /**
     * Set the x-coordinate of where the report took place
     * @param xCoord
     */
    public void setXCoord(Integer xCoord) {
        this.xCoord = xCoord;
    }

    /**
     * 
     * @return The y-coordinate for the location the report took place
     */
    public Integer getYCoord() {
        return this.yCoord;
    }

    /**
     * Set the y-coordinate of where the report took place
     * @param yCoord
     */
    public void setYCoord(Integer yCoord) {
        this.yCoord = yCoord;
    }

    /**
     *  
     * @return The geographical latitude of where the report took place
     */
    public Double getLatitude() {
        return this.latitude;
    }

    /**
     * Set the geographical latitude of where the report took place
     * @param latitude
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     *  
     * @return The geographical longitude of where the report took place
     */
    public Double getLongitude() {
        return this.longitude;
    }

    /**
     * Set the geographical longitude of where the report took place
     * @param Longitude
     */
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
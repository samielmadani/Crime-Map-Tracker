package seng202.group7;

import javafx.beans.property.*;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Report {
    private LocalDateTime date = null;
    private SimpleStringProperty primaryDescription = new SimpleStringProperty(null);
    private SimpleStringProperty secondaryDescription = new SimpleStringProperty(null);
    private SimpleStringProperty locationDescription = new SimpleStringProperty(null);
    private SimpleObjectProperty<Boolean> domestic = new SimpleObjectProperty<>(null);
    private SimpleObjectProperty<Integer> xCoord = new SimpleObjectProperty<>(null);
    private SimpleObjectProperty<Integer> yCoord = new SimpleObjectProperty<>(null);
    private SimpleObjectProperty<Double> latitude = new SimpleObjectProperty<>(null);
    private SimpleObjectProperty<Double> longitude = new SimpleObjectProperty<>(null);
    
    public LocalDateTime getDate() { return this.date; }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getPrimaryDescription() {
        return this.primaryDescription.get();
    }

    public void setPrimaryDescription(String primaryDescription) {
        if (Objects.equals(primaryDescription, "")) {
            this.primaryDescription.setValue(null);
        } else {
            this.primaryDescription.setValue(primaryDescription);
        }
    }

    public String getSecondaryDescription() {
        return this.secondaryDescription.get();
    }
    
    public void setSecondaryDescription(String secondaryDescription) {
        if (Objects.equals(secondaryDescription, "")) {
            this.secondaryDescription.setValue(null);
        } else {
            this.secondaryDescription.setValue(secondaryDescription);
        }
    }

    public String getLocationDescription() {
        return this.locationDescription.get();
    }

    public void setLocationDescription(String locationDescription) {
        if (Objects.equals(locationDescription, "")) {
            this.locationDescription.setValue(null);
        } else {
            this.locationDescription.setValue(locationDescription);
        }
    }

    public Boolean getDomestic() {
        return this.domestic.get();
    }

    public void setDomestic(Boolean domestic) {
        this.domestic.setValue(domestic);
    }

    public Integer getXCoord() {
        return this.xCoord.get();

    }

    public void setXCoord(Integer xCoord) { this.xCoord.setValue(xCoord);}

    public Integer getYCoord() {
        return this.yCoord.get();
    }

    public void setYCoord(Integer yCoord) { this.yCoord.setValue(yCoord); }

    public Double getLatitude() {
        return this.latitude.get();
    }

    public void setLatitude(Double latitude) { this.latitude.setValue(latitude); }

    public Double getLongitude() {
        return this.longitude.get();
    }

    public void setLongitude(Double longitude) { this.longitude.setValue(longitude); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Report report = (Report) o;
        return Objects.equals(date, report.getDate())
            && Objects.equals(primaryDescription.get(), report.getPrimaryDescription())
            && Objects.equals(secondaryDescription.get(), report.getSecondaryDescription())
            && Objects.equals(locationDescription.get(), report.getLocationDescription())
            && Objects.equals(domestic.get(), report.getDomestic())
            && Objects.equals(xCoord.get(), report.getXCoord())
            && Objects.equals(yCoord.get(), report.getYCoord())
            && Objects.equals(latitude.get(), report.getLatitude())
            && Objects.equals(longitude.get(), report.getLongitude());
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
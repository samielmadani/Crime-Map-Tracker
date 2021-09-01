package seng202.group7;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.util.Objects;

public class Crime extends Report {
    private SimpleStringProperty caseNumber = new SimpleStringProperty(null);
    private SimpleStringProperty block = new SimpleStringProperty(null);
    private SimpleStringProperty iucr = new SimpleStringProperty(null);
    private SimpleStringProperty fbiCD = new SimpleStringProperty(null);
    private SimpleObjectProperty<Boolean> arrest = new SimpleObjectProperty<>(null);
    private SimpleObjectProperty<Integer> beat = new SimpleObjectProperty<>(null);
    private SimpleObjectProperty<Integer> ward = new SimpleObjectProperty<>(null);


    /**
     * Initialises a crime object. \n
     * Note: Can currently only create complete classes
     * @param caseNumber
     * @param date
     * @param block
     * @param iucr
     * @param primaryDescription
     * @param secondaryDescription
     * @param locationDescription
     * @param arrest
     * @param domestic
     * @param beat
     * @param ward
     * @param fbiCD
     * @param xCoord
     * @param yCoord
     * @param latitude
     * @param longitude
     */
    public Crime(String caseNumber, LocalDateTime date, String block, String iucr, String primaryDescription,
    String secondaryDescription, String locationDescription, Boolean arrest, Boolean domestic, Integer beat,
    Integer ward, String fbiCD, Integer xCoord, Integer yCoord, Double latitude, Double longitude) {
        setCaseNumber(caseNumber);
        setDate(date);
        setBlock(block);
        setIucr(iucr);
        setPrimaryDescription(primaryDescription);
        setSecondaryDescription(secondaryDescription);
        setLocationDescription(locationDescription);
        setArrest(arrest);
        setDomestic(domestic);
        setBeat(beat);
        setWard(ward);
        setFbiCD(fbiCD);
        setXCoord(xCoord);
        setYCoord(yCoord);
        setLatitude(latitude);
        setLongitude(longitude);
    }
    public Crime() {}

    public String getCaseNumber() {
        return this.caseNumber.get();
    }

    public void setCaseNumber(String caseNumber) {
        if (Objects.equals(caseNumber, "")) {
            this.caseNumber.setValue(null);
        } else {
            this.caseNumber.setValue(caseNumber);
        }
    }

    public String getBlock() {
        return this.block.get();
    }

    public void setBlock(String block) {
        if (Objects.equals(block, "")) {
            this.block.setValue(null);
        } else {
            this.block.setValue(block);
        }
    }

    public String getIucr() {
        return this.iucr.get();
    }

    public void setIucr(String iucr) {
        if (Objects.equals(iucr, "")) {
            this.iucr.setValue(null);
        } else {
            this.iucr.setValue(iucr);
        }
    }

    public Boolean getArrest() {
        return this.arrest.get();
    }

    public void setArrest(Boolean arrest) {
        this.arrest.setValue(arrest);
    }

    public Integer getBeat() {
        return this.beat.get();
    }

    public void setBeat(Integer beat) {
        this.beat.setValue(beat);
    }

    public Integer getWard() {
        return this.ward.get();
    }

    public void setWard(Integer ward) {
        this.ward.setValue(ward);

    }

    public String getFbiCD() {
        return this.fbiCD.get();
    }

    public void setFbiCD(String fbiCD) {
        if (Objects.equals(fbiCD, "")) {
            this.fbiCD.setValue(null);
        } else {
            this.fbiCD.setValue(fbiCD);
        }
    }

    //TODO Remove when this method becomes unnecessary, move to tests.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Crime crime = (Crime) o;
        return Objects.equals(arrest.get(), crime.getArrest())
            && Objects.equals(beat.get(), crime.getBeat())
            && Objects.equals(ward.get(), crime.getWard())
            && Objects.equals(caseNumber.get(), crime.getCaseNumber())
            && Objects.equals(block.get(), crime.getBlock())
            && Objects.equals(iucr.get(), crime.getIucr())
            && Objects.equals(fbiCD.get(), crime.getFbiCD());
    }

    @Override
    public String toString() {
        return "Crime{" +
                "caseNumber='" + caseNumber + '\'' +
                ", block='" + block + '\'' +
                ", iucr='" + iucr + '\'' +
                ", arrest=" + arrest +
                ", beat=" + beat +
                ", ward=" + ward +
                ", fbiCD='" + fbiCD + '\'' +
                super.toString() + '\'' +
                '}';
    }
}
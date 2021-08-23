package seng202.group7;

import java.io.Console;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class Crime extends Report {
//TODO: change to wrappers?
    private String caseNumber = null;
    private String block = null;
    private String iucr = null;
    private Boolean arrest = null;
    private Integer beat = null;
    private Integer ward = null;
    private String fbiCD = null;

    /**
     * Initilises a crime object. \n
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
        setiucr(iucr);
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

    public Crime() {
        
    }

    public String getCaseNumber() {
        return this.caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        if (Objects.equals(caseNumber, "")) {
            this.caseNumber = null;
        } else {
            this.caseNumber = caseNumber;
        }
    }

    public String getBlock() {
        return this.block;
    }

    public void setBlock(String block) {
        if (Objects.equals(block, "")) {
            this.block = null;
        } else {
            this.block = block;
        }
    }

    public String getiucr() {
        return this.iucr;
    }

    public void setiucr(String iucr) {
        if (Objects.equals(iucr, "")) {
            this.iucr = null;
        } else {
            this.iucr = iucr;
        }
    }

    public Boolean getArrest() {
        return this.arrest;
    }

    public void setArrest(Boolean arrest) {
        this.arrest = arrest;
    }

    public Integer getBeat() {
        return this.beat;
    }

    public void setBeat(Integer beat) {
        this.beat = beat;
    }

    public Integer getWard() {
        return this.ward;
    }

    public void setWard(Integer ward) {
        this.ward = ward;

    }

    public String getFbiCD() {
        return this.fbiCD;
    }

    public void setFbiCD(String fbiCD) {
        if (Objects.equals(fbiCD, "")) {
            this.fbiCD = null;
        } else {
            this.fbiCD = fbiCD;
        }
    }

    public ArrayList<String> getAttributes() {
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add(caseNumber);
        attributes.add(toString(getDate()));
        attributes.add(block);
        attributes.add(iucr);
        attributes.add(getPrimaryDescription());
        attributes.add(getSecondaryDescription());
        attributes.add(getLocationDescription());
        attributes.add(toString(arrest));
        attributes.add(toString(getDomestic()));
        attributes.add(toString(beat));
        attributes.add(toString(ward));
        attributes.add(fbiCD);
        attributes.add(toString(getXCoord()));
        attributes.add(toString(getYCoord()));
        attributes.add(toString(getLatitude()));
        attributes.add(toString(getLongitude()));
        return attributes;
    }

    private String toString(Object o) {
        if (o != null) {
            if (o.getClass() == LocalDateTime.class) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a", Locale.US);
                return ((LocalDateTime) o).format(formatter);
            }
            return o.toString();
        } else {
            return "";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        
        Crime crime = (Crime) o;
        return Objects.equals(arrest, crime.arrest)
            && Objects.equals(beat, crime.beat)
            && Objects.equals(ward, crime.ward)
            && Objects.equals(caseNumber, crime.caseNumber)
            && Objects.equals(block, crime.block)
            && Objects.equals(iucr, crime.iucr)
            && Objects.equals(fbiCD, crime.fbiCD);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), caseNumber, block, iucr, arrest, beat, ward, fbiCD);
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
                ", fbi CD='" + fbiCD + '\'' +
                super.toString() + '\'' +
                '}';
    }
}
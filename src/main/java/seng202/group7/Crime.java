package seng202.group7;

import java.time.LocalDateTime;
import java.util.Objects;

public class Crime extends Report {
//TODO: change to wrappers?
    private String caseNumber = null;
    private String block = null;
    private String iucr = null;
    private boolean arrest = false;
    private int beat = -1;
    private int ward = -1;
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
    String secondaryDescription, String locationDescription, boolean arrest, Boolean domestic, int beat,
    int ward, String fbiCD, Integer xCoord, Integer yCoord, Double latitude, Double longitude) {
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
        this.caseNumber = caseNumber;
    }

    public String getBlock() {
        return this.block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getiucr() {
        return this.iucr;
    }

    public void setiucr(String iucr) {
        this.iucr = iucr;
    }

    public boolean getArrest() {
        return this.arrest;
    }

    public void setArrest(boolean arrest) {
        this.arrest = arrest;
    }

    public int getBeat() {
        return this.beat;
    }

    public void setBeat(int beat) {
        this.beat = beat;
    }

    public int getWard() {
        return this.ward;
    }

    public void setWard(int ward) {
        this.ward = ward;
    }

    public String getFbiCD() {
        return this.fbiCD;
    }

    public void setFbiCD(String fbiCD) {
        this.fbiCD = fbiCD;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Crime crime = (Crime) o;
        return arrest == crime.arrest && beat == crime.beat && ward == crime.ward && Objects.equals(caseNumber, crime.caseNumber) && Objects.equals(block, crime.block) && Objects.equals(iucr, crime.iucr) && Objects.equals(fbiCD, crime.fbiCD);
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
                ", fbiCD='" + fbiCD + '\'' +
                super.toString() + '\'' +
                '}';
    }
}
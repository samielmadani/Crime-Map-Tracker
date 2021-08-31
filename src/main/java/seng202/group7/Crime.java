package seng202.group7;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class Crime extends Report {
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
        super(date, primaryDescription, secondaryDescription, locationDescription, domestic, xCoord, yCoord, latitude, longitude);
        this.caseNumber = caseNumber;
        this.block = block;
        this.iucr = iucr;
        this.arrest = arrest;
        this.beat = beat;
        this.ward = ward;
        this.fbiCD = fbiCD;
    }

    /**
     * Creates an crime class with all atributes initilised to null.
     */
    public Crime() {
        super();
    }

    /**
     * 
     * @return The crime's case number
     */
    public String getCaseNumber() {
        return this.caseNumber;
    }

    /**
     * Sets the crimes case number, handles an empty string as null
     * @param caseNumber
     */
    public void setCaseNumber(String caseNumber) {
        if (Objects.equals(caseNumber, "")) {
            this.caseNumber = null;
        } else {
            this.caseNumber = caseNumber;
        }
    }

    /**
     * 
     * @return The block the crime occured in
     */
    public String getBlock() {
        return this.block;
    }

    /**
     * Sets the block the crime occured in, handles an empty string as null
     * @param block
     */
    public void setBlock(String block) {
        if (Objects.equals(block, "")) {
            this.block = null;
        } else {
            this.block = block;
        }
    }

    /**
     * 
     * @return The crime's Illinois Uniform Crime Reporting number
     */
    public String getIucr() {
        return this.iucr;
    }

    /**
     * Sets the crime's Illinois Uniform Crime Reporting number, handles an empty string as null
     * @param iucr
     */
    public void setIucr(String iucr) {
        if (Objects.equals(iucr, "")) {
            this.iucr = null;
        } else {
            this.iucr = iucr;
        }
    }

    /**
     * 
     * @return Whether the crime resulted in an arrest
     */
    public Boolean getArrest() {
        return this.arrest;
    }

    /**
     * Sets whether the crime resulted in an arrest
     * @param arrest
     */
    public void setArrest(Boolean arrest) {
        this.arrest = arrest;
    }

    /**
     * 
     * @return What beat the crime was in
     */
    public Integer getBeat() {
        return this.beat;
    }

    /**
     * Sets what beat the crime was in
     * @param beat
     */
    public void setBeat(Integer beat) {
        this.beat = beat;
    }

    /**
     * 
     * @return What ward the crime was in
     */
    public Integer getWard() {
        return this.ward;
    }

    /**
     * Sets what ward the crime was in
     * @param ward
     */
    public void setWard(Integer ward) {
        this.ward = ward;

    }

    /**
     * 
     * @return The crimes FBI CD number
     */
    public String getFbiCD() {
        return this.fbiCD;
    }

    /**
     * Sets the crime's FIB CD number, handles an empty string as null
     * @param fbiCD
     */
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

    /**
     * 
     */
    public ArrayList<String> getSchema() {
        ArrayList<String> schema = new ArrayList<>();
        schema.add("CASE NUMBER");
        schema.add("DATE");
        schema.add("BLOCK");
        schema.add("IUCR");
        schema.add("PRIMARY DESCRIPTION");
        schema.add("SECONDARY DESCRIPTION");
        schema.add("LOCATION DESCRIPTION");
        schema.add("ARREST");
        schema.add("DOMESTIC");
        schema.add("BEAT");
        schema.add("WARD");
        schema.add("FBI CD");
        schema.add("X COORDINATE");
        schema.add("Y COORDINATE");
        schema.add("LATITUDE");
        schema.add("LONGITUDE");
        return schema;
    }

    /**
     * Used to check if the object is null to prevent errors and format the date in the correct way.
     * @param o The object that is to be converted to a string
     * @return The string version of the input
     */
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
package seng202.group7;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import javax.naming.directory.InvalidAttributeValueException;

public class Crime extends Report {
    private SimpleStringProperty caseNumber = new SimpleStringProperty(null);
    private SimpleStringProperty block = new SimpleStringProperty(null);
    private SimpleStringProperty iucr = new SimpleStringProperty(null);
    private SimpleStringProperty fbiCD = new SimpleStringProperty(null);
    private SimpleObjectProperty<Boolean> arrest = new SimpleObjectProperty<>(null);
    private SimpleObjectProperty<Integer> beat = new SimpleObjectProperty<>(null);
    private SimpleObjectProperty<Integer> ward = new SimpleObjectProperty<>(null);


    /**
     * Initializes a crime object.
     * Note: Can currently only create complete classes
     * @param caseNumber A required field which must be unique and cannot be null
     * @param date A required field which contains the year, month, day and time of the crime
     * @param block An optional field which contains what block the crime occurred in
     * @param iucr An optional field which contains the Illinois Uniform Crime Reporting number
     * @param primaryDescription A required field which contains the primary description of the crime
     * @param secondaryDescription A required field which contains the more descriptive secondary description of the crime
     * @param locationDescription An optional field which contains the description of the location where the crime occurred
     * @param arrest An optional field which contains whether the crime resulted in an arrest
     * @param domestic An optional field which contains if the crime was domestic
     * @param beat An optional field which contains TODO what is this
     * @param ward An optional field which contains TODO what is this
     * @param fbiCD An optional field which contains TODO what is this
     * @param xCoord An optional field which is has the x-coordinate of where the crime occurred
     * @param yCoord An optional field which is has the y-coordinate of where the crime occurred
     * @param latitude An optional field which is has the latitude of where the crime occurred
     * @param longitude An optional field which is has the longitude of where the crime occurred
     */
    public Crime(String caseNumber, LocalDateTime date, String block, String iucr, String primaryDescription,
    String secondaryDescription, String locationDescription, Boolean arrest, Boolean domestic, Integer beat,
    Integer ward, String fbiCD, Integer xCoord, Integer yCoord, Double latitude, Double longitude) {
        super(date, primaryDescription, secondaryDescription, locationDescription, domestic, xCoord, yCoord, latitude, longitude);
        this.caseNumber = new SimpleStringProperty(caseNumber);
        this.block.setValue(block);
        this.iucr.setValue(iucr);
        this.arrest.setValue(arrest);
        this.beat.setValue(beat);
        this.ward.setValue(ward);
        this.fbiCD.setValue(fbiCD);
    }

    /**
     * 
     * @return The crime's case number
     */
    public String getCaseNumber() {
        return this.caseNumber.get();
    }


    /**
     * Sets the crimes case number, handles an empty string as null
     * @param caseNumber A required string attribute which must be unique
     * @throws InvalidAttributeValueException
     */
    public void setCaseNumber(String caseNumber) throws InvalidAttributeValueException {
        if (Objects.equals(caseNumber, "") || (caseNumber == null)) {
            throw new InvalidAttributeValueException();
        } else {
            this.caseNumber.setValue(caseNumber);
        }
    }

    /**
     * 
     * @return The block the crime occurred in
     */
    public String getBlock() {
        return this.block.get();
    }

    /**
     * Sets the block the crime occurred in, handles an empty string as null
     * @param block
     */
    public void setBlock(String block) {
        if (Objects.equals(block, "")) {
            this.block.setValue(null);
        } else {
            this.block.setValue(block);
        }
    }


    /**
     * 
     * @return The crime's Illinois Uniform Crime Reporting number
     */
    public String getIucr() {
        return this.iucr.get();
    }


    /**
     * Sets the crime's Illinois Uniform Crime Reporting number, handles an empty string as null
     * @param iucr
     */
    public void setIucr(String iucr) {
        if (Objects.equals(iucr, "")) {
            this.iucr.setValue(null);
        } else {
            this.iucr.setValue(iucr);
        }
    }

    /**
     * 
     * @return Whether the crime resulted in an arrest
     */
    public Boolean getArrest() {
        return this.arrest.get();
    }

    /**
     * Sets whether the crime resulted in an arrest
     * @param arrest
     */
    public void setArrest(Boolean arrest) {
        this.arrest.setValue(arrest);
    }

    /**
     * 
     * @return What beat the crime was in
     */
    public Integer getBeat() {
        return this.beat.get();
    }

    /**
     * Sets what beat the crime was in
     * @param beat
     */
    public void setBeat(Integer beat) {
        this.beat.setValue(beat);
    }

    /**
     * 
     * @return What ward the crime was in
     */
    public Integer getWard() {
        return this.ward.get();
    }

    /**
     * Sets what ward the crime was in
     * @param ward
     */
    public void setWard(Integer ward) {
        this.ward.setValue(ward);

    }

    /**
     * 
     * @return The crimes FBI CD number
     */
    public String getFbiCD() {
        return this.fbiCD.get();
    }

    /**
     * Sets the crime's FIB CD number, handles an empty string as null
     * @param fbiCD
     */
    public void setFbiCD(String fbiCD) {
        if (Objects.equals(fbiCD, "")) {
            this.fbiCD.setValue(null);
        } else {
            this.fbiCD.setValue(fbiCD);
        }
    }

    public ArrayList<String> getAttributes() {
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add(caseNumber.getValue());
        attributes.add(toString(getDate()));
        attributes.add(block.getValue());
        attributes.add(iucr.getValue());
        attributes.add(getPrimaryDescription());
        attributes.add(getSecondaryDescription());
        attributes.add(getLocationDescription());
        attributes.add(toString(arrest.getValue()));
        attributes.add(toString(getDomestic()));
        attributes.add(toString(beat.getValue()));
        attributes.add(toString(ward.getValue()));
        attributes.add(fbiCD.getValue());
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
                ", fbi CD='" + fbiCD + '\'' +
                super.toString() + '\'' +
                '}';
    }
}
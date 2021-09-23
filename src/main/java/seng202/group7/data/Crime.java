package seng202.group7.data;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.naming.directory.InvalidAttributeValueException;

/**
 * Used to create and store crime objects.
 * @author Jack McCorkindale
 */
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

        if (block != null && !block.isEmpty()) {
            this.block.setValue(block);
        }
        if (iucr != null && !iucr.isEmpty()) {
            this.iucr.setValue(iucr);
        }
        this.arrest.setValue(arrest);
        this.beat.setValue(beat);
        this.ward.setValue(ward);
        if (block != null && !fbiCD.isEmpty()) {
            this.fbiCD.setValue(fbiCD);
        }
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
        } else if (!Objects.equals(getCaseNumber(), caseNumber)){
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
        } else if (!Objects.equals(getBlock(), block)){
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
        } else if (!Objects.equals(getIucr(), iucr)){
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
        if (!Objects.equals(getArrest(), arrest)) {
            this.arrest.setValue(arrest);
        }
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
        if (!Objects.equals(getBeat(), beat)) {
            this.beat.setValue(beat);
        }
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
        if (!Objects.equals(getWard(), ward)) {
            this.ward.setValue(ward);
        }
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
        } else if (!Objects.equals(getFbiCD(), fbiCD)){
            this.fbiCD.setValue(fbiCD);
        }
    }

    /**
     * Updates the values found in the crime record.
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
     * @throws InvalidAttributeValueException
     */
    public void update(String caseNumber, LocalDateTime date, String block, String iucr, String primaryDescription,
    String secondaryDescription, String locationDescription, Boolean arrest, Boolean domestic, Integer beat,
    Integer ward, String fbiCD, Integer xCoord, Integer yCoord, Double latitude, Double longitude) throws InvalidAttributeValueException {
        super.update(date, primaryDescription, secondaryDescription, locationDescription, domestic, xCoord, yCoord, latitude, longitude);
        setCaseNumber(caseNumber);
        setBlock(block);
        setIucr(iucr);
        setArrest(arrest);
        setBeat(beat);
        setWard(ward);
        setFbiCD(fbiCD);
    }


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
                "caseNumber='" + getCaseNumber() + '\'' +
                ", block='" + getBlock() + '\'' +
                ", iucr='" + getIucr() + '\'' +
                ", arrest=" + getArrest() +
                ", beat=" + getBeat() +
                ", ward=" + getWard() +
                ", fbi CD='" + getFbiCD() + '\'' +
                super.toString() + '\'' +
                '}';
    }
}
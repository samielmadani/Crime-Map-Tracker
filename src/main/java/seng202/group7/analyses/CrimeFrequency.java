package seng202.group7.analyses;

public class CrimeFrequency {

    public String date;
    public int frequency;

    public CrimeFrequency(String date, int frequency) {
        this.date = date;
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

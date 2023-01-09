import java.time.LocalDateTime;

public class Task {
    private String omschrijving, importance;
    private int hours, minutes;

    public Task(String omschrijving, int hours, int minutes, String importance) {
        this.omschrijving = omschrijving;
        this.hours = hours;
        this.minutes = minutes;
        this.importance = importance;

    }
    @Override
    public String toString() {
        return hours + ":" + minutes + " " + omschrijving;}

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }
}

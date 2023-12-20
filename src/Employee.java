import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Employee implements Serializable {
    private final String name;
    private boolean present;
    private Date lastClockInTime;
    private Date lastClockOutTime;
    private final Map<String, Integer> workHours;

    public Employee(String name, boolean present, Date lastClockInTime, Date lastClockOutTime, Map<String, Integer> workHours) {
        this.name = name;
        this.present = present;
        this.lastClockInTime = lastClockInTime;
        this.lastClockOutTime = lastClockOutTime;
        this.workHours = new HashMap<>(workHours);
    }

    public void removeWorkHours(String date) {
        workHours.remove(date);
    }


    public void setWorkHours(Map<String, Integer> workHours) {
        this.workHours.clear();
        this.workHours.putAll(workHours);
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public Date getLastClockInTime() {
        return lastClockInTime;
    }

    public void setLastClockInTime(Date lastClockInTime) {
        this.lastClockInTime = lastClockInTime;
    }

    public Date getLastClockOutTime() {
        return lastClockOutTime;
    }

    public void setLastClockOutTime(Date lastClockOutTime) {
        this.lastClockOutTime = lastClockOutTime;
    }

    public Map<String, Integer> getWorkHours() {
        return workHours;
    }

    public void addWorkHours(String date, int hours) {
        workHours.put(date, hours);
    }

    public void clearWorkHours() {
        workHours.clear();
    }
}

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Employee {
    private final String name;
    private boolean present;
    private Date lastClockInTime;
    private Date lastClockOutTime;
    private final Map<String, Integer> workHours;

    public Employee(String name) {
        this.name = name;
        this.present = false;
        this.workHours = new HashMap<>();
    }

    public String getName() {
        return name;
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

    public void removeWorkHours(String date) {
        workHours.remove(date);
    }

    public void clearWorkHours() {
        workHours.clear();
    }
}

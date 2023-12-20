import java.util.HashMap; // For workHours HashMap in Employee class
import java.util.Map; // For workHours map in Employee class

class Employee {
    private final String name; // Name of employee (immutable)
    private boolean isPresent; // Whether an employee is present or not
    private String lastClockInTime; // Time of last clock in
    private String lastClockOutTime; // Time of last clock out
    private final Map<String, Integer> workHours; // Map of work hours for each day
    public Employee(String name) {
        this.name = name;
        this.isPresent = false; // By default, employee is not present
        this.lastClockInTime = null; // By default, employee has not clocked in
        this.lastClockOutTime = null; // By default, employee has not clocked out
        this.workHours = new HashMap<>(); // Initialize workHours map
    }

    public String getName() {
        return name;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public String getLastClockInTime() {
        return lastClockInTime;
    }

    public void setLastClockInTime(String lastClockInTime) {
        this.lastClockInTime = lastClockInTime;
    }

    public String getLastClockOutTime() {
        return lastClockOutTime;
    }

    public void setLastClockOutTime(String lastClockOutTime) {
        this.lastClockOutTime = lastClockOutTime;
    }

    public Map<String, Integer> getWorkHours() {
        return workHours;
    }

    public void addWorkHours(String date, int hours) {
        workHours.put(date, hours);
    }
}
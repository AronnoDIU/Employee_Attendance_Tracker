import java.util.*;

public class AttendanceTracker {
    private final Map<String, Employee> employees;

    public AttendanceTracker() {
        this.employees = new HashMap<>();
    }

    public void addEmployee(String name) {
        employees.put(name, new Employee(name));
    }

    public void clockIn(String employeeName) {
        Employee employee = getEmployee(employeeName);
        if (employee != null) {
            if (!employee.isPresent()) {
                employee.setPresent(true);
                recordClockInTime(employee);
            } else {
                System.out.println(employeeName + " is already clocked in.");
            }
        }
    }

    private void recordClockInTime(Employee employee) {
        Date clockInTime = new Date();
        employee.setLastClockInTime(clockInTime);
        System.out.println("Clocked in at " + clockInTime);
    }

    private Employee getEmployee(String employeeName) {
        Employee employee = employees.get(employeeName);
        if (employee == null) {
            System.out.println("Employee not found: " + employeeName);
        }
        return employee;
    }

    public void clockOut(String employeeName) {
        Employee employee = getEmployee(employeeName);
        if (employee != null) {
            if (employee.isPresent()) {
                employee.setPresent(false);
                recordClockOutTime(employee);
            } else {
                System.out.println(employeeName + " is already clocked out.");
            }
        }
    }

    private void recordClockOutTime(Employee employee) {
        Date clockOutTime = new Date();
        employee.setLastClockOutTime(clockOutTime);
        System.out.println("Clocked out at " + clockOutTime);
    }

    public void viewAttendance(String employeeName) {
        Employee employee = getEmployee(employeeName);
        if (employee != null) {
            System.out.println("Attendance for " + employeeName + ":");
            System.out.println("Last Clock In Time: " + employee.getLastClockInTime());
            System.out.println("Last Clock Out Time: " + employee.getLastClockOutTime());
        }
    }

    public void listEmployees() {
        System.out.println("List of Employees:");
        for (Employee employee : employees.values()) {
            System.out.println(employee.getName());
        }
    }

    public void displayWorkHours(String displayHoursName) {
        Employee employee = getEmployee(displayHoursName);
        if (employee != null) {
            System.out.println("Work hours for " + displayHoursName + ":");
            for (Map.Entry<String, Integer> entry : employee.getWorkHours().entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue() + " hours");
            }
        }
    }

    public void addWorkHours(String addHoursName, String date, int hours) {
        Employee employee = getEmployee(addHoursName);
        if (employee != null) {
            employee.addWorkHours(date, hours);
            System.out.println("Work hours added for " + addHoursName + " on " + date + ": " + hours + " hours");
        }
    }

    public void removeWorkHours(String removeHoursName, String removeHoursDate) {
        Employee employee = getEmployee(removeHoursName);
        if (employee != null) {
            employee.removeWorkHours(removeHoursDate);
            System.out.println("Work hours removed for " + removeHoursName + " on " + removeHoursDate);
        }
    }

    public void calculateTotalWorkHours(String calculateTotalHoursName) {
        Employee employee = getEmployee(calculateTotalHoursName);
        if (employee != null) {
            int totalHours = calculateTotalHours(employee.getWorkHours());
            System.out.println("Total work hours for " + calculateTotalHoursName + ": " + totalHours + " hours");
        }
    }

    private int calculateTotalHours(Map<String, Integer> workHours) {
        int totalHours = 0;
        for (int hours : workHours.values()) {
            totalHours += hours;
        }
        return totalHours;
    }

    public void calculateAverageWorkHours(String calculateAverageHoursName) {
        Employee employee = getEmployee(calculateAverageHoursName);
        if (employee != null) {
            double averageHours = calculateAverageHours(employee.getWorkHours());
            System.out.println("Average work hours for " + calculateAverageHoursName + ": " + averageHours + " hours");
        }
    }

    private double calculateAverageHours(Map<String, Integer> workHours) {
        int totalHours = calculateTotalHours(workHours);
        return (double) totalHours / workHours.size();
    }

    public void calculateTotalWorkHoursForMonth(String calculateTotalHoursMonthName, String calculateTotalHoursMonth) {
        Employee employee = getEmployee(calculateTotalHoursMonthName);
        if (employee != null) {
            int totalHours = calculateTotalHoursForMonth(employee.getWorkHours(), calculateTotalHoursMonth);
            System.out.println("Total work hours for " + calculateTotalHoursMonthName + " in " + calculateTotalHoursMonth + ": " + totalHours + " hours");
        }
    }

    private int calculateTotalHoursForMonth(Map<String, Integer> workHours, String calculateTotalHoursMonth) {
        int totalHours = 0;
        for (Map.Entry<String, Integer> entry : workHours.entrySet()) {
            String date = entry.getKey();
            int hours = entry.getValue();
            if (date.startsWith(calculateTotalHoursMonth)) {
                totalHours += hours;
            }
        }
        return totalHours;
    }

    public void calculateAverageWorkHoursForMonth(String calculateAverageHoursMonthName, String calculateAverageHoursMonth) {
        Employee employee = getEmployee(calculateAverageHoursMonthName);
        if (employee != null) {
            double averageHours = calculateAverageHoursForMonth(employee.getWorkHours(), calculateAverageHoursMonth);
            System.out.println("Average work hours for " + calculateAverageHoursMonthName + " in " + calculateAverageHoursMonth + ": " + averageHours + " hours");
        }
    }

    private double calculateAverageHoursForMonth(Map<String, Integer> workHours, String calculateAverageHoursMonth) {
        int totalHours = calculateTotalHoursForMonth(workHours, calculateAverageHoursMonth);
        int numDays = 0;
        for (String date : workHours.keySet()) {
            if (date.startsWith(calculateAverageHoursMonth)) {
                numDays++;
            }
        }
        return (double) totalHours / numDays;
    }

    public void calculateTotalWorkHoursForYear(String calculateTotalHoursYearName, String calculateTotalHoursYear) {
        Employee employee = getEmployee(calculateTotalHoursYearName);
        if (employee != null) {
            int totalHours = calculateTotalHoursForYear(employee.getWorkHours(), calculateTotalHoursYear);
            System.out.println("Total work hours for " + calculateTotalHoursYearName + " in " + calculateTotalHoursYear + ": " + totalHours + " hours");
        }
    }

    private int calculateTotalHoursForYear(Map<String, Integer> workHours, String calculateTotalHoursYear) {
        int totalHoursForYear = 0;
        for (Map.Entry<String, Integer> entry : workHours.entrySet()) {
            String date = entry.getKey();
            int hours = entry.getValue();
            if (date.startsWith(calculateTotalHoursYear)) {
                totalHoursForYear += hours;
            }
        }
        return totalHoursForYear;
    }

    public void calculateAverageWorkHoursForYear(String calculateAverageHoursYearName, String calculateAverageHoursYear) {
        Employee employee = getEmployee(calculateAverageHoursYearName);
        if (employee != null) {
            double averageHours = calculateAverageHoursForYear(employee.getWorkHours(), calculateAverageHoursYear);
            System.out.println("Average work hours for " + calculateAverageHoursYearName + " in " + calculateAverageHoursYear + ": " + averageHours + " hours");
        }
    }

    private double calculateAverageHoursForYear(Map<String, Integer> workHours, String calculateAverageHoursYear) {
        int totalHoursForYear = calculateTotalHoursForYear(workHours, calculateAverageHoursYear);
        int numDays = 0;
        for (String date : workHours.keySet()) {
            if (date.startsWith(calculateAverageHoursYear)) {
                numDays++;
            }
        }
        return (double) totalHoursForYear / numDays;
    }

    public void removeEmployee(String removeEmployeeName) {
        Employee removedEmployee = employees.remove(removeEmployeeName);
        if (removedEmployee != null) {
            System.out.println("Employee removed: " + removeEmployeeName);
        } else {
            System.out.println("Employee not found: " + removeEmployeeName);
        }
    }

    public void clearEmployees() {
        employees.clear();
        System.out.println("All employees cleared.");
    }

    public void clearAttendance(String clearAttendanceName) {
        Employee employee = getEmployee(clearAttendanceName);
        if (employee != null) {
            employee.setLastClockInTime(null);
            employee.setLastClockOutTime(null);
            System.out.println("Attendance cleared for " + clearAttendanceName);
        } else {
            System.out.println("Employee not found: " + clearAttendanceName);
        }
    }

    public void clearAllAttendance() {
        for (Employee employee : employees.values()) {
            employee.setLastClockInTime(null);
            employee.setLastClockOutTime(null);
        }
        System.out.println("Attendance cleared for all employees.");
    }

    public void clearWorkHours(String clearHoursName) {
        Employee employee = getEmployee(clearHoursName);
        if (employee != null) {
            employee.clearWorkHours();
            System.out.println("Work hours cleared for " + clearHoursName);
        } else {
            System.out.println("Employee not found: " + clearHoursName);
        }
    }

    public void clearAllWorkHours() {
        for (Employee employee : employees.values()) {
            employee.clearWorkHours();
        }
        System.out.println("Work hours cleared for all employees.");
    }

    public void clearAll() {
        clearEmployees();
        clearAllAttendance();
        clearAllWorkHours();
    }

    public void removeWorkHours(String removeHoursName) {
        Employee employee = getEmployee(removeHoursName);
        if (employee != null) {
            employee.clearWorkHours();
            System.out.println("Work hours removed for " + removeHoursName);
        } else {
            System.out.println("Employee not found: " + removeHoursName);
        }
    }

    public void removeAttendance(String removeAttendanceName) {
        Employee employee = getEmployee(removeAttendanceName);
        if (employee != null) {
            employee.setLastClockInTime(null);
            employee.setLastClockOutTime(null);
            System.out.println("Attendance removed for " + removeAttendanceName);
        } else {
            System.out.println("Employee not found: " + removeAttendanceName);
        }
    }

    public void removeAttendance(String removeAttendanceName, String removeAttendanceType) {
        Employee employee = getEmployee(removeAttendanceName);
        if (employee != null) {
            if (removeAttendanceType.equalsIgnoreCase("clock in")) {
                employee.setLastClockInTime(null);
                System.out.println("Clock in time removed for " + removeAttendanceName);
            } else if (removeAttendanceType.equalsIgnoreCase("clock out")) {
                employee.setLastClockOutTime(null);
                System.out.println("Clock out time removed for " + removeAttendanceName);
            } else {
                System.out.println("Invalid attendance type: " + removeAttendanceType);
            }
        } else {
            System.out.println("Employee not found: " + removeAttendanceName);
        }
    }

    public void removeAttendance(String removeAttendanceName, String removeAttendanceType, String removeAttendanceDate) {
        Employee employee = getEmployee(removeAttendanceName);
        if (employee != null) {
            if (removeAttendanceType.equalsIgnoreCase("clock in")) {
                employee.setLastClockInTime(null);
                System.out.println("Clock in time removed for " + removeAttendanceName + " on " + removeAttendanceDate);
            } else if (removeAttendanceType.equalsIgnoreCase("clock out")) {
                employee.setLastClockOutTime(null);
                System.out.println("Clock out time removed for " + removeAttendanceName + " on " + removeAttendanceDate);
            } else {
                System.out.println("Invalid attendance type: " + removeAttendanceType);
            }
        } else {
            System.out.println("Employee not found: " + removeAttendanceName);
        }
    }

    public void removeAttendance(String removeAttendanceName, String removeAttendanceType, String removeAttendanceDate, String removeAttendanceTime) {
        Employee employee = getEmployee(removeAttendanceName);
        if (employee != null) {
            if (removeAttendanceType.equalsIgnoreCase("clock in")) {
                employee.setLastClockInTime(null);
                System.out.println("Clock in time removed for " + removeAttendanceName + " on " + removeAttendanceDate + " at " + removeAttendanceTime);
            } else if (removeAttendanceType.equalsIgnoreCase("clock out")) {
                employee.setLastClockOutTime(null);
                System.out.println("Clock out time removed for " + removeAttendanceName + " on " + removeAttendanceDate + " at " + removeAttendanceTime);
            } else {
                System.out.println("Invalid attendance type: " + removeAttendanceType);
            }
        } else {
            System.out.println("Employee not found: " + removeAttendanceName);
        }
    }

    public static void main(String[] args) {
        AttendanceTracker tracker = new AttendanceTracker();
        Scanner userInput = new Scanner(System.in);

        while (true) {
            displayMenu();
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = userInput.nextInt();
                userInput.nextLine(); // Consume the newline character
            } catch (InputMismatchException e) {
                userInput.nextLine(); // Consume the invalid input
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    handleClockIn(userInput, tracker);
                    break;
                case 2:
                    handleClockOut(userInput, tracker);
                    break;
                case 3:
                    handleViewAttendance(userInput, tracker);
                    break;
                case 4:
                    tracker.listEmployees();
                    break;
                case 5:
                    handleDisplayWorkHours(userInput, tracker);
                    break;
                case 6:
                    handleAddWorkHours(userInput, tracker);
                    break;
                case 7:
                    handleRemoveWorkHours(userInput, tracker);
                    break;
                case 8:
                    handleCalculateTotalWorkHours(userInput, tracker);
                    break;
                case 9:
                    handleCalculateAverageWorkHours(userInput, tracker);
                    break;
                case 10:
                    handleCalculateTotalWorkHoursForMonth(userInput, tracker);
                    break;
                case 11:
                    handleCalculateAverageWorkHoursForMonth(userInput, tracker);
                    break;
                case 12:
                    handleCalculateTotalWorkHoursForYear(userInput, tracker);
                    break;
                case 13:
                    handleCalculateAverageWorkHoursForYear(userInput, tracker);
                    break;
                case 14:
                    handleRemoveEmployee(userInput, tracker);
                    break;
                case 15:
                    tracker.clearEmployees();
                    break;
                case 16:
                    handleClearAttendance(userInput, tracker);
                    break;
                case 17:
                    tracker.clearAllAttendance();
                    break;
                case 18:
                    handleClearWorkHours(userInput, tracker);
                    break;
                case 19:
                    tracker.clearAllWorkHours();
                    break;
                case 20:
                    tracker.clearAll();
                    break;
                case 21:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("1. Clock In");
        System.out.println("2. Clock Out");
        System.out.println("3. View Attendance");
        System.out.println("4. List Employees");
        System.out.println("5. Display Work Hours");
        System.out.println("6. Add Work Hours");
        System.out.println("7. Remove Work Hours");
        System.out.println("8. Calculate Total Work Hours");
        System.out.println("9. Calculate Average Work Hours");
        System.out.println("10. Calculate Total Work Hours for a Month");
        System.out.println("11. Calculate Average Work Hours for a Month");
        System.out.println("12. Calculate Total Work Hours for a Year");
        System.out.println("13. Calculate Average Work Hours for a Year");
        System.out.println("14. Remove Employee");
        System.out.println("15. Clear Employees");
        System.out.println("16. Clear Attendance");
        System.out.println("17. Clear All Attendance");
        System.out.println("18. Clear Work Hours");
        System.out.println("19. Clear All Work Hours");
        System.out.println("20. Clear All");
        System.out.println("21. Exit");
    }

    private static void handleClockIn(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name to clock in: ");
        String clockInName = userInput.nextLine();
        tracker.clockIn(clockInName);
    }

    private static void handleClockOut(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name to clock out: ");
        String clockOutName = userInput.nextLine();

        tracker.clockOut(clockOutName);
    }

    private static void handleViewAttendance(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name to view attendance: ");
        String viewAttendanceName = userInput.nextLine();
        tracker.viewAttendance(viewAttendanceName);
    }

    private static void handleDisplayWorkHours(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name to display work hours: ");
        String displayHoursName = userInput.nextLine();
        tracker.displayWorkHours(displayHoursName);
    }

    private static void handleAddWorkHours(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name to add work hours: ");
        String addHoursName = userInput.nextLine();
        System.out.print("Enter date (yyyy-MM-dd): ");
        String date = userInput.nextLine();
        System.out.print("Enter hours: ");
        int hours = userInput.nextInt();
        userInput.nextLine(); // Consume the newline character
        tracker.addWorkHours(addHoursName, date, hours);
    }

    private static void handleRemoveWorkHours(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name to remove work hours: ");
        String removeHoursName = userInput.nextLine();
        System.out.print("Enter date (yyyy-MM-dd): ");
        String removeHoursDate = userInput.nextLine();
        tracker.removeWorkHours(removeHoursName, removeHoursDate);
    }

    private static void handleCalculateTotalWorkHours(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name to calculate total work hours: ");
        String calculateTotalHoursName = userInput.nextLine();
        tracker.calculateTotalWorkHours(calculateTotalHoursName);
    }

    private static void handleCalculateAverageWorkHours(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name to calculate average work hours: ");
        String calculateAverageHoursName = userInput.nextLine();
        tracker.calculateAverageWorkHours(calculateAverageHoursName);
    }

    private static void handleCalculateTotalWorkHoursForMonth(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name to calculate total work hours: ");
        String calculateTotalHoursMonthName = userInput.nextLine();
        System.out.print("Enter month (yyyy-MM): ");
        String calculateTotalHoursMonth = userInput.nextLine();
        tracker.calculateTotalWorkHoursForMonth(calculateTotalHoursMonthName, calculateTotalHoursMonth);
    }

    private static void handleCalculateAverageWorkHoursForMonth(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name to calculate average work hours: ");
        String calculateAverageHoursMonthName = userInput.nextLine();
        System.out.print("Enter month (yyyy-MM): ");
        String calculateAverageHoursMonth = userInput.nextLine();
        tracker.calculateAverageWorkHoursForMonth(calculateAverageHoursMonthName, calculateAverageHoursMonth);
    }

    private static void handleCalculateTotalWorkHoursForYear(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name to calculate total work hours: ");
        String calculateTotalHoursYearName = userInput.nextLine();
        System.out.print("Enter year (yyyy): ");
        String calculateTotalHoursYear = userInput.nextLine();
        tracker.calculateTotalWorkHoursForYear(calculateTotalHoursYearName, calculateTotalHoursYear);
    }

    private static void handleCalculateAverageWorkHoursForYear(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name to calculate average work hours: ");
        String calculateAverageHoursYearName = userInput.nextLine();
        System.out.print("Enter year (yyyy): ");
        String calculateAverageHoursYear = userInput.nextLine();
        tracker.calculateAverageWorkHoursForYear(calculateAverageHoursYearName, calculateAverageHoursYear);
    }

    private static void handleRemoveEmployee(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name to remove: ");
        String removeEmployeeName = userInput.nextLine();
        tracker.removeEmployee(removeEmployeeName);
    }

    private static void handleClearAttendance(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name to clear attendance: ");
        String clearAttendanceName = userInput.nextLine();
        tracker.clearAttendance(clearAttendanceName);
    }

    private static void handleClearWorkHours(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name to clear work hours: ");
        String clearHoursName = userInput.nextLine();
        tracker.clearWorkHours(clearHoursName);
    }
}
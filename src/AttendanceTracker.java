import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class AttendanceTracker {
    private final Map<String, Employee> employees;
    private static final String DATA_FILE = "attendance_data.txt";

    public AttendanceTracker() {
        this.employees = new HashMap<>();
        readDataFromFile();
    }

    @SuppressWarnings("unchecked")
    private void readDataFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            employees.clear();
            employees.putAll((Map<String, Employee>) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading data from file: " + e.getMessage());
        }
    }

    private void writeDataToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            System.out.println("Error writing data to file: " + e.getMessage());
        }
    }

    public void addEmployee(String name, boolean present, String lastClockInTime, String lastClockOutTime, String workHours) {
        Employee employee = new Employee(
                name,
                present,
                parseDate(lastClockInTime),
                parseDate(lastClockOutTime),
                new HashMap<>()
        );

        parseWorkHours(workHours, employee);
        employees.put(name, employee);
        writeDataToFile();
    }



    private void parseWorkHours(String workHours, Employee employee) {
        if (workHours.equalsIgnoreCase("N/A")) {
            return;
        }
        String[] workHoursArray = workHours.split(",");
        for (String workHour : workHoursArray) {
            String[] workHourArray = workHour.split(":");
            String date = workHourArray[0];
            int hours = Integer.parseInt(workHourArray[1]);
            employee.addWorkHours(date, hours);
        }
    }

    private Date parseDate(String lastClockInTime) {
        if (lastClockInTime.equalsIgnoreCase("N/A")) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(lastClockInTime);
        } catch (Exception e) {
            System.out.println("Invalid date format: " + lastClockInTime);
            return null;
        }
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
            System.out.println("Last Clock In Time: " + formatDate(employee.getLastClockInTime()));
            System.out.println("Last Clock Out Time: " + formatDate(employee.getLastClockOutTime()));
        }
    }

    public void listEmployees() {
        System.out.println("List of Employees:");
        employees.keySet().forEach(System.out::println);
    }

    public void displayWorkHours(String displayHoursName) {
        Employee employee = getEmployee(displayHoursName);
        if (employee != null) {
            System.out.println("Work hours for " + displayHoursName + ":");
            employee.getWorkHours().forEach((date, hours) ->
                    System.out.println(date + ": " + hours + " hours"));
        }
    }

    public void addWorkHours(String addHoursName, String date, int hours) {
        Employee employee = getEmployee(addHoursName);
        if (employee != null) {
            employee.addWorkHours(date, hours);
            System.out.println("Work hours added for " + addHoursName + " on " + date + ": " + hours + " hours");
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
        return workHours.values().stream().mapToInt(Integer::intValue).sum();
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
        return totalHours / (double) workHours.size();
    }

    public void calculateTotalWorkHoursForMonth(String calculateTotalHoursMonthName, String calculateTotalHoursMonth) {
        Employee employee = getEmployee(calculateTotalHoursMonthName);
        if (employee != null) {
            int totalHours = calculateTotalHoursForMonth(employee.getWorkHours(), calculateTotalHoursMonth);
            System.out.println("Total work hours for " + calculateTotalHoursMonthName + " in " + calculateTotalHoursMonth + ": " + totalHours + " hours");
        }
    }

    private int calculateTotalHoursForMonth(Map<String, Integer> workHours, String calculateTotalHoursMonth) {
        return workHours.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(calculateTotalHoursMonth))
                .mapToInt(Map.Entry::getValue)
                .sum();
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
        long numDays = workHours.keySet().stream()
                .filter(date -> date.startsWith(calculateAverageHoursMonth))
                .count();
        return totalHours / (double) numDays;
    }

    public void calculateTotalWorkHoursForYear(String calculateTotalHoursYearName, String calculateTotalHoursYear) {
        Employee employee = getEmployee(calculateTotalHoursYearName);
        if (employee != null) {
            int totalHours = calculateTotalHoursForYear(employee.getWorkHours(), calculateTotalHoursYear);
            System.out.println("Total work hours for " + calculateTotalHoursYearName + " in " + calculateTotalHoursYear + ": " + totalHours + " hours");
        }
    }

    private int calculateTotalHoursForYear(Map<String, Integer> workHours, String calculateTotalHoursYear) {
        return workHours.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(calculateTotalHoursYear))
                .mapToInt(Map.Entry::getValue)
                .sum();
    }

    public void calculateAverageWorkHoursForYear(String calculateAverageHoursYearName, String calculateAverageHoursYear) {
        Employee employee = getEmployee(calculateAverageHoursYearName);
        if (employee != null) {
            double averageHours = calculateAverageHoursForYear(employee.getWorkHours(), calculateAverageHoursYear);
            System.out.println("Average work hours for " + calculateAverageHoursYearName + " in " + calculateAverageHoursYear + ": " + averageHours + " hours");
        }
    }

    private double calculateAverageHoursForYear(Map<String, Integer> workHours, String calculateAverageHoursYear) {
        int totalHours = calculateTotalHoursForYear(workHours, calculateAverageHoursYear);
        long numDays = workHours.keySet().stream()
                .filter(date -> date.startsWith(calculateAverageHoursYear))
                .count();
        return totalHours / (double) numDays;
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
        employees.values().forEach(employee -> {
            employee.setLastClockInTime(null);
            employee.setLastClockOutTime(null);
        });
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
        employees.values().forEach(Employee::clearWorkHours);
        System.out.println("Work hours cleared for all employees.");
    }

    public void clearAll() {
        clearEmployees();
        clearAllAttendance();
        clearAllWorkHours();
        System.out.println("All data cleared.");

        // Add this line to clear the data from the file
        writeDataToFile();
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

    private Employee getEmployee(String employeeName) {
        Employee employee = employees.get(employeeName);
        if (employee == null) {
            System.out.println("Employee not found: " + employeeName);
        }
        return employee;
    }

    private String formatDate(Date date) {
        if (date == null) {
            return "N/A";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
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
                case 0: // For Add employee
                    handleAddEmployee(userInput, tracker);
                    break;
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
                case 7, 21:
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
                case 22:
                    handleRemoveAttendance(userInput, tracker);
                    break;
                case 23:
                    handleRemoveAttendanceWithType(userInput, tracker);
                    break;
                case 24:
                    handleRemoveAttendanceWithTypeAndDate(userInput, tracker);
                    break;
                case 25:
                    handleRemoveAttendanceWithTypeDateAndTime(userInput, tracker);
                    break;
                case 26:
                    handleRemoveAttendanceWithDate(userInput, tracker);
                    break;
                case 27:
                    handleRemoveAttendanceWithDateAndTime(userInput, tracker);
                    break;
                case 28:
                    handleRemoveAttendanceWithTypeAndTime(userInput, tracker);
                    break;
                case 29: // For Remove employee
                    handleRemoveEmployee(userInput, tracker);
                    break;
                case 30: // For Remove Work Hours
                    handleRemoveWorkHours(userInput, tracker);
                    break;
                case 31:
                    userInput.close();
                    System.out.println("Exiting the Attendance Tracker. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 31.");
                    break;
            }
        }
    }

    private static void handleRemoveAttendanceWithTypeAndTime(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        System.out.print("Enter attendance type (clock in/clock out): ");
        String type = userInput.nextLine();
        System.out.print("Enter attendance time (HH:mm:ss): ");
        String time = userInput.nextLine();
        tracker.removeAttendance(name, type, time);
    }

    private static void handleRemoveAttendanceWithDateAndTime(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        System.out.print("Enter attendance date (yyyy-MM-dd): ");
        String date = userInput.nextLine();
        System.out.print("Enter attendance time (HH:mm:ss): ");
        String time = userInput.nextLine();
        tracker.removeAttendance(name, date, time);
    }

    private static void handleRemoveAttendanceWithDate(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        System.out.print("Enter attendance date (yyyy-MM-dd): ");
        String date = userInput.nextLine();
        tracker.removeAttendance(name, date);
    }

    private static void handleAddEmployee(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        System.out.print("Enter employee present (true/false): ");
        boolean present = userInput.nextBoolean();

        // Consume the newline character
        userInput.nextLine();

        System.out.print("Enter employee last clock in time (yyyy-MM-dd HH:mm:ss): ");
        String lastClockInTime = userInput.nextLine();
        System.out.print("Enter employee last clock out time (yyyy-MM-dd HH:mm:ss): ");
        String lastClockOutTime = userInput.nextLine();
        System.out.print("Enter employee work hours (yyyy-MM-dd:hours,yyyy-MM-dd:hours,...): ");
        String workHours = userInput.nextLine();

        tracker.addEmployee(name, present, lastClockInTime, lastClockOutTime, workHours);
        System.out.println("Employee added successfully.");
    }

    private static void displayMenu() {
        System.out.println("\nAttendance Tracker Menu:");
        System.out.println("0. Add Employee");
        System.out.println("1. Clock In");
        System.out.println("2. Clock Out");
        System.out.println("3. View Attendance");
        System.out.println("4. List Employees");
        System.out.println("5. Display Work Hours");
        System.out.println("6. Add Work Hours");
        System.out.println("7. Remove Work Hours");
        System.out.println("8. Calculate Total Work Hours");
        System.out.println("9. Calculate Average Work Hours");
        System.out.println("10. Calculate Total Work Hours for Month");
        System.out.println("11. Calculate Average Work Hours for Month");
        System.out.println("12. Calculate Total Work Hours for Year");
        System.out.println("13. Calculate Average Work Hours for Year");
        System.out.println("14. Remove Employee");
        System.out.println("15. Clear Employees");
        System.out.println("16. Clear Attendance for Employee");
        System.out.println("17. Clear All Attendance");
        System.out.println("18. Clear Work Hours for Employee");
        System.out.println("19. Clear All Work Hours");
        System.out.println("20. Clear All (Employees, Attendance, Work Hours)");
        System.out.println("21. Remove Work Hours for Employee");
        System.out.println("22. Remove Attendance for Employee");
        System.out.println("23. Remove Attendance for Employee with Type");
        System.out.println("24. Remove Attendance for Employee with Type and Date");
        System.out.println("25. Remove Attendance for Employee with Type, Date, and Time");
        System.out.println("26. Remove Attendance for Employee with Date");
        System.out.println("27. Remove Attendance for Employee with Date and Time");
        System.out.println("28. Remove Attendance for Employee with Type and Time");
        System.out.println("29. Remove Employee");
        System.out.println("30. Remove Work Hours");
        System.out.println("31. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void handleClockIn(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        tracker.clockIn(name);
    }

    private static void handleClockOut(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        tracker.clockOut(name);
    }

    private static void handleViewAttendance(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        tracker.viewAttendance(name);
    }

    private static void handleDisplayWorkHours(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        tracker.displayWorkHours(name);
    }

    private static void handleAddWorkHours(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        System.out.print("Enter date (yyyy-MM-dd): ");
        String date = userInput.nextLine();
        System.out.print("Enter hours: ");
        int hours = userInput.nextInt();
        tracker.addWorkHours(name, date, hours);
    }

    private static void handleRemoveWorkHours(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        tracker.removeWorkHours(name);
    }

    private static void handleCalculateTotalWorkHours(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        tracker.calculateTotalWorkHours(name);
    }

    private static void handleCalculateAverageWorkHours(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        tracker.calculateAverageWorkHours(name);
    }

    private static void handleCalculateTotalWorkHoursForMonth(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        System.out.print("Enter month (yyyy-MM): ");
        String month = userInput.nextLine();
        tracker.calculateTotalWorkHoursForMonth(name, month);
    }

    private static void handleCalculateAverageWorkHoursForMonth(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        System.out.print("Enter month (yyyy-MM): ");
        String month = userInput.nextLine();
        tracker.calculateAverageWorkHoursForMonth(name, month);
    }

    private static void handleCalculateTotalWorkHoursForYear(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        System.out.print("Enter year (yyyy): ");
        String year = userInput.nextLine();
        tracker.calculateTotalWorkHoursForYear(name, year);
    }

    private static void handleCalculateAverageWorkHoursForYear(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        System.out.print("Enter year (yyyy): ");
        String year = userInput.nextLine();
        tracker.calculateAverageWorkHoursForYear(name, year);
    }

    private static void handleRemoveEmployee(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        tracker.removeEmployee(name);
    }

    private static void handleClearAttendance(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        tracker.clearAttendance(name);
    }

    private static void handleClearWorkHours(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        tracker.clearWorkHours(name);
    }

    private static void handleRemoveAttendance(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        tracker.removeAttendance(name);
    }

    private static void handleRemoveAttendanceWithType(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        System.out.print("Enter attendance type (clock in/clock out): ");
        String type = userInput.nextLine();
        tracker.removeAttendance(name, type);
    }

    private static void handleRemoveAttendanceWithTypeAndDate(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        System.out.print("Enter attendance type (clock in/clock out): ");
        String type = userInput.nextLine();
        System.out.print("Enter attendance date (yyyy-MM-dd): ");
        String date = userInput.nextLine();
        tracker.removeAttendance(name, type, date);
    }

    private static void handleRemoveAttendanceWithTypeDateAndTime(Scanner userInput, AttendanceTracker tracker) {
        System.out.print("Enter employee name: ");
        String name = userInput.nextLine();
        System.out.print("Enter attendance type (clock in/clock out): ");
        String type = userInput.nextLine();
        System.out.print("Enter attendance date (yyyy-MM-dd): ");
        String date = userInput.nextLine();
        System.out.print("Enter attendance time (HH:mm:ss): ");
        String time = userInput.nextLine();
        tracker.removeAttendance(name, type, date, time);
    }
}
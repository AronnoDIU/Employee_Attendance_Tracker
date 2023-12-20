import java.text.SimpleDateFormat; // For formatting date and time
import java.util.Date; // For getting current date and time
import java.util.HashMap; // For employees HashMap in AttendanceTracker class
import java.util.Map; // For employees map in AttendanceTracker class
import java.util.Scanner;

public class AttendanceTracker {
    private final Map<String, Employee> employees; // Map of employees

    public AttendanceTracker() {
        this.employees = new HashMap<>();
    }

    // Add an employee to the employees map with the given name
    public void addEmployee(String name) {
        employees.put(name, new Employee(name));
    }

    // Clock in an employee with the given name
    public void clockIn(String employeeName) {
        Employee employee = employees.get(employeeName);
        // Check if employee exists in the employees map
        if (employee != null) {
            // Check if an employee is already clocked in or not
            if (!employee.isPresent()) {
                employee.setPresent(true); // Set employee to present
                // Get current date and time
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = sdf.format(new Date()); // Format date and time
                employee.setLastClockInTime(currentTime); // Set the last clock in time
                System.out.println(employeeName + " has clocked in at " + currentTime);
            } else {
                System.out.println(employeeName + " is already clocked in.");
            }
        } else {
            System.out.println("Employee not found: " + employeeName);
        }
    }

    // Clock out an employee with the given name
    public void clockOut(String employeeName) {
        Employee employee = employees.get(employeeName);
        if (employee != null) {
            if (employee.isPresent()) {
                employee.setPresent(false);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTime = sdf.format(new Date()); // Format date and time
                employee.setLastClockOutTime(currentTime); // Set the last clock out time
                System.out.println(employeeName + " has clocked out at " + currentTime);
            } else {
                System.out.println(employeeName + " is already clocked out.");
            }
        } else {
            System.out.println("Employee not found: " + employeeName);
        }
    }

    // View attendance for an employee with the given name
    public void viewAttendance(String employeeName) {
        Employee employee = employees.get(employeeName);
        if (employee != null) {
            System.out.println("Attendance for " + employeeName + ":");
            System.out.println("Last Clock In Time: " + employee.getLastClockInTime());
            System.out.println("Last Clock Out Time: " + employee.getLastClockOutTime());
        } else {
            System.out.println("Employee not found: " + employeeName);
        }
    }

    // List all employees in the employees map by name
    public void listEmployees() {
        System.out.println("List of Employees:");
        for (Employee employee : employees.values()) {
            System.out.println(employee.getName());
        }
    }

    private void calculateAverageWorkHours(String calculateAverageHoursName) {
        Employee employee = employees.get(calculateAverageHoursName);
        if (employee != null) {
            int totalHours = 0;
            for (int hours : employee.getWorkHours().values()) {
                totalHours += hours;
            }
            int averageHours = totalHours / employee.getWorkHours().size();
            System.out.println("Average work hours for " + calculateAverageHoursName + ": " + averageHours);
        } else {
            System.out.println("Employee not found: " + calculateAverageHoursName);
        }
    }

    private void calculateTotalWorkHours(String calculateTotalHoursName) {
        Employee employee = employees.get(calculateTotalHoursName);
        if (employee != null) {
            int totalHours = 0;
            for (int hours : employee.getWorkHours().values()) {
                totalHours += hours;
            }
            System.out.println("Total work hours for " + calculateTotalHoursName + ": " + totalHours);
        } else {
            System.out.println("Employee not found: " + calculateTotalHoursName);
        }
    }

    private void removeWorkHours(String removeHoursName, String removeHoursDate) {
        Employee employee = employees.get(removeHoursName);
        if (employee != null) {
            employee.getWorkHours().remove(removeHoursDate);
            System.out.println("Work hours removed for " + removeHoursName + " on " + removeHoursDate);
        } else {
            System.out.println("Employee not found: " + removeHoursName);
        }
    }

    private void addWorkHours(String addHoursName, String date, int hours) {
        Employee employee = employees.get(addHoursName);
        if (employee != null) {
            employee.addWorkHours(date, hours);
            System.out.println("Work hours added for " + addHoursName + " on " + date + ": " + hours);
        } else {
            System.out.println("Employee not found: " + addHoursName);
        }
    }

    private void displayWorkHours(String displayHoursName) {
        Employee employee = employees.get(displayHoursName);
        if (employee != null) {
            System.out.println("Work hours for " + displayHoursName + ":");
            for (Map.Entry<String, Integer> entry : employee.getWorkHours().entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } else {
            System.out.println("Employee not found: " + displayHoursName);
        }
    }

    public static void main(String[] args) {
        AttendanceTracker tracker = new AttendanceTracker();
        tracker.addEmployee("John Doe");
        tracker.addEmployee("Jane Smith");
        tracker.addEmployee("Bob Johnson");
        tracker.addEmployee("Mary Williams");
        tracker.addEmployee("James Brown");

        Scanner userInput = new Scanner(System.in);

        while (true) {
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
            System.out.println("14. Exit");
            System.out.print("Enter your choice: ");

            int choice = userInput.nextInt();
            userInput.nextLine(); // Consume the newline character

            switch (choice) {
                case 1: // For Clock in
                    System.out.print("Enter employee name to clock in: ");
                    String clockInName = userInput.nextLine();
                    tracker.clockIn(clockInName);
                    break;
                case 2: // For Clock out
                    System.out.print("Enter employee name to clock out: ");
                    String clockOutName = userInput.nextLine();
                    tracker.clockOut(clockOutName);
                    break;
                case 3: // For View attendance
                    System.out.print("Enter employee name to view attendance: ");
                    String viewAttendanceName = userInput.nextLine();
                    tracker.viewAttendance(viewAttendanceName);
                    break;
                case 4: // For List employees
                    tracker.listEmployees();
                    break;
                case 5:
                    System.out.print("Enter employee name to display work hours: ");
                    String displayHoursName = userInput.nextLine();
                    tracker.displayWorkHours(displayHoursName);
                    break;
                case 6:
                    System.out.print("Enter employee name to add work hours: ");
                    String addHoursName = userInput.nextLine();
                    System.out.print("Enter date (yyyy-MM-dd): ");
                    String date = userInput.nextLine();
                    System.out.print("Enter hours: ");
                    int hours = userInput.nextInt();
                    userInput.nextLine(); // Consume the newline character
                    tracker.addWorkHours(addHoursName, date, hours);
                    break;
                case 7:
                    System.out.print("Enter employee name to remove work hours: ");
                    String removeHoursName = userInput.nextLine();
                    System.out.print("Enter date (yyyy-MM-dd): ");
                    String removeHoursDate = userInput.nextLine();
                    tracker.removeWorkHours(removeHoursName, removeHoursDate);
                    break;
                case 8:
                    System.out.print("Enter employee name to calculate total work hours: ");
                    String calculateTotalHoursName = userInput.nextLine();
                    tracker.calculateTotalWorkHours(calculateTotalHoursName);
                    break;
                case 9:
                    System.out.print("Enter employee name to calculate average work hours: ");
                    String calculateAverageHoursName = userInput.nextLine();
                    tracker.calculateAverageWorkHours(calculateAverageHoursName);
                    break;
                case 10:
                    System.out.print("Enter employee name to calculate total work hours for a month: ");
                    String calculateTotalHoursMonthName = userInput.nextLine();
                    System.out.print("Enter month (yyyy-MM): ");
                    String calculateTotalHoursMonth = userInput.nextLine();
                    tracker.calculateTotalWorkHoursForMonth(calculateTotalHoursMonthName, calculateTotalHoursMonth);
                    break;
                case 11:
                    System.out.print("Enter employee name to calculate average work hours for a month: ");
                    String calculateAverageHoursMonthName = userInput.nextLine();
                    System.out.print("Enter month (yyyy-MM): ");
                    String calculateAverageHoursMonth = userInput.nextLine();
                    tracker.calculateAverageWorkHoursForMonth(calculateAverageHoursMonthName, calculateAverageHoursMonth);
                    break;
                case 12:
                    System.out.print("Enter employee name to calculate total work hours for a year: ");
                    String calculateTotalHoursYearName = userInput.nextLine();
                    System.out.print("Enter year (yyyy): ");
                    String calculateTotalHoursYear = userInput.nextLine();
                    tracker.calculateTotalWorkHoursForYear(calculateTotalHoursYearName, calculateTotalHoursYear);
                    break;
                case 13:
                    System.out.print("Enter employee name to calculate average work hours for a year: ");
                    String calculateAverageHoursYearName = userInput.nextLine();
                    System.out.print("Enter year (yyyy): ");
                    String calculateAverageHoursYear = userInput.nextLine();
                    tracker.calculateAverageWorkHoursForYear(calculateAverageHoursYearName, calculateAverageHoursYear);
                    break;
                case 14: // For Exit
                    System.out.println("Exiting...");
                    userInput.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private void calculateAverageWorkHoursForYear(String calculateAverageHoursYearName, String calculateAverageHoursYear) {
        Employee employee = employees.get(calculateAverageHoursYearName);
        if (employee != null) {
            int totalHours = 0;
            for (Map.Entry<String, Integer> entry : employee.getWorkHours().entrySet()) {
                if (entry.getKey().startsWith(calculateAverageHoursYear)) {
                    totalHours += entry.getValue();
                }
            }
            int averageHours = totalHours / employee.getWorkHours().size();
            System.out.println("Average work hours for " + calculateAverageHoursYearName + " in " + calculateAverageHoursYear + ": " + averageHours);
        } else {
            System.out.println("Employee not found: " + calculateAverageHoursYearName);
        }
    }

    private void calculateTotalWorkHoursForYear(String calculateTotalHoursYearName, String calculateTotalHoursYear) {
        Employee employee = employees.get(calculateTotalHoursYearName);
        if (employee != null) {
            int totalHours = 0;
            for (Map.Entry<String, Integer> entry : employee.getWorkHours().entrySet()) {
                if (entry.getKey().startsWith(calculateTotalHoursYear)) {
                    totalHours += entry.getValue();
                }
            }
            System.out.println("Total work hours for " + calculateTotalHoursYearName + " in " + calculateTotalHoursYear + ": " + totalHours);
        } else {
            System.out.println("Employee not found: " + calculateTotalHoursYearName);
        }
    }

    private void calculateAverageWorkHoursForMonth(String calculateAverageHoursMonthName, String calculateAverageHoursMonth) {
        Employee employee = employees.get(calculateAverageHoursMonthName);
        if (employee != null) {
            int totalHours = 0;
            for (Map.Entry<String, Integer> entry : employee.getWorkHours().entrySet()) {
                if (entry.getKey().startsWith(calculateAverageHoursMonth)) {
                    totalHours += entry.getValue();
                }
            }
            int averageHours = totalHours / employee.getWorkHours().size();
            System.out.println("Average work hours for " + calculateAverageHoursMonthName + " in " + calculateAverageHoursMonth + ": " + averageHours);
        } else {
            System.out.println("Employee not found: " + calculateAverageHoursMonthName);
        }
    }

    private void calculateTotalWorkHoursForMonth(String calculateTotalHoursMonthName, String calculateTotalHoursMonth) {
        Employee employee = employees.get(calculateTotalHoursMonthName);
        if (employee != null) {
            int totalHours = 0;
            for (Map.Entry<String, Integer> entry : employee.getWorkHours().entrySet()) {
                if (entry.getKey().startsWith(calculateTotalHoursMonth)) {
                    totalHours += entry.getValue();
                }
            }
            System.out.println("Total work hours for " + calculateTotalHoursMonthName + " in " + calculateTotalHoursMonth + ": " + totalHours);
        } else {
            System.out.println("Employee not found: " + calculateTotalHoursMonthName);
        }
    }
}

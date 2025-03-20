package seedu.address.model.attendance;

import seedu.address.model.leave.Leave;

/**
 * Represents the attendance details for a person in the address book.
 * Stores information such as the total number of working days, the number of absent days,
 * and the calculated attendance rate for the person.
 *
 * Guarantees:
 * The attendance rate is initially set to 100% and is recalculated whenever the absent day count is updated.
 */
public class Attendance {
    // Stores the number of working days so far in that year
    private static int workDayCount = 0;
    public static final String MESSAGE_CONSTRAINTS = "Absentees should be indicated by their NRIC "
            + "and each absentee's NRIC should be separated by at least one whitespace.";
    private int absentDayCount;
    // Stores the current attendance rate for this person in that year, initialized as 100% first
    private double attendanceRate;

    /**
     * Constructs an {@code Attendance} object with default values:
     * absent day count set to 0 and attendance rate set to 100%.
     */
    public Attendance() {
        this.absentDayCount = 0;
        this.attendanceRate = 100.0;
    }

    /**
     * Returns the total number of working days for the current year.
     *
     * @return The total number of working days.
     */
    public int getWorkDayCount() {
        return workDayCount;
    }

    /**
     * Sets the total number of working days for the current year.
     *
     * <p>This is a static method that modifies the shared {@code workDayCount} across all instances
     * of {@code Attendance}. This value represents the total number of days employees are expected
     * to work in the current year up to date and is used in attendance rate calculations for each person.</p>
     *
     * <p><strong>Note:</strong> This method does not perform validation on the provided value.
     * It should be used carefully, typically during initialization or data restoration processes.</p>
     *
     * @param count The total number of working days to set. Should be a non-negative integer.
     */
    public static void setWorkDayCount(int count) {
        workDayCount = count;
    }

    /**
     * Returns the total number of absent days recorded for this person.
     *
     * @return The number of absent days.
     */
    public int getAbsentDayCount() {
        return this.absentDayCount;
    }

    /**
     * Calculates and updates the attendance rate based on the number of working days and absent days.
     * The rate is calculated as the percentage of present days to the total working days.
     * If the total working days is zero, the attendance rate is set to 100% to avoid division by zero.
     */
    public void calculateAttendanceRate() {
        if (workDayCount == 0) {
            attendanceRate = 100.0; // Avoid division by zero
        } else {
            attendanceRate = ((double) (workDayCount - absentDayCount) / workDayCount) * 100;
        }
    }

    /**
     * Returns the current attendance rate for the person, calculated based on the number of absent days.
     *
     * @return The attendance rate as a double value.
     */
    public double getAttendanceRate() {
        calculateAttendanceRate();
        return attendanceRate;
    }

    /**
     * Increments the number of working days for the current year.
     */
    public static void incrementWorkDayCount() {
        workDayCount++;
    }

    /**
     * Increments the number of absent days for the person.
     */
    public void incrementAbsentDay() {
        this.absentDayCount++;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Attendance)) {
            return false;
        }

        Attendance otherAttendance = (Attendance) other;
        return absentDayCount == otherAttendance.absentDayCount
                && Double.compare(getAttendanceRate(), otherAttendance.getAttendanceRate()) == 0;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(absentDayCount) + Double.hashCode(attendanceRate);
    }

    @Override
    public String toString() {
        return "Number of working days so far this year: " + workDayCount
                + " Number of days absent: " + absentDayCount
                + " Attendance Rate: " + attendanceRate;
    }

}

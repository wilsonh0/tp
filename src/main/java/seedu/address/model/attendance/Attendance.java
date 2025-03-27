package seedu.address.model.attendance;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the attendance details for a person in the address book.
 * Stores information such as the total number of working days, the number of absent days,
 * and the calculated attendance rate for the person.
 *
 * Guarantees:
 * The attendance rate is initially set to 100% and is recalculated whenever the absent day count is updated.
 */
public class Attendance {
    public static final String MESSAGE_CONSTRAINTS = "Both the number of working days and absent days "
        + "should be larger than or equal to 0. Number of working days should be larger than number "
        + "of working days.";
    // Stores the number of working days for this person
    private int workDayCount;
    private int absentDayCount;
    // Stores the current attendance rate for this person in that year, initialized as 100% first
    private double attendanceRate;

    /**
     * Constructs an {@code Attendance} object with default values:
     * absent day count set to 0 and attendance rate set to 100%.
     */
    public Attendance() {
        this.workDayCount = 0;
        this.absentDayCount = 0;
        this.attendanceRate = 100.0;
    }

    /**
     * Constructs an {@code Attendance} object with the specified workday and absent day counts.
     * The attendance rate is automatically calculated based on the given values.
     *
     * @param workDayCount The number of workdays recorded.
     * @param absentDayCount The number of days the person was absent.
     * @throws NullPointerException if {@code workDayCount} or {@code absentDayCount} is null.
     */
    public Attendance(int workDayCount, int absentDayCount) {
        checkArgument(isValidAttendance(workDayCount, absentDayCount), MESSAGE_CONSTRAINTS);
        this.workDayCount = workDayCount;
        this.absentDayCount = absentDayCount;
        calculateAttendanceRate();
    }

    /**
     * Returns true if given workDayCount and absentDayCount values are valid when exist together.
     */
    public static boolean isValidAttendance(int workDayCount, int absentDayCount) {
        if (!isValidWorkDayCount(workDayCount) || !isValidAbsentDayCount(absentDayCount)) {
            return false;
        }

        if (workDayCount < absentDayCount) {
            return false;
        }

        return true;
    }

    /**
     * Returns true if given workDayCount value is valid.
     */
    public static boolean isValidWorkDayCount(int workDayCount) {
        if (workDayCount < 0) {
            return false;
        }

        return true;
    }

    /**
     * Returns true if given absentDayCount value is valid.
     */
    public static boolean isValidAbsentDayCount(int absentDayCount) {
        if (absentDayCount < 0) {
            return false;
        }

        return true;
    }

    /**
     * Returns the total number of working days for the person.
     *
     * @return The total number of working days for the person.
     */
    public int getWorkDayCount() {
        return this.workDayCount;
    }

    /**
     * Sets the total number of workdays recorded for the person and
     * recalculates the attendance rate accordingly.
     *
     * @param workDayCount the number of workdays to set
     */
    public void setWorkDayCount(int workDayCount) {
        checkArgument(isValidWorkDayCount(workDayCount), MESSAGE_CONSTRAINTS);
        checkArgument(isValidAttendance(workDayCount, absentDayCount), MESSAGE_CONSTRAINTS);
        this.workDayCount = workDayCount;
        calculateAttendanceRate();
    }

    /**
     * Returns the total number of absent days recorded for the person.
     *
     * @return The number of absent days.
     */
    public int getAbsentDayCount() {
        return this.absentDayCount;
    }

    /**
     * Sets the total number of absent days recorded for the person
     * and recalculates the attendance rate accordingly.
     *
     * @param absentDayCount the number of days to set as absent
     */
    public void setAbsentDayCount(int absentDayCount) {
        checkArgument(isValidAbsentDayCount(absentDayCount), MESSAGE_CONSTRAINTS);
        checkArgument(isValidAttendance(workDayCount, absentDayCount), MESSAGE_CONSTRAINTS);
        this.absentDayCount = absentDayCount;
        calculateAttendanceRate();
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
     * Increments the number of working days for the person.
     */
    public void incrementWorkDay() {
        this.workDayCount++;
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
        return this.workDayCount == otherAttendance.workDayCount
                && this.absentDayCount == otherAttendance.absentDayCount;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(workDayCount) + Integer.hashCode(absentDayCount);
    }

    @Override
    public String toString() {
        return "Number of working days so far this year: " + this.workDayCount
                + "; Number of days absent: " + this.absentDayCount
                + "; Attendance Rate: " + this.attendanceRate;
    }

}

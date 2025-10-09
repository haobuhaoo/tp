package seedu.address.ui;

import java.time.LocalDate;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Provides a simple bridge for UI components to access attendance information
 * without requiring direct references to the {@code Model}.
 * <p>
 * This class allows the UI (e.g. {@code PersonCard}) to query whether a student's
 * attendance is present, absent, or unrecorded for the currently displayed date.
 * The logic layer installs function references through {@link #install(BiFunction, Supplier)},
 * enabling loose coupling between the UI and model.
 * </p>
 */
public final class UiAttendanceAccess {
    /** Function that returns the student's attendance status (true = present, false = absent, null = not recorded). */
    private static BiFunction<String, LocalDate, Boolean> attendanceFn = (n, d) -> null;

    /** Supplier that provides the current date used for attendance lookup. */
    private static Supplier<LocalDate> currentDateSupplier = LocalDate::now;

    /** Prevents instantiation of this utility class. */
    private UiAttendanceAccess() {}

    /**
     * Installs the function and date supplier used by UI elements to query attendance.
     *
     * @param fn a function mapping (student name, date) → attendance status
     * @param dateSupplier a supplier providing the current date to query
     */
    public static void install(BiFunction<String, LocalDate, Boolean> fn,
                               Supplier<LocalDate> dateSupplier) {
        attendanceFn = fn;
        currentDateSupplier = dateSupplier;
    }

    /**
     * Returns the attendance status for the given student on the current UI date.
     *
     * @param name the student's name
     * @return {@code true} if present, {@code false} if absent, or {@code null} if not recorded
     */
    public static Boolean getStatus(String name) {
        return attendanceFn.apply(name, currentDateSupplier.get());
    }
}

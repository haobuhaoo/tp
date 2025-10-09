package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.UiAttendanceAccess;

/**
 * Records attendance for a student on a date.
 * <p>
 * Format: {@code attendance n/NAME d/YYYY-MM-DD s/1|0}
 */
public class AttendanceCommand extends Command {
    public static final String COMMAND_WORD = "attendance";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Records attendance.\n"
            + "Parameters: n/NAME d/YYYY-MM-DD s/1|0\n"
            + "Example: " + COMMAND_WORD + " n/marcus d/2025-09-19 s/1";

    private static final int NAME_MAX = 50;

    private final String nameRaw;
    private final String dateRaw;
    private final String statusRaw;

    /**
     * Constructs an {@code AttendanceCommand}.
     *
     * @param nameRaw   raw student name (validated in {@link #execute(Model)})
     * @param dateRaw   raw date in YYYY-MM-DD (validated in {@link #execute(Model)})
     * @param statusRaw raw status: "1" for present or "0" for absent (validated in {@link #execute(Model)})
     */
    public AttendanceCommand(String nameRaw, String dateRaw, String statusRaw) {
        this.nameRaw = nameRaw;
        this.dateRaw = dateRaw;
        this.statusRaw = statusRaw;
    }

    /**
     * Executes the command to record attendance.
     *
     * @param model the model to operate on; must be non-null
     * @return a {@link CommandResult} describing the outcome
     * @throws CommandException if validation fails or the same status was already recorded for the same date
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // --- validate name
        String name = nameRaw.trim().replaceAll("\\s+", " ");
        if (name.length() == 0 || name.length() > NAME_MAX) {
            throw new CommandException("Invalid student name: A name that is longer than 50 characters.");
        }
        if (!model.hasPersonName(name)) {
            throw new CommandException("Invalid student name: no matching student found.");
        }

        // --- validate date
        final LocalDate date;
        try {
            date = LocalDate.parse(dateRaw);
        } catch (DateTimeParseException ex) {
            throw new CommandException("Invalid date. The format must be YYYY-MM-DD.");
        }

        // --- validate status
        final boolean present;
        if ("1".equals(statusRaw)) {
            present = true;
        } else if ("0".equals(statusRaw)) {
            present = false;
        } else {
            throw new CommandException("Invalid status. Use 1 for present, 0 for absent.");
        }

        var idx = model.getAttendanceIndex();

        // duplicate handling: same value on same date -> error
        Optional<Boolean> existing = idx.get(name, date);
        if (existing.isPresent() && existing.get() == present) {
            String word = present ? "Present" : "Absent";
            throw new CommandException(
                    "Student " + name + " is already marked as " + word + " on " + date + ".");
        }

        idx.put(name, date, present);
        idx.setCurrentUiDate(date);

        String statusWord = present ? "Present" : "Absent";

        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        // Force UI to refresh PersonListPanel
        UiAttendanceAccess.install((n, d) ->
                model.getAttendanceIndex().get(n, d).orElse(null), () ->
                model.getAttendanceIndex().getCurrentUiDate());

        return new CommandResult("Success: Attendance recorded: " + name + ", " + date + ", " + statusWord + ".");
    }
}

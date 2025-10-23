package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import java.util.Locale;
import java.util.Objects;

/**
 * A validated, case-insensitive group name.
 * Rules: 1–30 chars; letters/digits/spaces and '-' or '/'; trimmed; multiple spaces collapsed.
 */
public final class GroupName {

    public static final String MESSAGE_CONSTRAINTS =
            "Group name: 1–30 characters; letters/digits/spaces and '-' or '/'; not only spaces.";

    // Accepts letters/digits/spaces and - or /
    private static final String BASIC_REGEX = "[A-Za-z0-9\\-\\/ ]+";

    private final String canonical; // trimmed + collapsed spaces (keeps original case)
    private final String key; // lowercase key for identity (case-insensitive)

    private GroupName(String canonical, String key) {
        this.canonical = canonical;
        this.key = key;
    }

    /** Factory with validation + normalization. */
    public static GroupName of(String name) {
        requireNonNull(name);
        String trimmed = name.trim().replaceAll("\\s+", " ");
        if (trimmed.isEmpty() || trimmed.length() > 30 || !trimmed.matches(BASIC_REGEX)) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        return new GroupName(trimmed, trimmed.toLowerCase(Locale.ROOT));
    }

    /** Canonical display form (trimmed, collapsed spaces, original letter case preserved). */
    @Override
    public String toString() {
        return canonical;
    }

    /** Internal case-insensitive key string. */
    public String key() {
        return key;
    }

    @Override
    public boolean equals(Object obj) {
        return (this == obj)
                || (obj instanceof GroupName && key.equals(((GroupName) obj).key));
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}

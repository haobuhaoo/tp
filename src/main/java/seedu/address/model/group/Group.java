package seedu.address.model.group;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * A first-class group. Wraps a {@link GroupName}.
 * Future-friendly placeholder for metadata (e.g., color).
 */
public final class Group {

    private final GroupName name;

    public Group(GroupName name) {
        this.name = requireNonNull(name);
    }

    public GroupName getName() {
        return name;
    }

    @Override
    public String toString() {
        return name.toString();
    }

    @Override
    public boolean equals(Object other) {
        return (this == other) || (other instanceof Group && name.equals(((Group) other).name));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

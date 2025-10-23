package seedu.address.model.group;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class GroupNameTest {

    @Test
    public void of_validNames_success() {
        // Should construct without throwing
        assertDoesNotThrow(() -> GroupName.of("  group   a  "));
        assertDoesNotThrow(() -> GroupName.of("Group A"));
        assertDoesNotThrow(() -> GroupName.of("Math-101"));
        assertDoesNotThrow(() -> GroupName.of("CS/2030"));

        // Variants that should be considered the "same" logically
        GroupName n1 = GroupName.of("  group   a  ");
        GroupName n2 = GroupName.of("Group A");
        assertEquals(n2, n1);
        assertEquals(n2.hashCode(), n1.hashCode());
    }

    @Test
    public void of_invalidNames_throw() {
        // empty after trim
        assertThrows(IllegalArgumentException.class, () -> GroupName.of("   "));
        // too long (31 chars)
        assertThrows(IllegalArgumentException.class, () -> GroupName.of("a".repeat(31)));
        // illegal symbols
        assertThrows(IllegalArgumentException.class, () -> GroupName.of("Group@A"));
    }

    @Test
    public void equals_hash() {
        GroupName a = GroupName.of("Group A");
        GroupName b = GroupName.of(" group   a ");
        GroupName c = GroupName.of("Group B");

        // Equal despite spacing/case variants
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        // Different names are not equal
        assertNotEquals(a, c);
    }
}

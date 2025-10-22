package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // invalid description
        assertFalse(Description.isValidDescription(null));
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // whitespace only

        // valid description
        assertTrue(Description.isValidDescription("valid"));
        assertTrue(Description.isValidDescription("!@#$%^&*-_+=(){}[]|/,.?<>:;`~")); // special characters
    }

    @Test
    public void equals() {
        Description desc = new Description("valid desc");

        // same values -> return true
        assertEquals(new Description("valid desc"), desc);

        // same object -> return true
        assertEquals(desc, desc);

        // null -> return false
        assertNotEquals(null, desc);

        // different type -> return false
        assertNotEquals(5.0f, desc);

        // different values -> return false
        assertNotEquals(new Description("diff desc"), desc);

        // different casing -> return true
        assertEquals(new Description("VALID DESC"), desc);
    }

    @Test
    public void hashCodeMethod() {
        Description desc = new Description("valid DESC");
        assertEquals(desc.hashCode(), new Description("valid desc").hashCode());
    }
}

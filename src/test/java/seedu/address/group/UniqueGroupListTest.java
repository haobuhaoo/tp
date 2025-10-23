package seedu.address.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.UniqueGroupList;

public class UniqueGroupListTest {

    @Test
    public void addContains_success() {
        UniqueGroupList list = new UniqueGroupList();
        GroupName name = GroupName.of("Group A");
        list.add(new Group(name));
        org.junit.jupiter.api.Assertions.assertTrue(list.contains(name));
    }

    @Test
    public void addDuplicate_throws() {
        UniqueGroupList list = new UniqueGroupList();
        GroupName name = GroupName.of("Group A");
        list.add(new Group(name));
        assertThrows(IllegalArgumentException.class, () ->
                list.add(new Group(GroupName.of(" group   a "))));
    }

    @Test
    public void removeMissing_throws() {
        UniqueGroupList list = new UniqueGroupList();
        assertThrows(IllegalArgumentException.class, () ->
                list.remove(GroupName.of("Group A")));
    }

    @Test
    public void setGroupsRejectsDuplicates_throws() {
        UniqueGroupList list = new UniqueGroupList();
        Group g1 = new Group(GroupName.of("Group A"));
        Group g2 = new Group(GroupName.of(" group   a "));
        assertThrows(IllegalArgumentException.class, () ->
                list.setGroups(List.of(g1, g2)));
    }

    @Test
    public void setGroupsReplaces_ok() {
        UniqueGroupList list = new UniqueGroupList();
        Group g1 = new Group(GroupName.of("X"));
        Group g2 = new Group(GroupName.of("Y"));
        list.setGroups(List.of(g1, g2));
        assertEquals(2, list.asUnmodifiableObservableList().size());
    }
}

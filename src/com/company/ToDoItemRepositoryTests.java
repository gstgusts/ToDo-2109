package com.company;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ToDoItemRepositoryTests {
    @Test
    public void delete_to_do_is_true() {
        ToDoItemRepository repo = new ToDoItemRepository();
        var res = repo.deleteToDo(9);
        assertEquals(true, res);
    }
}

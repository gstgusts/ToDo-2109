package com.company;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ToDoItemRepositoryTests {
    @Test
    public void delete_to_do_is_true() {
        ToDoItemRepository repo = new ToDoItemRepository();
        var res = repo.deleteToDo(9);
        assertEquals(true, res);
    }

    @Test
    public void update_to_do_is_true() {
        ToDoItemRepository repo = new ToDoItemRepository();
        var todo = new ToDo(2, "Iznest miskasti", true, LocalDateTime.now().plusDays(3),LocalDateTime.now(),"", true);

        var res = repo.updateToDo(todo);

        assertEquals(true, res);
    }


    @Test
    public void mark_to_do_as_done_is_true() {
        var repo = new ToDoItemRepository();
        var res = repo.markToDoAsDone(10);

        assertTrue(res);
    }
}

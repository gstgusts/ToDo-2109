package com.company;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ToDoItemRepositoryTests {
    @Test
    public void delete_to_do_is_true() {
        ToDoItemRepository repo = new ToDoItemRepository(1);
        var res = repo.deleteToDo(9);
        assertEquals(true, res);
    }

    @Test
    public void update_to_do_is_true() {
        ToDoItemRepository repo = new ToDoItemRepository(1);
        var user = new User(1,"gusts","Gusts","Linkevics");
        var todo = new ToDo(2, "Iznest miskasti", true, LocalDateTime.now().plusDays(3),LocalDateTime.now(),"", true, user);

        var res = repo.updateToDo(todo);

        assertEquals(true, res);
    }


    @Test
    public void mark_to_do_as_done_is_true() {
        var repo = new ToDoItemRepository(1);
        var res = repo.markToDoAsDone(10);

        assertTrue(res);
    }

    @Test
    public void get_todo_by_id_is_true() {
        var repo = new ToDoItemRepository(1);
        var todo = repo.getById(1);

        assertNotNull(todo);
    }
}

package com.company;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ToDoItemRepository {

    private static final String connectionUrl = "jdbc:mysql://localhost:3306/todo?serverTimezone=UTC";
    private static final String userName = "todo";
    private static final String password = "todo123";

    public List<ToDo> getAllImportantItems() {
        var sqlText = "SELECT * FROM todo.todo_items where ti_is_important = 1 and ti_is_done = 0";

        List<ToDo> items = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
            PreparedStatement ps = connection.prepareStatement(sqlText);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                items.add(ToDo.create(rs));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return items;
    }

    public List<ToDo> getAllItemsForDay() {
        var from = LocalDate.now();
        var to = LocalDate.now().plusDays(1);
        return getAllItemsForDay(LocalDateTime.of(from.getYear(), from.getMonth(), from.getDayOfMonth(), 0,0,0),
                LocalDateTime.of(to.getYear(), to.getMonth(), to.getDayOfMonth(), 0,0,0));
    }

    public List<ToDo> getAllItemsForDay(LocalDateTime from, LocalDateTime to) {
        var sqlText = "select * from todo.todo_items where ti_is_done = 0 and (ti_due_date between ? and ?)";

        List<ToDo> items = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
            PreparedStatement ps = connection.prepareStatement(sqlText);

            ps.setTimestamp(1, Timestamp.valueOf(from));
            ps.setTimestamp(2, Timestamp.valueOf(to));

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                items.add(ToDo.create(rs));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return items;
    }
}

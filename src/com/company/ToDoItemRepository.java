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

    private Connection connection;

    public List<ToDo> getAllImportantItems() {
        var sqlText = "SELECT * FROM todo.todo_items where ti_is_important = 1 and ti_is_done = 0";

        try {
            var ps = getPreparedStatement(sqlText);
            return getItems(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return new ArrayList<>();
    }

    public List<ToDo> getAllItemsForDay() {
        var from = LocalDate.now();
        var to = LocalDate.now().plusDays(1);
        return getAllItemsForDay(LocalDateTime.of(from.getYear(), from.getMonth(), from.getDayOfMonth(), 0,0,0),
                LocalDateTime.of(to.getYear(), to.getMonth(), to.getDayOfMonth(), 0,0,0));
    }

    public List<ToDo> getAllItemsForDay(LocalDateTime from, LocalDateTime to) {
        var sqlText = "select * from todo.todo_items where ti_is_done = 0 and (ti_due_date between ? and ?)";

        List<ToDo> ps = getItems(from, to, sqlText);
        if (ps != null) return ps;

        return new ArrayList<>();
    }

    public List<ToDo> getDoneItemsForLast7Days() {
        return getDoneItemsForLast7Days(LocalDateTime.now());
    }

    public List<ToDo> getDoneItemsForLast7Days(LocalDateTime to) {

        var from = to.minusDays(7);
        var sqlText = "select * from todo.todo_items where ti_is_done = 1 and (ti_finished_date between ? and ?)";

        List<ToDo> ps = getItems(from, to, sqlText);
        if (ps != null) return ps;

        return new ArrayList<>();
    }

    public ToDo getById(int id) {

        var sqlText = "select * from todo.todo_items where ti_id = ?";

        try {
            var ps = getPreparedStatement(sqlText);
            ps.setInt(1, id);
            return getItem(ps);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    private PreparedStatement getPreparedStatement(String sqlText) throws SQLException {
        connection = DriverManager.getConnection(connectionUrl, userName, password);
        return connection.prepareStatement(sqlText);
    }

    private ToDo getItem(PreparedStatement ps) throws SQLException {
        var items = getItems(ps);
        return items.get(0);
    }

    private List<ToDo> getItems(PreparedStatement ps) throws SQLException {

        List<ToDo> items = new ArrayList<>();

        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            items.add(ToDo.create(rs));
        }

        return items;
    }

    private List<ToDo> getItems(LocalDateTime from, LocalDateTime to, String sqlText) {
        try {
            var ps = getPreparedStatement(sqlText);

            ps.setTimestamp(1, Timestamp.valueOf(from));
            ps.setTimestamp(2, Timestamp.valueOf(to));

            return getItems(ps);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}

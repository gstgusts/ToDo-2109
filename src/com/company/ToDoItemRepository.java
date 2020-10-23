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

    private int userId;

    private Connection connection;

    public ToDoItemRepository(int userId) {
        this.userId = userId;
    }

    public List<ToDo> getAllImportantItems() {
        var sqlText = "SELECT * FROM todo.v_todo_items_with_user where ti_is_important = 1 and ti_is_done = 0 and user_id = ?";

        try {
            var ps = getPreparedStatement(sqlText);

            ps.setInt(1, userId);

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
        var sqlText = "select * from todo.v_todo_items_with_user where ti_is_done = 0 and (ti_due_date between ? and ?) and user_id = ?";

        List<ToDo> ps = getItems(from, to, sqlText);
        if (ps != null) return ps;

        return new ArrayList<>();
    }

    public List<ToDo> getDoneItemsForLast7Days() {
        return getDoneItemsForLast7Days(LocalDateTime.now());
    }

    public List<ToDo> getDoneItemsForLast7Days(LocalDateTime to) {

        var from = to.minusDays(7);
        var sqlText = "select * from todo.v_todo_items_with_user where ti_is_done = 1 and (ti_finished_date between ? and ?) and user_id = ?";

        List<ToDo> ps = getItems(from, to, sqlText);
        if (ps != null) return ps;

        return new ArrayList<>();
    }

    public ToDo getById(int id) {

        var sqlText = "select * from todo.v_todo_items_with_user where ti_id = ?";

        try {
            var ps = getPreparedStatement(sqlText);
            ps.setInt(1, id);
            return getItem(ps);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public int addToDo(ToDo item) {
        try {
            connection = getConnection();
            var stm = connection.prepareCall("{call spAddToDo(?,?,?,?,?,?,?,?)}");
            stm.setString("name", item.getName());
            stm.setBoolean("is_done", item.isDone());
            stm.setTimestamp("due_date", getTimeStamp(item.getDueDate()));
            stm.setTimestamp("finished_date", getTimeStamp(item.getFinishedDate()));
            stm.setString("description", item.getDescription());
            stm.setBoolean("is_important", item.isImportant());
            stm.setInt("user_id", item.getOwner().getId());

            stm.registerOutParameter("id", Types.INTEGER);

            stm.executeUpdate();

            return stm.getInt("id");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public boolean deleteToDo(int id) {
        try {
            connection = getConnection();
            var stm = connection.prepareCall("{call spDeleteToDo(?)}");
            stm.setInt("id", id);

            return stm.executeUpdate() == 1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean updateToDo(ToDo item) {
        try {
            connection = getConnection();
            var stm = connection.prepareCall("{call spUpdateToDo(?,?,?,?,?,?,?)}");
            stm.setInt("id", item.getId());
            stm.setString("name", item.getName());
            stm.setBoolean("is_done", item.isDone());
            stm.setTimestamp("due_date", getTimeStamp(item.getDueDate()));
            stm.setTimestamp("finished_date", getTimeStamp(item.getFinishedDate()));
            stm.setString("description", item.getDescription());
            stm.setBoolean("is_important", item.isImportant());

            return stm.executeUpdate() == 1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean markToDoAsDone(int id) {
        try {
            connection = getConnection();
            var stm = connection.prepareCall("{call spMarkToDoAsDone(?)}");
            stm.setInt("id", id);

            return stm.executeUpdate() == 1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    private Timestamp getTimeStamp(LocalDateTime dateTime) {
        if(dateTime == null) {
            return null;
        }

        return Timestamp.valueOf(dateTime);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl, userName, password);
    }

    private PreparedStatement getPreparedStatement(String sqlText) throws SQLException {
        connection = getConnection();
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
            ps.setInt(3, userId);

            return getItems(ps);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}

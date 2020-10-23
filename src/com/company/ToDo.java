package com.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ToDo {
    private int id;
    private String name;
    private boolean isDone;
    private LocalDateTime dueDate;
    private LocalDateTime finishedDate;
    private String description;
    private boolean isImportant;
    private User owner;

    public ToDo(int id, String name, boolean isDone, LocalDateTime dueDate, LocalDateTime finishedDate, String description, boolean isImportant, User owner) {
        this.id = id;
        this.name = name;
        this.isDone = isDone;
        this.dueDate = dueDate;
        this.finishedDate = finishedDate;
        this.description = description;
        this.isImportant = isImportant;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(LocalDateTime finishedDate) {
        this.finishedDate = finishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean important) {
        isImportant = important;
    }

    public static ToDo create(ResultSet rs) {
        try {
            return new ToDo(rs.getInt("ti_id"),
                    rs.getString("ti_name"),
                    rs.getBoolean("ti_is_done"),
                    getLocalDateTime(rs.getTimestamp("ti_due_date")),
                    getLocalDateTime(rs.getTimestamp("ti_finished_date")),
                    rs.getString("ti_description"),
                    rs.getBoolean("ti_is_important"),
                    User.create(rs)
                    );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    private static LocalDateTime getLocalDateTime(Timestamp tmp) {
        if(tmp != null) {
            return tmp.toLocalDateTime();
        }

        return null;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}

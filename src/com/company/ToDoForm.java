package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

public class ToDoForm extends JFrame {

    JPanel mainPanel;
    JPanel buttonPanel;
    JButton btnLoadImportant;
    JButton btnLoadToday;

    ToDoItemRepository repo;

    JScrollPane scrollPane;
    JTable dataTable;

    String[] columns;

    public ToDoForm() {
        super("ToDo");

        repo = new ToDoItemRepository(1);

        this.setSize(300, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        this.setContentPane(mainPanel);

        columns = new String[] { "Name", "Is done" };
    }

    public void open() {
        initUi();
        getAllImportantItems();
        this.setVisible(true);
    }

    private void initUi() {
        buttonPanel = new JPanel();
        buttonPanel.setBounds(0,0,300, 30);
        buttonPanel.setBackground(Color.GRAY);
        buttonPanel.setLayout(new GridLayout(1, 3));

        mainPanel.add(buttonPanel);

        btnLoadImportant = new JButton("Important");
        btnLoadImportant.addActionListener(e -> {
            getAllImportantItems();
        });

        buttonPanel.add(btnLoadImportant);

        btnLoadToday = new JButton("Today");
        btnLoadToday.addActionListener(e->{
           getAllItemsForDay();
        });

        buttonPanel.add(btnLoadToday);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 50, 300, 550);
        mainPanel.add(scrollPane);

        Object[][] data = new Object[][] {  };

        dataTable = new JTable(data, columns);

        scrollPane.setViewportView(dataTable);
    }

    private void getAllImportantItems() {
        bindDataToTable(repo.getAllImportantItems());
    }

    private void getAllItemsForDay() {
        bindDataToTable(repo.getAllItemsForDay());
    }

    private void getAllItemsForDay(LocalDateTime from, LocalDateTime to) {
        bindDataToTable(repo.getAllItemsForDay(from, to));
    }

    private void bindDataToTable(List<ToDo> items) {
        var data = new Object[items.size()][2];

        for (int i = 0; i < items.size(); i++) {
            data[i][0] = items.get(i).getName();
            data[i][1] = items.get(i).isDone();
        }

        DefaultTableModel model = new DefaultTableModel(data, columns);
        dataTable.setModel(model);
    }
}

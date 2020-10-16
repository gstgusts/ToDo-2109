package com.company;

import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        ToDoItemRepository repo = new ToDoItemRepository();
        var importantItems = repo.getAllItemsForDay();

        for (var item :
                importantItems) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            System.out.println(item.getName() +" "+ item.getDueDate().format(df));
        }
    }
}

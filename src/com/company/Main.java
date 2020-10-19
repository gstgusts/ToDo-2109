package com.company;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        ToDoItemRepository repo = new ToDoItemRepository();
        var importantItems = repo.getDoneItemsForLast7Days();

        for (var item :
                importantItems) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            System.out.println(item.getName() +" "+ item.getDueDate().format(df));
        }



        var todo1 = repo.getById(3);

        System.out.println(todo1.getName());

    }
}

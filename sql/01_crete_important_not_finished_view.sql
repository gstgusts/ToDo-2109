CREATE VIEW `vImportant_Unfinished_To_Does` AS
select * from todo.todo_items where ti_is_important = 1 and ti_is_done=0

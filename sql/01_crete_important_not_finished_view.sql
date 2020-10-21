CREATE VIEW `v_imp_not_finished` AS
select * from todo.todo_items where ti_is_important = 1 and ti_is_done=0

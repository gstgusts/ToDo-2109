CREATE VIEW `v_todo_items_with_user` AS
select * from todo.todo_items as ti
                  inner join todo.todo_users as u on ti.ti_user_id = u.user_id;

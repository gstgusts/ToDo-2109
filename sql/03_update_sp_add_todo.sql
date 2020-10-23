USE `todo`;
DROP procedure IF EXISTS `spAddToDo`;

DELIMITER $$
USE `todo`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `spAddToDo`(
    in name nvarchar(200),
    in is_done bit(1),
    in due_date timestamp,
    in finished_date timestamp,
    in description nvarchar(500),
    in is_important bit(1),
    in user_id int,
    out id int)
BEGIN

    insert into todo.todo_items (ti_name, ti_is_done, ti_due_date, ti_finished_date, ti_description, ti_is_important, ti_user_id)
    values (name, is_done, due_date, finished_date, description, is_important, user_id);

    select last_insert_id() into id;

END$$

DELIMITER ;
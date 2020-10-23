CREATE TABLE `todo`.`todo_users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_login` VARCHAR(50) NOT NULL,
  `user_password` VARCHAR(50) NOT NULL,
  `user_name` NVARCHAR(100) NOT NULL,
  `user_surname` NVARCHAR(100) NOT NULL,
  PRIMARY KEY (`user_id`));

ALTER TABLE `todo`.`todo_items`
ADD COLUMN `ti_user_id` INT NOT NULL DEFAULT 1 AFTER `ti_is_important`;

ALTER TABLE `todo`.`todo_items`
ADD INDEX `FK_todo_items_todo_users_ti_user_id_idx` (`ti_user_id` ASC) VISIBLE;
;
ALTER TABLE `todo`.`todo_items`
ADD CONSTRAINT `FK_todo_items_todo_users_ti_user_id`
  FOREIGN KEY (`ti_user_id`)
  REFERENCES `todo`.`todo_users` (`user_id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

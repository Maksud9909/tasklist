-- отсюда будем добавлять данные в schema.sql . Также, оно нам нужно, чтобы удобно пользоваться базой данных
-- пароли были сгенерированы, bcrypt-generator - он хещирует данные


insert into users (name, username, password)
values ('John Doe', 'johndoe@gmail.com', '123'), -- 12345
       ('Mike Smith', 'mikesmith@yahoo.com', '345');





insert into tasks (title, description, status, expirationDate)
values ('Buy cheese', null, 'TODO', '2023-01-29 12:00:00'),
       ('Do homework', 'Math, Physics, Literature', 'IN_PROGRESS', '2023-01-31 00:00:00'),
       ('Clean rooms', null, 'DONE', null),
       ('Call Mike', 'Ask about meeting', 'TODO', '2023-02-01 00:00:00');


insert into users_tasks (task_id,user_id)
values (1, 2),
       (2, 2),
       (3, 2),
       (4, 1);

insert into users_role (user_id, role)
values (1, 'ROLE_ADMIN'),
       (1, 'ROLE_USER'),
       (2, 'ROLE_USER');
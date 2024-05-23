insert into users (name, username, password)
values ('John Doe', 'johndoe@gmail.com', '$2a$12$YTZ5ayytUDpKvEkzxK5aPei/9WKQgsub8f3v2TNan0PYmLZMMj0Ny'),
       ('Mike Smith', 'mikesmith@yahoo.com', '$2a$12$YTZ5ayytUDpKvEkzxK5aPei/9WKQgsub8f3v2TNan0PYmLZMMj0Ny');

insert into tasks (title, description, status, expiration_date)
values ('Buy cheese', null, 'TODO', '2023-01-29 12:00:00'),
       ('Do homework', 'Math, Physics, Literature', 'IN_PROGRESS', '2023-01-31 00:00:00'),
       ('Clean rooms', null, 'DONE', null),
       ('Call Mike', 'Ask about meeting', 'TODO', '2023-02-01 00:00:00');

insert into users_tasks (task_id, user_id)
values (1, 2),
       (2, 2),
       (3, 2),
       (4, 1);

insert into users_roles (user_id, role)
values (1, 'ROLE_ADMIN'),
       (1, 'ROLE_USER'),
       (2, 'ROLE_USER');
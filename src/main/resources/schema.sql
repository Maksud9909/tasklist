-- здесь мы будем создавать схему

-- здесь мы будем создавать схему
-- каждый раз, будет создаваться это схема, если уже есть то не будет
-- create schema if not exists tasklist;
--
create table if not exists users
(
  id bigserial primary key, --bigserial - это значит long
  name varchar(255) not null, -- имя будет размера 255 и не может быть null
  username varchar(255) not null unique, -- юзернейм пользователя должен быть 255, также должен быть уникальным
  password varchar(255) not null -- пароль будет размера 255 и не может быть null
);

create table if not exists tasks
(
  id bigserial primary key,
  title varchar(255) not null, -- здесь дефолтное значение не может быть null
  description varchar(255) null, -- дефолтное значение будет null
  status varchar(255) not null,
  expirationDate timestamp null
);

create table if not exists users_tasks -- это таблица для отношения многие ко многим между пользователями и задачами.
(
  user_id bigint not null,
  task_id bigint not null,
  primary key (user_id,task_id), -- Первичный ключ для этой таблицы состоит из комбинации user_id и task_id.

--Установлены внешние ключи (foreign key), чтобы обеспечить целостность данных при удалении или обновлении записей в таблицах users и tasks.
  constraint fk_users_tasks_users foreign key (user_id) references users(id) on delete cascade on update no action,
  constraint fk_users_tasks_tasks foreign key (task_id) references tasks(id) on delete cascade on update no action

);


create table if not exists users_role  --users_role - это таблица для хранения ролей пользователей.
(
  user_id bigint not null, --user_id - внешний ключ, связанный с полем id из таблицы users.
  role varchar(255) not null,
  primary key (user_id,role), --Первичный ключ для этой таблицы состоит из комбинации user_id и role.


  --Установлен внешний ключ (foreign key), чтобы обеспечить целостность данных при удалении или обновлении записей в таблице users.
  constraint fk_users_roles_users foreign key (user_id) references users (id) on delete cascade on update no action
);
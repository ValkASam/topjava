DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals; --не обязательно - каскадно очиститься
ALTER SEQUENCE global_seq RESTART WITH 100000;

-- password
INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');
-- admin
INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES ('ROLE_USER', 100000);
INSERT INTO user_roles (role, user_id) VALUES ('ROLE_ADMIN', 100001);

INSERT INTO meals(datetime, description, calories, user_id) VALUES ('2015-09-30 10:00:00','Завтрак',500,100000);
INSERT INTO meals(datetime, description, calories, user_id) VALUES ('2015-09-30 13:00:00','Обед',1000,100000);
INSERT INTO meals(datetime, description, calories, user_id) VALUES ('2015-09-30 20:00:00','Ужин',500,100000);
INSERT INTO meals(datetime, description, calories, user_id) VALUES ('2015-09-29 10:00:00','Завтрак',1000,100000);
INSERT INTO meals(datetime, description, calories, user_id) VALUES ('2015-09-29 13:00:00','Обед',500,100000);
INSERT INTO meals(datetime, description, calories, user_id) VALUES ('2015-09-29 20:00:00','Ужин',510,100000);

INSERT INTO meals(datetime, description, calories, user_id) VALUES ('2015-10-30 14:00:00','Админ ланч',510,100001);
INSERT INTO meals(datetime, description, calories, user_id) VALUES ('2015-10-30 21:00:00','Админ ужин',510,100001);


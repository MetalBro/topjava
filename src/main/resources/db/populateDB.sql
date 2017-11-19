DELETE FROM users;
DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM user_meals;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (dateTime, description, calories) VALUES
  (TIMESTAMP '2015-05-30 10:00:00', 'Завтрак', 500),
  (TIMESTAMP '2015-05-30 13:00:00', 'Обед', 1000),
  (TIMESTAMP '2015-05-30 20:00:00', 'Ужин', 500),
  (TIMESTAMP '2015-05-31 10:00:00', 'Завтрак', 1000),
  (TIMESTAMP '2015-05-31 13:00:00', 'Обед', 500),
  (TIMESTAMP '2015-05-31 20:00:00', 'Ужин', 510);

INSERT INTO user_meals (user_id, meal_id) VALUES
  (100000, 1),
  (100000, 2),
  (100000, 3),
  (100001, 4),
  (100001, 5),
  (100001, 6);
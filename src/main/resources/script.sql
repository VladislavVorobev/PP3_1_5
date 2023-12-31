

INSERT INTO users (name, surname, email, username, password)
VALUES
    ('Marat', 'Sizov', 'sizov@mail.ru', 'admin', '$2a$12$QR5SQ7AdaSQLDAK/uh2/tuAkbBVU9J4lEoLPEwXd2QFy5s3nQ3YpC'),
    ('Dmitriy', 'Ivanov', 'divanov@yandex.ru', 'user', '$2a$12$bKWK/fK0zeINCTv10lG1cOxpJzp3Yj7edGmni5z3m37QdxImLGfy6');

INSERT INTO roles (id, name)
VALUES
    ('1', 'ROLE_USER'),
    ('2', 'ROLE_ADMIN');

INSERT INTO users_roles (users_id, roles_id)
VALUES
    ('1', '2'),
    ('2', '1');

-- Import Script for testing and local dev

CREATE TABLE IF NOT EXISTS  greeting ( id serial PRIMARY KEY, value VARCHAR ( 255 ));

INSERT INTO greeting(id, value) VALUES (1, 'Hi there!');
INSERT INTO greeting(id, value) VALUES (2, 'Ey buenas!');
INSERT INTO greeting(id, value) VALUES (3, 'Morning!');
INSERT INTO greeting(id, value) VALUES (4, 'Buenos diaaaas!!');

ALTER SEQUENCE IF EXISTS hibernate_sequence RESTART WITH 5;
CREATE TABLE testApp_user (
  id serial PRIMARY KEY,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  pass_token varchar(255),
  salt varchar,
  name varchar(255)
);

CREATE TABLE test (
  id serial PRIMARY KEY,
  user_id int NOT NULL REFERENCES testApp_user,
  name varchar(255) NOT NULL
);

CREATE TABLE question (
  id serial PRIMARY KEY,
  test_id int NOT NULL REFERENCES test,
  text varchar(255) NOT NULL
);

CREATE TABLE option (
  id serial PRIMARY KEY,
  question_id int NOT NULL REFERENCES question,
  text varchar(255) NOT NULL,
  isCorrect boolean NOT NULL
);

CREATE TABLE link (
  id serial PRIMARY KEY,
  email varchar(255) NOT NULL,
  test_id int NOT NULL REFERENCES test,
  random_link varchar(255) DEFAULT NULL,
  right_answers_count int DEFAULT 0
);

CREATE TABLE attempt (
    id serial PRIMARY KEY,
    option_id int NOT NULL REFERENCES option,
    link_id int NOT NULL REFERENCES link
);
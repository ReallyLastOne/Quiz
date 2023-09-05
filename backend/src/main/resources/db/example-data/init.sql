-- example data to use in fresh development database

INSERT
INTO
  user_entity
  (id, nickname, email, password, roles)
VALUES
  (1, 'user', 'user@mail.com', 'user', ARRAY[0]), (3, 'admin', 'admin@mail.com', 'admin', ARRAY[0, 1]);
SELECT setval('user_entity_seq', 2);


INSERT
INTO
  tag
  (id, name, description)
VALUES
  (1, 'Linux', 'General knowledge about Linux based operating systems'), (2, 'Docker', 'Knowledge about Docker containerization platform - including CLI tool'), (3, 'Trivia', 'Not important question on all topics, sometimes tricky');
SELECT setval('tag_seq', 3);


INSERT
INTO
  question
  (id, content, correct_answer)
VALUES
  (1, 'What is NOT file permission in Linux?', 'Update'), (2, 'Who was the first USA president?', 'George Washington'), (3, 'What is the primary purpose of Docker?', 'To package and deploy applications in containers');
SELECT setval('exercise_seq', 3);


INSERT
INTO
  question_tags
  (question_id, tag_id)
VALUES
  (1, 1), (2, 3), (3, 2);

INSERT
INTO
  question_wrong_answers
  (question_id, wrong_answer)
VALUES
  (1, 'Read'), (1, 'Write'), (1, 'Execute'), (2, 'Thomas Jefferson'), (2, 'John F. Kennedy'), (2, 'John Adams'), (3, 'To manage cloud resources'), (3, 'To create interactive chatbots'), (3, 'To virtualize hardware for gaming');


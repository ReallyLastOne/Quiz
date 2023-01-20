insert into question_exercise(id, correct_answer, question) values (1, '1410', 'In what year did the Battle of Grunwald take place?');
insert into question_exercise_wrong_answers (question_exercise_id, wrong_answer) values (1, '1999'), (1, '1510'), (1, '2001');

insert into question_exercise(id, correct_answer, question) values (2, 'Pascal', 'Which of listed programming languages is not object oriented?');
insert into question_exercise_wrong_answers (question_exercise_id, wrong_answer) values (2, 'Java'), (2, 'C#'), (2, 'Scala');

insert into question_exercise(id, correct_answer, question) values (3, '2011', 'In which year Minecraft full version was released?');
insert into question_exercise_wrong_answers (question_exercise_id, wrong_answer) values (3, '2009'), (3, '2010'), (3, '2012');

insert into question_exercise(id, correct_answer, question) values (4, 'Streams', 'Which functionality is provided in Java 8?');
insert into question_exercise_wrong_answers (question_exercise_id, wrong_answer) values (4, 'Records'), (4, 'Multi line text blocks'), (4, 'Sealed classes');

insert into question_exercise(id, correct_answer, question) values (5, 'Lionel Messi', 'Which players has most FIFA World Cup appearances?');
insert into question_exercise_wrong_answers (question_exercise_id, wrong_answer) values (5, 'Cristiano Ronaldo'), (5, 'Lothar Matthaus'), (5, 'Miroslav Klose');

insert into translation_exercise(id, image_path) values (1, '');
insert into translation_map(translation_exercise_id, "translation", locale) values  (1, 'jabłko', 'pl'), (1, 'apple', 'en');
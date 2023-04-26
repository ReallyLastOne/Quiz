insert into question(id, correct_answer, content) values (nextval('question_seq'), '1410', 'In what year did the Battle of Grunwald take place?');
insert into question_wrong_answers (question_id, wrong_answer) values (currval('question_seq'), '1999'), (currval('question_seq'), '1510'), (currval('question_seq'), '2001');

insert into question(id, correct_answer, content) values (nextval('question_seq'), 'Pascal', 'Which of listed programming languages is not object oriented?');
insert into question_wrong_answers (question_id, wrong_answer) values (currval('question_seq'), 'Java'), (currval('question_seq'), 'C#'), (currval('question_seq'), 'Scala');

insert into question(id, correct_answer, content) values (nextval('question_seq'), '2011', 'In which year Minecraft full version was released?');
insert into question_wrong_answers (question_id, wrong_answer) values (currval('question_seq'), '2009'), (currval('question_seq'), '2010'), (currval('question_seq'), '2012');

insert into question(id, correct_answer, content) values (nextval('question_seq'), 'Streams', 'Which functionality is provided in Java 8?');
insert into question_wrong_answers (question_id, wrong_answer) values (currval('question_seq'), 'Records'), (currval('question_seq'), 'Multi line text blocks'), (currval('question_seq'), 'Sealed classes');

insert into question(id, correct_answer, content) values (nextval('question_seq'), 'Lionel Messi', 'Which players has most FIFA World Cup appearances?');
insert into question_wrong_answers (question_id, wrong_answer) values (currval('question_seq'), 'Cristiano Ronaldo'), (currval('question_seq'), 'Lothar Matthaus'), (currval('question_seq'), 'Miroslav Klose');

insert into phrase(id, image_path) values (nextval('phrase_seq') , '');
insert into translation_map(translation_id, "translation", locale) values  (currval('phrase_seq'), 'jabłko', 'pl'), (currval('phrase_seq'), 'apple', 'en');
-- example data to use in fresh development database

INSERT
INTO
  user_entity
  (id, nickname, email, password, roles)
VALUES
  (1, 'user', 'user@mail.com', '$2a$10$z0RSawXQa9RItZzcnLp9DuiOyOHCdUSCwrK6HJXfqdp2oudZwSl6S', ARRAY[0]), (3, 'admin', 'admin@mail.com', 'admin', ARRAY[0, 1]);
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
  (id, content, correct_answer, multiple_correct_answers)
VALUES
  (1, 'What is NOT file permission in Linux?', 'Update', false), (2, 'Who was the first USA president?', 'George Washington', false), (3, 'What is the primary purpose of Docker?', 'To package and deploy applications in containers', false),(4, 'What is the default shell for most Linux distributions?', 'Bash', false),(5, 'Which file permissions allow a user to read and write a file but prevent others from doing so?', '-rw-------', true),(6, 'Which of the following commands can be used to compress files in Linux?', 'tar', true),(7, 'Which of the following Linux distributions are Debian-based?', 'Ubuntu', true),(8, 'Which of the following are valid Linux package management systems?', 'APT', true),(9, 'What is the purpose of the ''ls'' command in Linux?', 'List files and directories', false),(10, 'Which command is used to create a new directory in Linux?', 'mkdir', false),(11, 'Which of the following is not a valid Linux file permission?', 'lrwxr-xr-x', false),(12, 'What does the ''sudo'' command in Linux allow you to do?', 'Run a command with superuser privileges', false),(13, 'Which Linux shell is known for its scripting capabilities and is the default shell for most Linux distributions?', 'Bash', false),(14, 'In Linux, which command is used to display the contents of a file on the terminal?', 'cat', false),(15, 'What is the purpose of the ''grep'' command in Linux?', 'Search for text patterns in files', false),(16, 'Which package management system is commonly used in Debian-based Linux distributions like Ubuntu?', 'APT', false), (17, 'What does the ''ps'' command in Linux do?', 'Display a list of currently running processes', false);

SELECT setval('exercise_seq', 17);


INSERT
INTO
  question_tags
  (question_id, tag_id)
VALUES
  (1, 1), (2, 3), (3, 2), (4, 1), (5, 1), (6, 1), (7, 1), (8, 1), (9, 1), (10, 1), (11, 1), (12, 1), (13, 1), (14, 1), (15, 1), (16, 1), (17, 1);

INSERT
INTO
  question_wrong_answers
  (question_id, wrong_answer)
VALUES
  (1, 'Read'), (1, 'Write'), (1, 'Execute'), (2, 'Thomas Jefferson'), (2, 'John F. Kennedy'), (2, 'John Adams'), (3, 'To manage cloud resources'), (3, 'To create interactive chatbots'), (3, 'To virtualize hardware for gaming'),(4, 'Command Prompt'),(4, 'PowerShell'),(4, 'Zsh'),(5, '-r-xr-xr-x'),(5, '-r--r--r--'),(6, 'unzip'),(6, 'ls'),(7, 'Fedora'),(7, 'CentOS'),(8, 'brew'),(9, 'Load a system service'),(9, 'Launch a text editor'),(9, 'List running processes'),(10, 'mkfile'),(10, 'touch'),(10, 'newdir'),(11, 'rwxr-xr-x'),(11, '-rwxrw-r--'),(11, 'drwxrwxrwx'),(12, 'Switch to a different user account'),(12, 'List the contents of a directory'),(12, 'Create a new user'),(13, 'Zsh'),(13, 'Ksh'),(13, 'Tcsh'),(14, 'show'),(14, 'display'),(14, 'print'),(15, 'Copy files and directories'),(15, 'List installed packages'),(15, 'Display disk usage statistics'),(16, 'RPM'),(16, 'YUM'),(16, 'DNF'),(17, 'Print system information'),(17, 'Create a new process'),(17, 'Pause a running process');

INSERT
INTO
    question_correct_answers
    (question_id, correct_answer)
VALUES
    (5, '-rwxrwxrwx'),(6, 'gzip'),(7, 'Arch'),(8, 'YUM'),(8, 'Pacman');

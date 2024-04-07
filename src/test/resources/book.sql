INSERT INTO `book`.`books` (`book_id`, `title`) VALUES (1, 'book1');
INSERT INTO `book`.`books` (`book_id`, `title`) VALUES (2, 'book2');
INSERT INTO `book`.`books` (`book_id`, `title`) VALUES (3, 'book3');
INSERT INTO `book`.`books` (`book_id`, `title`) VALUES (4, 'book4');
INSERT INTO `book`.`authors` (`author_id`, `name`) VALUES (1, 'author1_1');
INSERT INTO `book`.`authors` (`author_id`, `name`) VALUES (2, 'author1_2');
INSERT INTO `book`.`authors` (`author_id`, `name`) VALUES (3, 'author1_3');
INSERT INTO `book`.`authors` (`author_id`, `name`) VALUES (4, 'author2_1');
INSERT INTO `book`.`authors` (`author_id`, `name`) VALUES (5, 'author2_2');
INSERT INTO `book`.`authors` (`author_id`, `name`) VALUES (6, 'author2_3');
INSERT INTO `book`.`authors` (`author_id`, `name`) VALUES (7, 'author3_1');
INSERT INTO `book`.`authors` (`author_id`, `name`) VALUES (8, 'author3_2');
INSERT INTO `book`.`authors` (`author_id`, `name`) VALUES (9, 'author3_3');
INSERT INTO `book`.`books_authors` (`book_id`, `author_id`) VALUES (1, 1);
INSERT INTO `book`.`books_authors` (`book_id`, `author_id`) VALUES (1, 2);
INSERT INTO `book`.`books_authors` (`book_id`, `author_id`) VALUES (1, 3);
INSERT INTO `book`.`books_authors` (`book_id`, `author_id`) VALUES (2, 4);
INSERT INTO `book`.`books_authors` (`book_id`, `author_id`) VALUES (2, 5);
INSERT INTO `book`.`books_authors` (`book_id`, `author_id`) VALUES (2, 6);
INSERT INTO `book`.`books_authors` (`book_id`, `author_id`) VALUES (3, 7);
INSERT INTO `book`.`books_authors` (`book_id`, `author_id`) VALUES (3, 8);
INSERT INTO `book`.`books_authors` (`book_id`, `author_id`) VALUES (3, 9);
INSERT INTO `book`.`books_authors` (`book_id`, `author_id`) VALUES (4, 4);
CREATE TABLE IF NOT EXISTS `books_authors` (
    `book_id` int(10) NOT NULL ,
    `author_id` int(10) NOT NULL,
    PRIMARY KEY (`book_id`, `author_id`),
    FOREIGN KEY (`book_id`) REFERENCES books(`book_id`),
    FOREIGN KEY (`author_id`) REFERENCES authors(`author_id`)
)
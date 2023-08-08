DROP TABLE IF EXISTS articles;

CREATE TABLE articles
(
    id      INTEGER PRIMARY KEY AUTOINCREMENT,
    title   TEXT default NULL,
    wanted_price INTEGER default NULL,
    writer  TEXT default NULL,
    description TEXT default NULL,
    password TEXT default NULL
);

-- private Long id;
--     private String writer;
--     private String title;
--     private int wantedPrice;
--     private String description;
--     private String password;
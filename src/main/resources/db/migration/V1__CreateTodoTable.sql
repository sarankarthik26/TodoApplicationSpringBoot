CREATE TABLE TODO (
    ID BIGINT GENERATED BY DEFAULT AS IDENTITY,
    TODO_NAME VARCHAR(100) NOT NULL,
    IS_DONE BIT NOT NULL,
    PRIMARY KEY (ID)
);
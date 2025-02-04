CREATE TABLE  TODO (
                       ID BIGINT NOT NULL AUTO_INCREMENT,
                       USERNAME NVARCHAR(200),
                       PASSWORD NVARCHAR(32) NOT NULL,
                       CREATED_AT DATETIME NOT NULL,
                       MODIFIED_AT DATETIME NOT NULL,
                       PRIMARY KEY (id)
);
CREATE TABLE  TODO (
                       ID BIGINT NOT NULL AUTO_INCREMENT,
                       USER_ID BIGINT NOT NULL,
                       CONTENT NVARCHAR(200),
                       PASSWORD NVARCHAR(32) NOT NULL,
                       CREATED_AT DATETIME NOT NULL,
                       MODIFIED_AT DATETIME NOT NULL,
                       PRIMARY KEY (id),
                       FOREIGN KEY (USER_ID) REFERENCES USER (id)
);

CREATE TABLE USER (
                      ID BIGINT NOT NULL AUTO_INCREMENT,
                      NAME NVARCHAR(64) NOT NULL,
                      PRIMARY KEY (id)
);
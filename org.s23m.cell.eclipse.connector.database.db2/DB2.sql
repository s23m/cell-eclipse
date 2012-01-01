-- ------------------------------- --
-- Gmodel setup script for IBM DB2 --
-- ------------------------------- --

-- Create database
create database gmodel using codeset UTF-8 territory us collate using identity 

-- Connect to it
connect to gmodel user db2admin using db2admin

-- Create tables
CREATE TABLE document (
    id varchar(36) NOT NULL PRIMARY KEY,
    serialized_instance CLOB(3M) NOT NULL
)

CREATE TABLE index (
    uuid varchar(36) NOT NULL PRIMARY KEY,
    urr varchar(36) NOT NULL,
    name varchar(256) NOT NULL,
    plural_name varchar(256) NOT NULL,
    type varchar(256) NOT NULL,
    identity varchar(36) NOT NULL,
    meta_element_id varchar(36) NOT NULL,
    content_id varchar(36) NOT NULL,
    FOREIGN KEY (content_id)
         REFERENCES document(id)
)

-- Example connection string:  jdbc:db2j:net://localhost:50000/gmodel
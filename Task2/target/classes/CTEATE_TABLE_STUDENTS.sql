create table hibernate_sequence
(
    next_val bigint
) engine=MyISAM;

insert into hibernate_sequence
values (1);

CREATE TABLE students
(

    id BIGINT NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id)

) engine=MyISAM;
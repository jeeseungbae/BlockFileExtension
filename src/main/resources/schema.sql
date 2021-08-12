DROP TABLE IF EXISTS custom_extension,fixed_extension;

create table custom_extension(
    seq bigint(255) not null auto_increment primary key,
    name varchar(20) not null
);

create table fixed_extension(
    seq bigint(255) not null auto_increment primary key,
    bat BOOLEAN not null,
    cmd BOOLEAN not null,
    com BOOLEAN not null,
    cpl BOOLEAN not null,
    exe BOOLEAN not null,
    scr BOOLEAN not null,
    js  BOOLEAN not null
);
create table if not exists users(
    username varchar(50) primary key ,
    password varchar,
    role varchar[]
)
use onboarding;

create table if not exists members
(
    id       bigint              not null auto_increment,
    nickname varchar(255) unique not null,
    password varchar(255)        not null,
    role     enum ('ROLE_ADMIN','ROLE_USER'),
    username varchar(255)        not null,
    primary key (id)
)
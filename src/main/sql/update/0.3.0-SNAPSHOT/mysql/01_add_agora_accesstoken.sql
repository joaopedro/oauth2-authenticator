create table agora_acesstoken (
  id bigint not null auto_increment,
  access_token varchar(50) not null,
  refresh_token varchar(50) not null,
  token_type varchar(50) not null,
  issuer varchar(50) not null,
  username varchar(50) not null,
  clientId varchar(50) not null,
  user_id bigint not null,
  lastAccessUTC datetime not null,
  primary key (id)
);

create table agora_acesstoken_role (
  id bigint not null auto_increment,
  token_id bigint not null,
  role varchar(50) not null,
    primary key (id)
);
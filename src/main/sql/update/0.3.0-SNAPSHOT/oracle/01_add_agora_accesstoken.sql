create table agora_acesstoken (
  id number not null,
  access_token varchar2(50) not null,
  refresh_token varchar2(50) not null,
  token_type varchar2(50) not null,
  issuer varchar2(50) not null,
  username varchar2(50) not null,
  clientId varchar2(50) not null,
  userId number not null,
  lastAccessUTC date not null,
  primary key (id)
);

create table agora_acesstoken_role (
  id number not null,
  token_id number not null,
  role varchar2(50) not null,
    primary key (id)
);
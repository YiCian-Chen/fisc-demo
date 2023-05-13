create table if not exists SYS_USER (
    user_id varchar(30) not null PRIMARY KEY ,
    user_name varchar(150) not null ,
    role_id varchar(36) not null ,
    dep_id varchar(36) not null ,
    branch_id varchar(36) not null ,
    password varchar(256) not null ,
    email varchar(300) not null ,
    last_login_ip varchar(50) ,
    last_login_time timestamp ,
    token varchar(300) not null ,
    enabled Boolean
);

create table if not exists SYS_ROLE (
    role_id varchar(36) not null PRIMARY KEY ,
    role_name varchar(150) not null ,
    functions text not null ,
    role_desc varchar(150) not null
);

create table if not exists SYS_FUNC (
    func_id varchar(36) not null PRIMARY KEY ,
    func_name varchar(150) not null ,
    func_url varchar(150) not null ,
    parent_id varchar(36) not null ,
    order_no int not null,
    permission varchar(30) not null
);

create table if not exists ACCESS_LOG (
    uuid varchar(36) not null PRIMARY KEY ,
    user_id varchar(30) not null ,
    request_url varchar(300) not null ,
    request_method varchar(10) not null ,
    request_time timestamp not null ,
    response_code varchar(3) not null ,
    response_message text not null
);

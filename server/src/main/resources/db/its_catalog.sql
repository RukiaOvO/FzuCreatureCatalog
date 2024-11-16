drop database if exists its_catalog;

create database if not exists its_catalog;
use its_catalog;

drop table if exists UserTable;
create table UserTable
(
  openid varchar(32) PRIMARY KEY comment '微信用户唯一标识',
  nickname varchar(32) not null default '微信用户' comment '用户昵称 默认',-- 微信用户 + MD5(rand() * 10000)
  avatar_url varchar(1000) not null default '' comment '用户头像url',
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment '用户创建时间 trigger',
  last_login_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '用户最后登录时间',
  `status` enum('1','0') not null default '1' comment '用户状态 1:正常 0:禁用'
);

drop table if exists BioCardTable;
create table BioCardTable
(
  card_id int primary key comment '微信用户唯一标识',
  openid varchar(32) comment '创建者id 外键',
  
  pet_name varchar(32) comment '宠物名称',
  species varchar(32) comment '物种类型',
  personality varchar(32) comment '性格特征',
  
  photo_url varchar(1000) not null comment '照片URL',
  like_count int not null default 0 comment '获得点赞数量',
 
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment '名片创建时间 trigger',
  update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '最后更新时间',
  `status` enum('1','0') not null default '1' comment '状态 1:正常 0:下架',
  
  foreign key(openid) references UserTable(openid)
);

drop table if exists LikeTable cascade;
create table LikeTable
(
  like_id int PRIMARY KEY comment '点赞id',
  card_id int comment '名片ID',
  openid varchar(32) comment '微信用户唯一标识',
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间 trigger',

  foreign key(openid) references UserTable(openid),
  foreign key(card_id) references BioCardTable(card_id)
);

drop table if exists StreamTable;
create table StreamTable
(
  stream_id int primary key comment '直播流id',
  card_id int comment '名片ID',
  stream_url  varchar(1000) not null default '' comment '直播流url',
  `status` enum('1','0') not null comment '状态 1:在线 0:离线',
  start_time timestamp not null default CURRENT_TIMESTAMP comment '直播开始时间',
  end_time timestamp not null default CURRENT_TIMESTAMP comment '直播结束时间',
  foreign key(card_id) references BioCardTable(card_id)
);

drop table if exists Admintable;
create table Admintable
(
  admin_id int primary key comment'管理员id',
  username varchar(32) unique comment '用户名',
  password_hash text comment 'trigger',
  role enum('1','0') comment'管理员角色 1:超级管理员 2:普通管理员',
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment'创建时间',
  `status` enum('1','0') not null default '1' comment '状态 1:正常 0:禁用'
);

drop view if exists RankingView;
create view RankingView as 
(
  select card_id,like_count from BioCardTable order by like_count
);
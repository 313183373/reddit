create database if not exists `reddit`;

use reddit;

drop table if exists `comment`;

create table `comment`
(
  `id`                 bigint(20) not null auto_increment,
  `created_by`         varchar(255) default null,
  `creation_date`      datetime     default null,
  `last_modified_by`   varchar(255) default null,
  `last_modified_date` datetime     default null,
  `body`               varchar(255) default null,
  `link_id`            bigint(20)   default null,
  primary key (`id`),
  key `FKoutxw6g1ndh1t6282y0fwvam1` (`link_id`)
) engine = MyISAM
  default charset = utf8mb4
  collate = utf8mb4_0900_ai_ci;

drop table if exists `link`;

create table `link`
(
  `id`                 bigint(20) not null auto_increment,
  `created_by`         varchar(255) default null,
  `creation_date`      datetime     default null,
  `last_modified_by`   varchar(255) default null,
  `last_modified_date` datetime     default null,
  `title`              varchar(255) default null,
  `url`                varchar(255) default null,
  primary key (`id`)
) engine = MyISAM
  default charset = utf8mb4
  collate = utf8mb4_0900_ai_ci;
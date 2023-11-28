-- ############################################
-- clear

DROP TABLE IF EXISTS channel_desciption CASCADE;

DROP TABLE IF EXISTS channel_follower CASCADE;

DROP TABLE IF EXISTS channel_stream CASCADE;

DROP TABLE IF EXISTS channel_subscriber CASCADE;

DROP TABLE IF EXISTS channels CASCADE;

DROP TABLE IF EXISTS stream CASCADE;

DROP TABLE IF EXISTS subscriber CASCADE;

DROP TABLE IF EXISTS follower CASCADE;

-- ############################################
-- create table



create table follower (
  follower_id bigint not null auto_increment,
  user_id bigint not null,

  create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  update_at datetime(6),

  primary key (follower_id)
);

create table channel_follower (
  channel_follower_id bigint not null auto_increment,
  channel_id bigint,
  follower_id bigint,
  follow_at datetime(6),
  unfollow_at datetime(6),

  create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  update_at datetime(6),

  primary key (channel_follower_id)
);

create table subscriber (
  subscriber_id bigint not null auto_increment,
  user_id bigint not null,

  create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  update_at datetime(6),

  primary key (subscriber_id)
);

create table channel_subscriber (
  channel_subscriber_id bigint not null auto_increment,
  channel_id bigint,
  subscribe_id bigint,
  expire_at datetime(6) not null,
  subscribe_at datetime(6) not null,
  subscribe_update_at datetime(6),

  create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  update_at datetime(6),

  primary key (channel_subscriber_id)
);

create table stream (
  stream_id bigint not null auto_increment,
  channel_stream_id bigint not null,
  stream_url varchar(255) not null,
  active_at datetime(6),
  deactive_at datetime(6),

  create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  update_at datetime(6),

  primary key (stream_id)
);

create table channel_stream (
  channel_stream_id bigint not null auto_increment,
  active enum ('OFF','ON'),

  create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  update_at datetime(6),

  primary key (channel_stream_id)
);

create table channel_desciption (
  channel_description_id bigint not null auto_increment,
  titles varchar(255) not null,
  channel_banner_image tinyblob,

  create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  update_at datetime(6),
  
  primary key (channel_description_id)
);

create table channels (
  channel_id bigint not null,
  channel_description_id bigint not null,
  channel_stream_id bigint not null,

  create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP not null,
  update_at datetime(6),

  primary key (channel_id)
);

-- ############################################
-- set FK

alter table channel_follower 
  add constraint channel_follower_channels_channel_id
  foreign key (channel_id) 
  references channels (channel_id);

alter table channel_follower 
  add constraint channel_follower_follower_follower_id
  foreign key (follower_id) 
  references follower (follower_id);

alter table channel_subscriber 
  add constraint channel_subscriber_channels_channel_id
  foreign key (channel_id) 
  references channels (channel_id);

alter table channel_subscriber 
  add constraint channel_subscriber_subscriber_subscriber_id
  foreign key (subscribe_id) 
  references subscriber (subscriber_id);

alter table channels 
  add constraint channels_channel_stream_channel_stream_id
  foreign key (channel_stream_id) 
  references channel_stream (channel_stream_id);

alter table channels 
  add constraint channels_channel_desciption_channel_desciption_id
  foreign key (channel_description_id) 
  references channel_desciption (channel_description_id);

alter table stream 
  add constraint stream_channel_stream_channel_stream_id
  foreign key (channel_stream_id) 
  references channel_stream (channel_stream_id);

-- ############################################
-- set unique

alter table channels 
  add constraint channels_channel_description_id_unique unique (channel_description_id);

alter table channels 
  add constraint channels_channel_stream_id_id_unique unique (channel_stream_id);

alter table follower 
  add constraint follower_user_id_unique unique (user_id);

alter table subscriber 
  add constraint subscriber_user_id_unique unique (user_id);
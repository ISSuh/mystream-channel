INSERT INTO channel_desciption(
  titles)
VALUES
  ('welcome my channel!'),
  ('welcome my channel!'),
  ('welcome my channel!'),
  ('welcome my channel!'),
  ('welcome my channel!');


INSERT INTO follower(
  user_id
)
VALUES
  (1),
  (2),
  (3),
  (4),
  (5);

INSERT INTO channel_stream(
  active
)
VALUES
  ('OFF'),
  ('OFF'),
  ('OFF'),
  ('OFF'),
  ('OFF');


INSERT INTO channels(
  channel_id, username, channel_description_id, channel_stream_id)
VALUES
  (1, "test1", 1, 1),
  (2, "test2", 2, 2),
  (3, "test3", 3, 3),
  (4, "test4", 4, 4),
  (5, "test5", 5, 5);

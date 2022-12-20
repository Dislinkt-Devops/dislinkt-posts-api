insert into person (id, first_name, last_name, gender, phone_number, date_of_birth, bio, privacy) values
    ('2C20B8C0469311EBB3780242AC130002', 'Jovan', 'Svorcan', 0, '1111111', '1998-03-31 23:50:00', 'bio1', 0),
    ('2C20B8C0469311EBB3780242AC130003', 'Maressa', 'Castana', 1, '480-291-1342', '1999-04-14 12:00:00', 'bio3', 1);

insert into followers (follower_id, followed_id) values
    ('2C20B8C0469311EBB3780242AC130002', '2C20B8C0469311EBB3780242AC130003'),
    ('2C20B8C0469311EBB3780242AC130003', '2C20B8C0469311EBB3780242AC130002');
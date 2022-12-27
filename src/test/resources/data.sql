insert into person (id, first_name, last_name, gender, phone_number, date_of_birth, bio, privacy) values
    ('2C20B8C0469311EBB3780242AC130002', 'Jovan', 'Svorcan', 0, '1111111', '1998-03-31 23:50:00', 'bio1', 0),
    ('2C20B8C0469311EBB3780242AC130003', 'Maressa', 'Castana', 1, '480-291-1342', '1999-04-14 12:00:00', 'bio3', 1),
    ('2C20B8C0469311EBB3780242AC130004', 'Maressa', 'Castana', 1, '480-291-1346', '1999-04-14 12:00:00', 'bio3', 0),
    ('2C20B8C0469311EBB3780242AC130005', 'Maressa', 'Castana', 0, '480-291-1347', '1999-04-14 12:00:00', 'bio3', 1);

insert into followers (follower_id, followed_id) values
    -- both users follow one another
    ('2C20B8C0469311EBB3780242AC130002', '2C20B8C0469311EBB3780242AC130003'),
    ('2C20B8C0469311EBB3780242AC130003', '2C20B8C0469311EBB3780242AC130002'),

    -- only one follows another
    ('2C20B8C0469311EBB3780242AC130002', '2C20B8C0469311EBB3780242AC130004'),

    -- both follow, but one is to be blocked by another for testing blocking
    ('2C20B8C0469311EBB3780242AC130002', '2C20B8C0469311EBB3780242AC130005'),
    ('2C20B8C0469311EBB3780242AC130005', '2C20B8C0469311EBB3780242AC130002');

insert into blocking (blocker_id, blocked_id) values
    ('2C20B8C0469311EBB3780242AC130005', '2C20B8C0469311EBB3780242AC130002');

insert into post (id, text, image_url, person_id) values
    (1, 'this is a text', 'img1', '2C20B8C0469311EBB3780242AC130002'),
    (2, 'this is a text also', 'img2', '2C20B8C0469311EBB3780242AC130003'),
    (3, 'this is a text also also', 'img3', '2C20B8C0469311EBB3780242AC130002'),
    (4, 'this is a text also also also', 'img4', '2C20B8C0469311EBB3780242AC130004');

insert into links (post_id, links) values (1, 'link1'), (1, 'link2'), (2, 'link3'), (2, 'link4');
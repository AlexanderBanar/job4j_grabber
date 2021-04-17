create database meetings;
create table meeting(
    id int primary key,
    name text
);
create table users(
    id int primary key,
    name text
);
create table users_meetings(
    id_meeting int references meeting(id),
    id_user int references users(id),
    acceptance boolean
);

insert into meeting(id, name) VALUES (1, 'Compliance');
insert into meeting(id, name) VALUES (2, 'Quality');
insert into meeting(id, name) VALUES (3, 'Action plan');
insert into meeting(id, name) VALUES (4, 'Safety rules');
insert into meeting(id, name) VALUES (5, 'Opening meeting');

insert into users(id, name) VALUES (1, 'Alex');
insert into users(id, name) VALUES (2, 'Anton');
insert into users(id, name) VALUES (3, 'Oleg');
insert into users(id, name) VALUES (4, 'Kate');
insert into users(id, name) VALUES (5, 'Nina');
insert into users(id, name) VALUES (6, 'Olga');

insert into users_meetings(id_meeting, id_user, acceptance) VALUES (1, 1, true);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (1, 2, false);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (1, 3, true);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (1, 4, false);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (1, 5, true);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (1, 6, false);

insert into users_meetings(id_meeting, id_user, acceptance) VALUES (2, 2, true);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (2, 1, true);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (2, 3, true);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (2, 4, true);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (2, 5, true);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (2, 6, true);

insert into users_meetings(id_meeting, id_user, acceptance) VALUES (3, 1, true);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (3, 2, false);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (3, 3, false);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (3, 4, false);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (3, 5, false);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (3, 6, false);

insert into users_meetings(id_meeting, id_user, acceptance) VALUES (4, 1, false);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (4, 2, false);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (4, 3, false);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (4, 4, true);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (4, 5, true);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (4, 6, true);

insert into users_meetings(id_meeting, id_user, acceptance) VALUES (5, 1, false);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (5, 2, false);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (5, 3, false);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (5, 4, false);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (5, 5, false);
insert into users_meetings(id_meeting, id_user, acceptance) VALUES (5, 6, false);

select rsl.name as meeting_name, count(rsl.id_user) as people_qty_acptd from
(select m.name, um.id_user, um.acceptance from meeting as m join users_meetings um on m.id = um.id_meeting) as rsl
where acceptance = true group by meeting_name order by people_qty_acptd;

select k.meeting_name from
(select rsl.name as meeting_name, count(rsl.id_user) as people_qty_rjct from
(select m.name, um.id_user, um.acceptance from meeting as m join users_meetings um on m.id = um.id_meeting) as rsl
where acceptance = false group by meeting_name order by people_qty_rjct) as k
where k.people_qty_rjct = (select count(id) from users);
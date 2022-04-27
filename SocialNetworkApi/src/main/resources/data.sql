-- admin password 'admin'
-- user password 'user'
-- other user password 'user'

-- ADMIN init
INSERT INTO users (id, username, password, first_name, last_name, email, status, created, updated)
VALUES (1, 'admin', '$2a$04$w2cYM5Tuf8LFwW8Mc6c1m.f91tTxeup6oBNM3y06fn0C1sKr4H9J6', 'daniil', 'gomerov',
        'gomerov@gmail.com', 'ACTIVE',CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP());

-- USER init
INSERT INTO users (id, username, password, first_name, last_name, email, status, created, updated)
VALUES (2, 'user', '$2a$04$.QUi82ez7KnCB76Ws/bE.uaG4S8489cTaexdKP2z5eZsmjWg./WhK', 'vasy', 'pupkin', 'pupkin@gmail.com',
        'ACTIVE', CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP());

-- Role init
INSERT INTO roles(id, name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');

-- UserRole init
--admin
INSERT INTO user_roles(user_id, role_id)
VALUES (1, 2);
--user
INSERT INTO user_roles(user_id, role_id)
VALUES (2, 1);

--otherUsers
INSERT INTO users (id, username, password, first_name, last_name, email, status)
VALUES (3, 'user1', '$2a$04$.QUi82ez7KnCB76Ws/bE.uaG4S8489cTaexdKP2z5eZsmjWg./WhK', 'vasy', 'pupkin',
        'pupkin@gmail.com', 'ACTIVE');

INSERT INTO users (id, username, password, first_name, last_name, email, status)
VALUES (4, 'user2', '$2a$04$.QUi82ez7KnCB76Ws/bE.uaG4S8489cTaexdKP2z5eZsmjWg./WhK', 'Masha', 'masha', 'masha@gmail.com',
        'ACTIVE');

INSERT INTO users (id, username, password, first_name, last_name, email, status)
VALUES (5, 'user3', '$2a$04$.QUi82ez7KnCB76Ws/bE.uaG4S8489cTaexdKP2z5eZsmjWg./WhK', 'Pety', 'pety', 'pety@gmail.com',
        'ACTIVE');

INSERT INTO users (id, username, password, first_name, last_name, email, status)
VALUES (6, 'user4', '$2a$04$.QUi82ez7KnCB76Ws/bE.uaG4S8489cTaexdKP2z5eZsmjWg./WhK', 'Dasha', 'dasha', 'dasha@gmail.com',
        'ACTIVE');

--otherUsersRole
INSERT INTO user_roles(user_id, role_id)
VALUES (3, 1);
INSERT INTO user_roles(user_id, role_id)
VALUES (4, 1);
INSERT INTO user_roles(user_id, role_id)
VALUES (5, 1);
INSERT INTO user_roles(user_id, role_id)
VALUES (6, 1);

--UsersFriends
INSERT INTO friends (id, user_id, friend_id, friend_status, status)
VALUES (1, 2, 6, 'FRIEND', 'ACTIVE');

INSERT INTO friends (id, user_id, friend_id, friend_status, status)
VALUES (2, 2, 5, 'FRIEND', 'ACTIVE');

INSERT INTO friends (id, user_id, friend_id, friend_status, status)
VALUES (3, 4, 2, 'FRIEND', 'ACTIVE');

INSERT INTO friends (id, user_id, friend_id, friend_status, status)
VALUES (4, 4, 3, 'FRIEND', 'ACTIVE');

INSERT INTO friends (id, user_id, friend_id, friend_status, status)
VALUES (5, 4, 5, 'FRIEND', 'ACTIVE');

--Messages
INSERT INTO users_messages (id, recipient_id, sender_id, value_text, messages_status, created, updated)
VALUES (1, 2, 3, 'Hello', 'CREATED',CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP() );

INSERT INTO users_messages (id, recipient_id, sender_id, value_text, messages_status, created, updated)
VALUES (2, 2, 3, 'Im vasy', 'CREATED',CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP());

INSERT INTO users_messages (id, recipient_id, sender_id, value_text, messages_status, created, updated)
VALUES (3, 3, 2, 'Hello vasy', 'CREATED',CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP());


INSERT INTO users_messages (id, recipient_id, sender_id, value_text, messages_status, created, updated)
VALUES (4, 2, 3, 'Hello', 'CREATED',CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP());

INSERT INTO users_messages (id, recipient_id, sender_id, value_text, messages_status, created, updated)
VALUES (5, 2, 3, 'Im vasy', 'CREATED',CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP());

INSERT INTO users_messages (id, recipient_id, sender_id, value_text, messages_status, created, updated)
VALUES (6, 3, 2, 'Hello vasy', 'CREATED',CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP());

--News
INSERT INTO user_news (id, user_id, header, description, news_status)
VALUES (1, 2, 'Title', 'Hello', 'CREATED');

INSERT INTO user_news (id, user_id, header, description, news_status)
VALUES (2, 2, 'Title2', 'Hello', 'CREATED');

INSERT INTO user_news (id, user_id, header, description, news_status)
VALUES (3, 2, 'Title3', 'Hello', 'CREATED');

INSERT INTO user_news (id, user_id, header, description, news_status)
VALUES (4, 3, 'Title1', 'Hello', 'CREATED');

INSERT INTO user_news (id, user_id, header, description, news_status)
VALUES (5, 3, 'Title2', 'Hello', 'CREATED');

INSERT INTO user_news (id, user_id, header, description, news_status)
VALUES (6, 3, 'Title3', 'Hello', 'CREATED');




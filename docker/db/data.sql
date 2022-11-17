use fruits;

delete from user;
insert into user(username, pwdHash, email, phone) values
                                                      ('lzj', 'dkfjkdjfkdjf', 'lzj@test.com', '1333333333'),
                                                      ('admin', 'dkfjkdjfkdjf', 'admin@test.com', '1333333334'),
                                                      ('user', 'dkfjkdjfkdjf', 'user@test.com', '1333333335');
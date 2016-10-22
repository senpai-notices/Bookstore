# Set up init data when the application is deployed

# Set up role
insert into BookstoreRole(Id, RoleName) values (1, 'ADMIN')
insert into BookstoreRole(Id, RoleName) values (2, 'USER')


# Generate test data

# Generate user
# default password: qwerty
insert into BookstoreUser(USERNAME, PASSWORD, ROLE_ID, FULLNAME) values ('admin', '65e84be33532fb784c48129675f9eff3a682b27168c0ea744b2cf58ee02337c5', 1, 'Administrator')
insert into BookstoreUser(USERNAME, PASSWORD, ROLE_ID, FULLNAME) values ('sondang2412', '65e84be33532fb784c48129675f9eff3a682b27168c0ea744b2cf58ee02337c5', 2, 'Cuu Son Dang')
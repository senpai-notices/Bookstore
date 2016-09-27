import { InMemoryDbService } from 'angular2-in-memory-web-api';
import { UserModel } from './models/user.model';

export const NORMAL_USERS: UserModel[] = [
    {
        id: 1,
        username: 'username1',
        fullname: 'normal user1',
        password: '1234561',
        role: 'user',
    },
    {
        id: 2,
        username: 'username2',
        fullname: 'normal user2',
        password: '1234562',
        role: 'user',
    },
    {
        id: 3,
        username: 'username3',
        fullname: 'normal user3',
        password: '1234563',
        role: 'user',
    },
    {
        id: 4,
        username: 'username4',
        fullname: 'normal user4',
        password: '1234564',
        role: 'user',
    },
]

export const ADMIN_USERS: UserModel[] = [
    {
        id: 5,
        username: 'admin1',
        fullname: 'admin user1',
        password: '1234561',
        role: 'admin',
    },
    {
        id: 6,
        username: 'admin2',
        fullname: 'admin user2',
        password: '1234562',
        role: 'admin',
    },
]


export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    let users = NORMAL_USERS.concat(ADMIN_USERS);

    return {users};
  }
}
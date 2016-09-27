import { Injectable } from '@angular/core';
import { Headers, Http, Response } from '@angular/http';

import { UserModel } from '../models/user.model';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class UserService {

    private loginURL = 'http://localhost:8080/BookstoreService/api/user/login';
    private loggedUser: UserModel;

    constructor(private http: Http) { 
        this.loggedUser = JSON.parse(localStorage.getItem("loggedUser"));
    }

    public login(username: string, password: string) {

        let headers = new Headers();
        headers.append('Content-Type', 'application/x-www-form-urlencoded');

        let body = 'username=' + username + '&password=' + password;
        
        return this.http.post(this.loginURL, body,{ headers: headers }).toPromise()
        .then(response => {
            this.loggedUser = response.json() as UserModel;
        });
        
        // return this.http.post(this.loginURL, body).toPromise()
        // .then(response => {
        //     console.log(response);
        //     alert(response);
        //     this.loggedUser = response.json().data as UserModel;
        //     return (this.loggedUser != null);
        // });
    }

    public logout() {
        localStorage.removeItem('loggedUser');
        this.loggedUser = null;
    }

    public getLoggedUser(){
        return this.loggedUser;
    }
}
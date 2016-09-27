import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Http, Headers } from '@angular/http';

import { UserModel } from '../../models/user.model';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'header',
  templateUrl: '/app/components/header/header.component.html',
})

export class HeaderComponent {
  private searchKey: string;
  private username: string;
  private password: string;

  constructor(private userSerivce: UserService, 
              private router : Router,
              private http: Http){ }

  login() : void {
    this.userSerivce.login(this.username, this.password).then( () => {
      this.router.navigate(['/dashboard']);
    }).catch(ex => {
      alert("Cannot login");
    })
  }

  logout() : void {
    this.userSerivce.logout();
    this.password = null;
  }

  loggedUser() {
    return this.userSerivce.getLoggedUser();
  }
}
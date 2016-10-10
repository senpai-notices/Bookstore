import { Injectable } from '@angular/core';
import { Router, CanActivate, NavigationExtras } from '@angular/router';

import { UserService } from './services/user.service';

@Injectable()
export class RequireLoginGuard implements CanActivate {

  constructor(private UserService: UserService,
              private router: Router) {}

  canActivate() {
    if (this.UserService.getLoggedUser() == null){
      let navigationExtras: NavigationExtras = {
        queryParams: { 'errorMessage': "Please login to continue" }
      };
      this.router.navigate(['/login'], navigationExtras);
      return false;
    } else {
      return true;
    }
  }
}
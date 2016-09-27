import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserService } from '../../services/user.service';

@Component({
  selector: 'my-dashboard',
  templateUrl: '/app/components/dashboard/dashboard.component.html',
})


export class DashboardComponent {

  constructor(private userUservice: UserService, private router: Router){ 
    if (this.userUservice.getLoggedUser() == null){
      this.router.navigate(['/login']);
    }
  }
}
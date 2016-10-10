import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

@Component({
  selector: 'login-page',
  templateUrl: '/app/components/login/login.component.html'
})

export class LoginComponent {
    private errorMessage: string;

    constructor(private route: ActivatedRoute){
      this.route.queryParams.map(params => params['errorMessage'] || null).subscribe(errorMessage => this.errorMessage = errorMessage);
    }
}
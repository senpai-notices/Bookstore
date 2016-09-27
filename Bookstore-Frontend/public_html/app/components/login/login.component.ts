import { Component, Optional } from '@angular/core';

@Component({
  selector: 'login',
  templateUrl: '/app/components/login/login.component.html'
})

export class LoginComponent {

    constructor(@Optional() private errorMessage?: String){
    }
}
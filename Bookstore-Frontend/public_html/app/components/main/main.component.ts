import { Component, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'my-app',
  templateUrl: '/app/components/main/main.component.html',
  encapsulation: ViewEncapsulation.None,
})

export class MainComponent {
  title = 'Tour of Heroes';
}
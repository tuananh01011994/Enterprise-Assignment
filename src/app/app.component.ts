import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'angular-frontend';
  isAuthenticated = false;
  show = true;
  
  async logout(): Promise<void> {
    // todo
  }
}

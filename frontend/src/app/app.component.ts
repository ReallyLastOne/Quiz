import { Component } from '@angular/core';
import { UserAuthenticationService } from './services/user-authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';
  constructor(private readonly userAuthenticationService: UserAuthenticationService) { }

  get userAuthService() {
    return this.userAuthenticationService;
  }
}

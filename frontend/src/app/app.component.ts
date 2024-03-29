import { Component } from '@angular/core';
import { UserAuthenticationService } from './services/user-authentication.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  constructor(
    private readonly _userAuthenticationService: UserAuthenticationService
  ) {}

  get userAuthService(): UserAuthenticationService {
    return this._userAuthenticationService;
  }
}

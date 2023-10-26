import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserAuthenticationService } from '../services/user-authentication.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  isActive = false;

  get userAuthService() {
    return this._userAuthenticationService;
  }

  constructor(
    private readonly _userAuthenticationService: UserAuthenticationService,
    private readonly _router: Router
  ) {}

  ngOnInit(): void {}

  homeClick(): void {
    this._router.navigate([`/home`]);
  }

  phraseClick(): void {
    this._router.navigate([`/phrase`]);
  }

  expandMenuClick(): void {
    this.isActive = !this.isActive;
  }

  logoutClick(): void {
    this._userAuthenticationService.logout();
  }
}

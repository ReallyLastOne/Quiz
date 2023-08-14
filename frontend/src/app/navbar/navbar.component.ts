import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserAuthenticationService } from '../services/user-authentication.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  isActive = false;

  constructor(
    private readonly _userAuthenticationService: UserAuthenticationService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  homeClick(): void {
    this.router.navigate([`/home`]);
  }

  phraseClick(): void {
    this.router.navigate([`/phrase`]);
  }

  get userAuthService() {
    return this._userAuthenticationService;
  }

  expandMenuClick(): void {
    this.isActive = !this.isActive;
  }

  logoutClick(): void {
    this._userAuthenticationService.logout();
  }
}

import { Injectable, inject } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { UserAuthenticationService } from '../services/user-authentication.service';

@Injectable({
  providedIn: 'root',
})
export class LoginGuard implements CanActivate {
  constructor(
    private readonly _userAuthService: UserAuthenticationService,
    private readonly _router: Router
  ) {}
  canActivate(): Observable<boolean | UrlTree> | boolean | UrlTree {
    if (this._userAuthService.isLoggedIn()) {
      return this._router.createUrlTree(['/']);
    } else {
      return true;
    }
  }
}

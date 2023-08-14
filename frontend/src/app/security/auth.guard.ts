import { Injectable, inject } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { UserAuthenticationService } from '../services/user-authentication.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard {
  constructor(
    private readonly _userAuthService: UserAuthenticationService,
    private readonly _router: Router
  ) {}
  canActivate(): Observable<boolean | UrlTree> | boolean | UrlTree {
    if (this._userAuthService.isLoggedIn()) {
      return true;
    } else {
      return this._router.createUrlTree(['/login']);
    }
  }
}

export const authGuard: CanActivateFn = (
  route: ActivatedRouteSnapshot
): boolean | UrlTree | Observable<boolean | UrlTree> => {
  return inject(AuthGuard).canActivate();
};

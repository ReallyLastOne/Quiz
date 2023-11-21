import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { RegistrationRequest } from '../model/registration-request.model';
import { environment } from '../../environments/environment';
import { LoginRequest } from '../model/login-request.model';
import { tap, shareReplay } from 'rxjs/operators';
import * as moment from 'moment';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class UserAuthenticationService {
  constructor(
    private readonly _http: HttpClient,
    private readonly _router: Router,
    private readonly _cookieService: CookieService
  ) {}

  registration(registrationRequest: RegistrationRequest) {
    return this._http.post(
      environment.apiUrl + `/auth/register`,
      JSON.parse(JSON.stringify(registrationRequest))
    );
  }
  signIn(loginRequest: LoginRequest) {
    return this._http
      .post(
        environment.apiUrl + '/auth/authenticate',
        JSON.parse(JSON.stringify(loginRequest))
      )
      .pipe(
        tap((res) => {
          this.setSession(res);
          this._router.navigate(['/home']);
        }),
        shareReplay()
      );
  }

  refreshToken() {
    return this._http.post(environment.apiUrl + '/auth/refresh', null);
  }

  private setSession(authResult): void {
    const expiresAt = moment().add(authResult.expiresIn, 'second');
    this.saveAccessToken(authResult.accessToken);
    this.saveExpirationTokenTime(JSON.stringify(expiresAt.valueOf()));
    this.saveRefreshToken(authResult.refreshToken);
  }

  public saveRefreshToken(token: string): void {
    window.sessionStorage.removeItem('auth-refreshtoken');
    window.sessionStorage.setItem('auth-refreshtoken', token);
  }

  public saveAccessToken(token: string): void {
    localStorage.removeItem('accessToken');
    localStorage.setItem('accessToken', token);
  }

  public saveExpirationTokenTime(token: string): void {
    localStorage.removeItem('expires_at');
    localStorage.setItem('expires_at', token);
  }

  public getRefreshToken(): string {
    return localStorage.getItem('auth-refreshtoken');
  }

  public getAccessToken(): string | null {
    return localStorage.getItem('accessToken');
  }

  logout(): void {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('expires_at');
    this._router.navigate(['/login']);
  }

  public isLoggedIn(): boolean {
    return this.getAccessToken() != null;
  }

  isLoggedOut(): boolean {
    return !this.isLoggedIn();
  }

  getCsrfToken(): string {
    return this._cookieService.get('XSRF-TOKEN');
  }
}

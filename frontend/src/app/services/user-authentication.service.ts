import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { RegistrationRequest } from '../model/registration-request.model';
import { environment } from '../../environments/environment';
import { LoginRequest } from '../model/login-request.model';
import { tap, shareReplay, catchError } from 'rxjs/operators';
import * as moment from 'moment';
import { of } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class UserAuthenticationService {
  constructor(
    private http: HttpClient,
    private router: Router,
    private readonly _cookieService: CookieService
  ) {}

  registration(registrationRequest: RegistrationRequest) {
    return this.http.post(
      environment.apiUrl + `/auth/register`,
      JSON.parse(JSON.stringify(registrationRequest))
    );
  }
  signIn(loginRequest: LoginRequest) {
    return this.http
      .post(
        environment.apiUrl + '/auth/authenticate',
        JSON.parse(JSON.stringify(loginRequest))
      )
      .pipe(
        tap((res) => this.setSession(res)),
        tap((res) => {
          console.log(res);
          this.router.navigate(['/home']);
        }),
        catchError(() => {
          return of([]);
        }),
        shareReplay()
      );
  }

  refreshToken() {
    return this.http.post(environment.apiUrl + '/auth/refresh', null);
  }

  private setSession(authResult) {
    const expiresAt = moment().add(authResult.expiresIn, 'second');
    console.log(authResult);
    this.saveAccessToken(authResult.accessToken);
    this.saveExpirationTokenTime(JSON.stringify(expiresAt.valueOf()));
    this.saveRefreshToken(authResult.refreshToken);
  }

  public saveRefreshToken(token: string) {
    window.sessionStorage.removeItem('auth-refreshtoken');
    window.sessionStorage.setItem('auth-refreshtoken', token);
  }

  public saveAccessToken(token: string) {
    localStorage.removeItem('accessToken');
    localStorage.setItem('accessToken', token);
  }

  public saveExpirationTokenTime(token: string) {
    localStorage.removeItem('expires_at');
    localStorage.setItem('expires_at', token);
  }

  public getRefreshToken(): string {
    return localStorage.getItem('auth-refreshtoken');
  }

  public getAccessToken(): string {
    return localStorage.getItem('accessToken');
  }

  logout() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('expires_at');
    this.router.navigate(['/login']);
  }

  public isLoggedIn() {
   return this.getAccessToken() != null;
    // return moment().isBefore(this.getExpiration());
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

  getExpiration() {
    const expiration = localStorage.getItem('expires_at');
    const expiresAt = JSON.parse(expiration);
    return moment(expiresAt);
  }

  getCsrfToken() {
    return this._cookieService.get('XSRF-TOKEN');
  }
}

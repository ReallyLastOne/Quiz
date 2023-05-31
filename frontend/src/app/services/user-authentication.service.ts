import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegistrationRequest } from '../model/registration-request.model';
import { environment } from '../../environments/environment';
import { LoginRequest } from '../model/login-request.model';
import { tap, shareReplay } from 'rxjs/operators';
import * as moment from 'moment';

@Injectable({
  providedIn: 'root',
})
export class UserAuthenticationService {
  constructor(private http: HttpClient) {}

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
        shareReplay()
      );
  }

  private setSession(authResult) {
    const expiresAt = moment().add(authResult.expiresIn, 'second');
    console.log(authResult);
    localStorage.setItem('id_token', authResult.idToken);
    localStorage.setItem('expires_at', JSON.stringify(expiresAt.valueOf()));
  }

  logout() {
    localStorage.removeItem('id_token');
    localStorage.removeItem('expires_at');
  }

  public isLoggedIn() {
    return moment().isBefore(this.getExpiration());
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

  getExpiration() {
    const expiration = localStorage.getItem('expires_at');
    const expiresAt = JSON.parse(expiration);
    return moment(expiresAt);
  }
}

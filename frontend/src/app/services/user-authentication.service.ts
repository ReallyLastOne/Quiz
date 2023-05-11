import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { RegistrationRequest } from '../model/registration-request.model';
import { environment } from '../../environments/environment';
import { LoginRequest } from '../model/login-request.model';

@Injectable({
  providedIn: 'root',
})
export class UserAuthenticationService {
  constructor(private http: HttpClient) {}

  registration(registrationRequest: RegistrationRequest) {
    return this.http.post(
      environment.apiUrl + `/auth/register`,
      JSON.stringify(registrationRequest)
    );
  }
  signIn(loginRequest: LoginRequest) {
    return this.http.post(
      environment.apiUrl + '/auth/authenticate',
      loginRequest
    );
  }
}

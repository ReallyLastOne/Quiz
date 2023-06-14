import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { tap, catchError } from 'rxjs/operators';
import { LoginRequest } from '../model/login-request.model';
import { UserAuthenticationService } from '../services/user-authentication.service';
import { of } from 'rxjs';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  private _logForm: FormGroup;
  private _submitted = false;
  login = true;

  constructor(private _userAuthenticationService: UserAuthenticationService) {}

  get submitted(): boolean {
    return this._submitted;
  }

  set submitted(value: boolean) {
    this._submitted = value;
  }

  get logForm(): FormGroup {
    return this._logForm;
  }

  ngOnInit(): void {
    this._logForm = new FormGroup({
      nickname: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
      ]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
      ]),
    });
  }

  registerMeClick(register: boolean) {
    this.login = register;
  }

  onLogin() {
    this.submitted = true;
    if (this._logForm.invalid) {
      return;
    } else {
      let loginRequest: LoginRequest;
      loginRequest = new LoginRequest(
        this._logForm.get('nickname').value,
        this._logForm.get('password').value
      );
      this._userAuthenticationService
        .signIn(loginRequest)
        .pipe(
          tap((response) => {
            console.log(response);
          }),
          catchError(() => {
            return of([]);
          })
        )
        .subscribe();
    }
  }

  get loginForm() {
    return this._logForm.controls;
  }
}

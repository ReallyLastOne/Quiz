import { Component, DestroyRef, OnInit, inject } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { catchError } from 'rxjs/operators';
import { LoginRequest } from '../model/login-request.model';
import { UserAuthenticationService } from '../services/user-authentication.service';
import { of } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  private _destroyRef = inject(DestroyRef);
  private _logForm: FormGroup;
  private _submitted = false;
  private _hidePassword = true;
  private _error = '';

  login = true;

  get loginForm() {
    return this._logForm.controls;
  }

  get submitted(): boolean {
    return this._submitted;
  }

  set submitted(value: boolean) {
    this._submitted = value;
  }

  get logForm(): FormGroup {
    return this._logForm;
  }

  get hidePassword(): boolean {
    return this._hidePassword;
  }

  get error(): string {
    return this._error;
  }

  constructor(
    private readonly _userAuthenticationService: UserAuthenticationService
  ) {}

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

  private setError(error: string): void {
    this._error = error;
  }

  private clearError(): void {
    this._error = '';
  }

  registerMeClick(register: boolean): void {
    this.login = register;
    this.clearError();
  }

  onLogin(): void {
    this.submitted = true;
    if (this._logForm.invalid) {
      this.setError('Wypełnij login i hasło');
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
          catchError((error) => {
            if (error.status == 422) {
              this.setError('Niepoprawny login lub hasło');
            }
            return of([]);
          }),
          takeUntilDestroyed(this._destroyRef)
        )
        .subscribe();
    }
  }

  changePasswordVisibility(): void {
    this._hidePassword = !this._hidePassword;
  }
}

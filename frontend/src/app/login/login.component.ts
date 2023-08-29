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

  registerMeClick(register: boolean): void {
    this.login = register;
  }

  onLogin(): void {
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
          catchError(() => {
            return of([]);
          }),
          takeUntilDestroyed(this._destroyRef)
        )
        .subscribe();
    }
  }
}

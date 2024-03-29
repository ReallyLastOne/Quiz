import {
  Component,
  OnInit,
  Output,
  EventEmitter,
  DestroyRef,
  inject,
} from '@angular/core';
import { UserAuthenticationService } from '../services/user-authentication.service';
import { RegistrationRequest } from '../model/registration-request.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  private _destroyRef = inject(DestroyRef);
  private _regForm: FormGroup;
  private _submitted = false;
  private _hidePassword = true;

  @Output() backToLogin = new EventEmitter<boolean>();

  get regForm(): FormGroup {
    return this._regForm;
  }

  get submitted(): boolean {
    return this._submitted;
  }

  set submitted(value: boolean) {
    this._submitted = value;
  }

  get registerForm() {
    return this.regForm.controls;
  }
  get hidePassword(): boolean {
    return this._hidePassword;
  }

  constructor(
    private readonly _userAuthenticationService: UserAuthenticationService
  ) {}

  ngOnInit(): void {
    this._regForm = new FormGroup({
      nickname: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
      ]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
      ]),
    });
  }

  backToLoginClick(): void {
    this.backToLogin.emit(false);
  }

  onRegister(): void {
    this.submitted = true;
    if (this._regForm.invalid) {
      return;
    } else {
      let registrationRequest: RegistrationRequest;
      registrationRequest = new RegistrationRequest(
        this._regForm.get('nickname').value,
        this._regForm.get('email').value,
        this._regForm.get('password').value
      );
      this._userAuthenticationService
        .registration(registrationRequest)
        .pipe(
          catchError((error) => {
            console.error(error);
            return of([]);
          }),
          takeUntilDestroyed(this._destroyRef)
        )
        .subscribe({
          next: () => this.backToLogin.emit(false),
        });
    }
  }

  changePasswordVisibility() {
    this._hidePassword = !this._hidePassword;
  }
}

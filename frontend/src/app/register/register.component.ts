import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { UserAuthenticationService } from '../services/user-authentication.service';
import { RegistrationRequest } from '../model/registration-request.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { catchError, tap } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  private _regForm: FormGroup;
  private _submitted = false;
  constructor(private _userAuthenticationService: UserAuthenticationService) {}

  get regForm(): FormGroup {
    return this._regForm;
  }

  get submitted(): boolean {
    return this._submitted;
  }

  set submitted(value: boolean) {
    this._submitted = value;
  }

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

  @Output() backToLogin = new EventEmitter<boolean>();

  backToLoginClick() {
    this.backToLogin.emit(false);
  }

  onRegister() {
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
          tap((response) => {
            console.log(response);
          }),
          catchError((error) => {
            console.log(error);
            return of([]);
          })
        )
        .subscribe();
    }
  }

  get registerForm() {
    return this.regForm.controls;
  }
}

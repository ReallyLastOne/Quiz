import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { UserAuthenticationService } from '../services/user-authentication.service';
import { User } from '../model/User.model';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { error } from 'console';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
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
      let user: User;
      user = new User(
        this._regForm.get('nickname').value,
        this._regForm.get('email').value,
        this._regForm.get('password').value
      );
      this._userAuthenticationService.registration(user).subscribe(
        (response) => {
          console.log(response);
        },
        (error) => {
          console.log(error);
        }
      );
    }
  }

  get registerForm() {
    return this.regForm.controls;
  }
}

import { Component, OnInit, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}

  @Output() backToLogin = new EventEmitter<boolean>();

  backToLoginClick() {
    this.backToLogin.emit(false);
  }
}

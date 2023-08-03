import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login.component';
import { RegisterModule } from '../register/register.module';

@NgModule({
  imports: [CommonModule, ReactiveFormsModule, RegisterModule],
  declarations: [LoginComponent],
  exports: [LoginComponent],
  providers: [],
})
export class LoginModule {}

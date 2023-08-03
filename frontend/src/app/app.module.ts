import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ExerciseComponent } from './exercise/exercise.component';
import { ScoreService } from './score/score.service';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { CsrfTokenInterceptor } from './interceptors/csrf-token.interceptor';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarModule } from './navbar/navbar.module';
import { LoginModule } from './login/login.module';

@NgModule({
  declarations: [AppComponent, ExerciseComponent],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    NavbarModule,
    LoginModule,
  ],
  providers: [
    ScoreService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: CsrfTokenInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}

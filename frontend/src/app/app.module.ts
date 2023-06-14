import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ExerciseComponent } from './exercise/exercise.component';
import { ErrorComponent } from './error/error.component';
import { ScoreComponent } from './score/score.component';
import { ScoreService } from './score/score.service';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home/home.component';
import { RegisterComponent } from './register/register.component';
import { NavbarComponent } from './navbar/navbar.component';
import { AuthInterceptor } from './interceptors/auth.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    ExerciseComponent,
    HomeComponent,
    ErrorComponent,
    ScoreComponent,
    LoginComponent,
    RegisterComponent,
    NavbarComponent,
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
  ],
  providers: [
    ScoreService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}

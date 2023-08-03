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
import { CsrfTokenInterceptor } from './interceptors/csrf-token.interceptor';
import { PhraseComponent } from './phrase/phrase.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

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
    PhraseComponent,
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    MatPaginatorModule,
    MatTableModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatIconModule
  ],
  providers: [
    ScoreService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: CsrfTokenInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }

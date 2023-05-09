import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { ExerciseComponent } from './exercise/exercise.component';
import { ErrorComponent } from './error/error.component';
import { ScoreComponent } from './score/score.component';
import { ScoreService } from './score/score.service';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home/home.component';
import { RegisterComponent } from './register/register.component';

@NgModule({
  declarations: [
    AppComponent,
    ExerciseComponent,
    HomeComponent,
    ErrorComponent,
    ScoreComponent,
    LoginComponent,
    RegisterComponent,
  ],
  imports: [HttpClientModule, BrowserModule, AppRoutingModule],
  providers: [ScoreService],
  bootstrap: [AppComponent],
})
export class AppModule {}

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { ExerciseComponent } from './exercise/exercise.component';
import { ErrorComponent } from './error/error.component';
import { ScoreComponent } from './score/score.component';
import { ScoreService } from './score/score.service';

@NgModule({
  declarations: [
    AppComponent,
    ExerciseComponent,
    ErrorComponent,
    ScoreComponent,
  ],
  imports: [HttpClientModule, BrowserModule, AppRoutingModule],
  providers: [ScoreService],
  bootstrap: [AppComponent],
})
export class AppModule {}

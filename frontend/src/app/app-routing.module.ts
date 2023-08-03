import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router'; // CLI imports router
import { HomeComponent } from './home/home/home.component';
import { ExerciseComponent } from './exercise/exercise.component';
import { ErrorComponent } from './error/error.component';
import { ScoreComponent } from './score/score.component';
import { LoginComponent } from './login/login.component';
import { PhraseComponent } from './phrase/phrase.component';

const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'exercise',
    component: ExerciseComponent,
  },
  {
    path: 'score',
    component: ScoreComponent,
  },
  {
    path: 'error',
    component: ErrorComponent,
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'phrase',
    component: PhraseComponent,
  },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', redirectTo: 'error' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

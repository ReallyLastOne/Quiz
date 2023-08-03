import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ExerciseComponent } from './exercise/exercise.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  {
    path: 'home',
    loadChildren: () => import('./home/home.module').then((m) => m.HomeModule),
  },
  {
    path: 'exercise',
    component: ExerciseComponent,
  },
  {
    path: 'score',
    loadChildren: () =>
      import('./score/score.module').then((m) => m.ScoreModule),
  },
  {
    path: 'error',
    loadChildren: () =>
      import('./error/error.module').then((m) => m.ErrorModule),
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'phrase',
    loadChildren: () =>
      import('./phrase/phrase.module').then((m) => m.PhraseModule),
  },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', redirectTo: 'error' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

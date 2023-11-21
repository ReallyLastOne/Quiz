import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { LoginGuard } from './security/login.guard';
import { AuthGuard } from './security/auth.guard';

const routes: Routes = [
  {
    path: 'home',
    loadChildren: () => import('./home/home.module').then((m) => m.HomeModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'exercise',
    loadChildren: () =>
      import('./exercise/exercise.module').then((m) => m.ExerciseModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'score',
    loadChildren: () =>
      import('./score/score.module').then((m) => m.ScoreModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'error',
    loadChildren: () =>
      import('./error/error.module').then((m) => m.ErrorModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [LoginGuard],
  },
  {
    path: 'phrase',
    loadChildren: () =>
      import('./phrase/phrase.module').then((m) => m.PhraseModule),
    canActivate: [AuthGuard],
  },
  {
    path: 'translation',
    loadChildren: () =>
      import('./translation/translation.module').then(
        (m) => m.TranslationModule
      ),
    canActivate: [AuthGuard],
  },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', redirectTo: 'home' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

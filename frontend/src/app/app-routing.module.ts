import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router'; // CLI imports router
import { HomeComponent } from './home/home/home.component';
import { ExerciseComponent } from './exercise/exercise.component';
const routes: Routes = [
  {
    path: 'home', component: HomeComponent,
  },
  {
    path: 'exercise', component: ExerciseComponent,
  },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', redirectTo: 'home' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

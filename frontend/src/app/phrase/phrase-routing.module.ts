import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PhraseComponent } from './phrase.component';

const routes: Routes = [
  {
    path: '',
    component: PhraseComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PhraseRoutingModule {}

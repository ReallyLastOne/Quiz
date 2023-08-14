import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ScoreComponent } from './score.component';
import { ScoreRoutingModule } from './score-routing.module';

@NgModule({
  imports: [CommonModule, ScoreRoutingModule],
  declarations: [ScoreComponent],
  exports: [ScoreComponent],
  providers: [],
})
export class ScoreModule {}

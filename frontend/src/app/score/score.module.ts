import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ScoreComponent } from './score.component';
import { ScoreRoutingModule } from './score-routing.module';
import { MatButtonModule } from '@angular/material/button';

@NgModule({
  imports: [CommonModule, ScoreRoutingModule, MatButtonModule],
  declarations: [ScoreComponent],
  exports: [ScoreComponent],
  providers: [],
})
export class ScoreModule {}

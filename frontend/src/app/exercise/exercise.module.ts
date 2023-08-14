import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExerciseComponent } from './exercise.component';
import { ExerciseRoutingModule } from './exercise-routing.module';

@NgModule({
  imports: [CommonModule, ExerciseRoutingModule],
  declarations: [ExerciseComponent],
  exports: [ExerciseComponent],
  providers: [],
})
export class ExerciseModule {}

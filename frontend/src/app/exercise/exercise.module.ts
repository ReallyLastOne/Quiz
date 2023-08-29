import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { ExerciseComponent } from './exercise.component';
import { ExerciseRoutingModule } from './exercise-routing.module';
import { MatButtonModule } from '@angular/material/button';
import { MatRadioModule } from '@angular/material/radio';
import { FormsModule } from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    ExerciseRoutingModule,
    MatCardModule,
    MatButtonModule,
    MatRadioModule,
    FormsModule,
  ],
  declarations: [ExerciseComponent],
  exports: [ExerciseComponent],
  providers: [],
})
export class ExerciseModule {}

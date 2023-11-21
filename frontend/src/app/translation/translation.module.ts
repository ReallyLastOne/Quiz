import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { TranslationRoutingModule } from './translation-routing.module';
import { TranslationComponent } from './translation.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';

@NgModule({
  imports: [
    CommonModule,
    TranslationRoutingModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
  ],
  declarations: [TranslationComponent],
  exports: [TranslationComponent],
  providers: [],
})
export class TranslationModule {}

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PhraseComponent } from './phrase.component';
import { PhraseRoutingModule } from './phrase-routing.module';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@NgModule({
  imports: [
    CommonModule,
    PhraseRoutingModule,
    MatPaginatorModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
  ],
  declarations: [PhraseComponent],
  exports: [PhraseComponent],
  providers: [],
})
export class PhraseModule {}

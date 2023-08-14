import {
  AfterViewInit,
  Component,
  ElementRef,
  OnInit,
  Renderer2,
  ViewChild,
} from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { AppService } from '../services/app.service';
import { tap } from 'rxjs/operators';
import { PhraseTableEntry } from './phrase';

@Component({
  selector: 'app-phrase',
  templateUrl: './phrase.component.html',
  styleUrls: ['./phrase.component.scss'],
})
export class PhraseComponent implements OnInit, AfterViewInit {
  // TODO: all locales or something like that
  definedColumns: string[] = ['pl', 'it', 'us', 'es', 'de'];
  columnToDisplay: Set<string> = new Set(['id']);
  // TODO: read from button states and couple with columnToDisplay
  buttonsClicked: Set<string> = new Set();

  dataSource = new MatTableDataSource(ELEMENT_DATA);
  @ViewChild(MatPaginator) paginator: any = MatPaginator;

  constructor(
    private _appService: AppService,
    private _elementRef: ElementRef,
    private _renderer: Renderer2
  ) {}

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit(): void {}

  onFlagClick(flag: string) {
    const element = document.getElementsByClassName('fi-' + flag)[0];
    if (this.buttonsClicked.has(flag)) {
      this._renderer.setStyle(element, 'filter', 'grayscale(100%)');
      this.buttonsClicked.delete(flag);
      this.columnToDisplay.delete(flag);
    } else {
      this._renderer.setStyle(element, 'filter', 'grayscale(0%)');
      this.buttonsClicked.add(flag);
      this.columnToDisplay.add(flag);
    }
    this.updateTable();
  }

  updateTable() {
    ELEMENT_DATA.splice(0);
    if (this.buttonsClicked.size == 0) return;

    this._appService
      .getPhrases(Array.from(this.buttonsClicked.values()))
      .pipe(
        tap((response) => {
          for (var key in response) {
            var res = response[key];
            const element: PhraseTableEntry = {
              id: res['id'],
              es: res['translationMap']['es'],
              us: res['translationMap']['en'],
              de: res['translationMap']['de'],
              pl: res['translationMap']['pl'],
              it: res['translationMap']['it'],
            };
            ELEMENT_DATA.push(element);
          }
          this.dataSource = new MatTableDataSource(ELEMENT_DATA);
        })
      )
      .subscribe();
  }
}

const ELEMENT_DATA: PhraseTableEntry[] = [];

import { Injectable, OnDestroy } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ScoreService implements OnDestroy {
  private _dataSource = new Subject<string>();
  private _scorePoints: number = 0;

  get scorePoints(): number {
    return this._scorePoints;
  }

  addScore(value: number) {
    this._scorePoints += value;
    this._dataSource.next('value');
  }

  resetScore() {
    this._scorePoints = 0;
  }

  ngOnDestroy() {
    this._dataSource.next('');
    this._dataSource.complete();
  }
}

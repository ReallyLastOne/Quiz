import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-score',
  templateUrl: './score.component.html',
  styleUrls: ['./score.component.scss'],
})
export class ScoreComponent implements OnInit {
  private _scoreString = '';

  @Input({ required: true })
  achievedScore: number;

  @Output('newGame')
  newGame = new EventEmitter<void>();

  constructor() {}

  get scoreString(): string {
    return this._scoreString;
  }
  set scoreString(scoreString: string) {
    this._scoreString = scoreString;
  }

  ngOnInit(): void {
    this.scoreString = `You achieved ${this.achievedScore} ${
      this.achievedScore == 1 ? 'point' : 'points'
    }`;
  }

  startAgain() {
    this.newGame.next();
  }
}

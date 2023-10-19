import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ScoreService } from './score.service';

@Component({
  selector: 'app-score',
  templateUrl: './score.component.html',
  styleUrls: ['./score.component.scss'],
})
export class ScoreComponent implements OnInit {
  private _score = 0;
  private _scoreString = '';

  @Output('newGame')
  newGame = new EventEmitter<void>();

  constructor(private readonly _scoreService: ScoreService) {}

  get score(): number {
    return this._score;
  }
  set score(score: number) {
    this._score = score;
  }

  get scoreString(): string {
    return this._scoreString;
  }
  set scoreString(scoreString: string) {
    this._scoreString = scoreString;
  }

  ngOnInit(): void {
    this.score = this._scoreService.scorePoints;
    this.scoreString = this.scoreText();
  }
  scoreText(): string {
    return `You have ${this.score} ${this.score == 1 ? 'point' : 'points'}`;
  }

  startAgain() {
    this.newGame.next();
  }

  dataSource() {}
}

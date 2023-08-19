import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ScoreService } from './score.service';

@Component({
  selector: 'app-score',
  templateUrl: './score.component.html',
  styleUrls: ['./score.component.scss'],
})
export class ScoreComponent implements OnInit {
  constructor(
    private readonly _router: Router,
    private readonly _scoreService: ScoreService
  ) {}

  private _score = 0;
  private _scoreString = '';

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
    this._router.navigate([`/exercise`]);
  }

  dataSource() {}
}

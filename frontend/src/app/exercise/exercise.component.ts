import { Component, OnInit, inject, DestroyRef } from '@angular/core';
import { of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { AppService } from '../services/app.service';
import { Exercise } from '../model/Exercise.model';
import { Router } from '@angular/router';
import { ScoreService } from '../score/score.service';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-exercise',
  templateUrl: './exercise.component.html',
  styleUrls: ['./exercise.component.scss'],
})
export class ExerciseComponent implements OnInit {
  private _destroyRef = inject(DestroyRef);
  private _newParsedData: Exercise;
  private _nextExercise: number = 1;
  private _exerciseCount: number;
  private _points: number = 0;
  private _allPoints: number = 0;
  answers: string[] = [];
  marked: boolean[] = [false, false, false, false];
  correctAnswer: string;
  question: string;
  clicked = false;
  exerciseEnd = false;
  questionCount = '';
  constructor(
    private readonly _appService: AppService,
    private readonly _router: Router,
    private readonly _scoreService: ScoreService
  ) {}

  ngOnInit(): void {
    this._exerciseCount = 3;
    this._scoreService.resetScore();
    this.nextApi();
  }

  private initializeExercise(exercise: Exercise): void {
    this.questionCount = `${this._nextExercise}/${this._exerciseCount}`;
    this.question = exercise.question;
    this.correctAnswer = exercise.correctAnswer;
    this.answers.push(...exercise.wrongAnswers);
    this.answers.push(exercise.correctAnswer);
    this.answers.sort((a, b) => 0.5 - Math.random());
  }

  private nextApi(): void {
    this._appService
      .startGame()
      .pipe(
        tap((data) => {
          this._newParsedData = JSON.parse(JSON.stringify(data));
          this.initializeExercise(this._newParsedData);
          this.exerciseEnd = false;
        }),
        catchError(() => {
          this._router.navigate([`/error`]);
          return of([]);
        }),
        takeUntilDestroyed(this._destroyRef)
      )
      .subscribe();
  }

  private summarizePoints(): void {
    this._allPoints += this._points;
    this._points = 0;
  }
  private prepareNextQuestion(): void {
    this.exerciseEnd = true;
    this.marked = [false, false, false, false];
    this.answers.length = 0;
    this.question = '';
    this.clicked = false;
  }

  getAnswer(event: any) {
    if (event.target.innerText.innerText == this.correctAnswer)
      event.target.classList.add('button-correct');
    else event.target.classList.add('button-wrong');
    this.clicked = true;
  }

  markAnswer(button: number, event: any) {
    for (let i = 0; i < this.marked.length; i++) {
      this.marked[i] = i + 1 == button ? true : false;
    }
    if (event.target.innerText == this.correctAnswer) this._points = 1;
    else this._points = 0;
    this.clicked = true;
  }

  nextQuestion() {
    this.prepareNextQuestion();
    this.summarizePoints();
    this._nextExercise++;
    if (this._nextExercise < 4) {
      this.nextApi();
    } else {
      this.exerciseEnd = true;
      this._scoreService.addScore(this._allPoints);
      this._router.navigate([`/score`], { skipLocationChange: true });
    }
  }
}

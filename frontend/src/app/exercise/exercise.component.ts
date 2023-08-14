import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { AppService } from '../services/app.service';
import { Exercise } from '../model/Exercise.model';
import { Router } from '@angular/router';
import { ScoreService } from '../score/score.service';

@Component({
  selector: 'app-exercise',
  templateUrl: './exercise.component.html',
  styleUrls: ['./exercise.component.scss'],
})
export class ExerciseComponent implements OnDestroy, OnInit {
  private subscriptions = new Subscription();
  private newParsedData: Exercise;
  private nextExercise: number = 1;
  private exerciseCount: number;
  private points: number = 0;
  private allPoints: number = 0;
  answers: string[] = [];
  marked: boolean[] = [false, false, false, false];
  correctAnswer: string;
  question: string;
  clicked = false;
  exerciseEnd = false;
  questionCount = '';
  constructor(
    private _appService: AppService,
    private router: Router,
    private _scoreService: ScoreService
  ) {}

  ngOnInit(): void {
    this.exerciseCount = 3;
    this._scoreService.resetScore();
    this.nextApi();
  }

  private initializeExercise(exercise: Exercise): void {
    this.questionCount = `${this.nextExercise}/${this.exerciseCount}`;
    this.question = exercise.question;
    this.correctAnswer = exercise.correctAnswer;
    this.answers.push(...exercise.wrongAnswers);
    this.answers.push(exercise.correctAnswer);
    this.answers.sort((a, b) => 0.5 - Math.random());
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
    if (event.target.innerText == this.correctAnswer) this.points = 1;
    else this.points = 0;
    this.clicked = true;
  }

  nextApi() {
    this.subscriptions.add(
      this._appService
        .getAnswer()
        .pipe(
          tap((data) => {
            this.newParsedData = JSON.parse(JSON.stringify(data));
            this.initializeExercise(this.newParsedData);
            this.exerciseEnd = false;
          }),
          catchError(() => {
            this.router.navigate([`/error`]);
            return of([]);
          })
        )
        .subscribe()
    );
  }

  nextQuestion() {
    this.prepareNextQuestion();
    this.summarizePoints();
    this.nextExercise++;
    if (this.nextExercise < 4) {
      this.nextApi();
    } else {
      this.exerciseEnd = true;
      this._scoreService.addScore(this.allPoints);
      this.router.navigate([`/score`], { skipLocationChange: true });
    }
  }

  summarizePoints() {
    this.allPoints += this.points;
    this.points = 0;
  }
  prepareNextQuestion() {
    this.exerciseEnd = true;
    this.marked = [false, false, false, false];
    this.answers.length = 0;
    this.question = '';
    this.clicked = false;
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }
}

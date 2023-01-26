import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { AppService } from '../app.service';
import { Exercise } from '../model/Exercise.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-exercise',
  templateUrl: './exercise.component.html',
  styleUrls: ['./exercise.component.css'],
})
export class ExerciseComponent implements OnDestroy, OnInit {
  private subscriptions = new Subscription();
  private newParsedData: Exercise;
  private nextExercise: number = 1;
  answers: string[] = [];
  correctAnswer: string;
  question: string;
  clicked = false;
  constructor(private _appService: AppService, private router: Router) {}

  ngOnInit(): void {
    this.nextApi();
  }

  private initializeExercise(exercise: Exercise): void {
    this.question = exercise.question;
    this.correctAnswer = exercise.correctAnswer;
    this.answers.push(...exercise.wrongAnswers);
    this.answers.push(exercise.correctAnswer);
    this.answers.sort((a, b) => 0.5 - Math.random());
  }

  getAnswer(event: any, answer: string) {
    if (answer == this.correctAnswer)
      event.target.classList.add('button-correct');
    else event.target.classList.add('button-wrong');
    this.clicked = true;
  }

  nextApi() {
    this.subscriptions.add(
      this._appService
        .getAnswer(this.nextExercise.toString())
        .pipe(
          tap((data) => {
            this.newParsedData = JSON.parse(JSON.stringify(data));
            this.initializeExercise(this.newParsedData);
          }),
          catchError((error) => {
            this.router.navigate([`/error`]);
            return of([]);
          })
        )
        .subscribe()
    );
  }

  nextQuestion() {
    this.nextExercise++;
    this.clicked = false;
    this.answers.length = 0;
    this.question = '';
    if (this.nextExercise < 6) {
      this.nextApi();
    }
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }
}

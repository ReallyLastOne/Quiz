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
  private points: number = 0;
  answers: string[] = [];
  marked: boolean[] = [false, false, false, false];
  correctAnswer: string;
  question: string;
  clicked = false;
  exerciseEnd = false;
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

  getAnswer(event: any) {
    if (event.target.innerText.innerText == this.correctAnswer)
      event.target.classList.add('button-correct');
    else event.target.classList.add('button-wrong');
    this.clicked = true;
  }

  markAnswer(button: number) {
    for (let i = 0; i < this.marked.length; i++) {
      this.marked[i] = i + 1 == button ? true : false;
    }
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
            this.exerciseEnd = false;
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
    this.marked = [false, false, false, false];
    this.exerciseEnd = true;
    this.nextExercise++;
    this.clicked = false;
    this.answers.length = 0;
    this.question = '';
    if (this.nextExercise < 6) {
      this.nextApi();
    } else {
      this.exerciseEnd = true;
    }
  }

  ngOnDestroy() {
    this.subscriptions.unsubscribe();
  }
}

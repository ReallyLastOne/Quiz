import {
  Component,
  OnInit,
  inject,
  DestroyRef,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
} from '@angular/core';
import { of } from 'rxjs';
import { catchError, finalize, switchMap } from 'rxjs/operators';
import { AppService } from '../services/app.service';
import { Exercise } from '../model/exercise.model';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { ExerciseService } from './exercise.service';

@Component({
  selector: 'app-exercise',
  templateUrl: './exercise.component.html',
  styleUrls: ['./exercise.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [ExerciseService],
})
export class ExerciseComponent implements OnInit {
  private _destroyRef = inject(DestroyRef);
  private _question: string;
  private _answers: string[];
  private _checkedAnswer!: string;
  private _isEndOfQuiz = false;

  public get question(): string {
    return this._question;
  }

  public get answers(): string[] {
    return this._answers;
  }

  public get checkedAnswer(): string {
    return this._checkedAnswer;
  }

  public set checkedAnswer(value: string) {
    this._checkedAnswer = value;
  }

  public get isEndOfQuiz(): boolean {
    return this._isEndOfQuiz;
  }

  constructor(
    private readonly _appService: AppService,
    private readonly _changeDetector: ChangeDetectorRef,
    private readonly _exerciseService: ExerciseService
  ) {}

  ngOnInit(): void {
    this.initializeGame();
  }

  private initializeGame(): void {
    this._appService
      .startGame()
      .pipe(
        switchMap(() => this._exerciseService.nextQuestion()),
        catchError((error) => {
          console.error(error);
          return of([]);
        }),
        takeUntilDestroyed(this._destroyRef),
        finalize(() => {
          this._changeDetector.detectChanges();
        })
      )
      .subscribe({
        next: (response: Exercise) => {
          this._question = response.content;
          this._answers = response.answers;
        },
      });
  }

  private nextQuestion(): void {
    this._exerciseService
      .nextQuestion()
      .pipe(takeUntilDestroyed(this._destroyRef))
      .subscribe({
        next: (response) => {
          this._question = response.content;
          this._answers = response.answers;
          this._changeDetector.detectChanges();
        },
      });
  }

  onConfirm(): void {
    if (this._checkedAnswer != undefined) {
      this._exerciseService
        .answerQuestion(this._checkedAnswer)
        .pipe(takeUntilDestroyed(this._destroyRef))
        .subscribe({
          next: (res) => {
            if (res.questionsLeft != 0) {
              this.nextQuestion();
            } else {
              this._appService
                .stopGame()
                .pipe(takeUntilDestroyed(this._destroyRef))
                .subscribe();
              this._isEndOfQuiz = true;
              this._question = '';
              this._answers = [];
            }
          },
          complete: () => {
            this.checkedAnswer = null;
            this._changeDetector.detectChanges();
          },
        });
    }
  }

  startNewGame(): void {
    this._isEndOfQuiz = false;
    this.initializeGame();
  }
}

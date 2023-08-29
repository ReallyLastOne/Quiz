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
import { Exercise } from '../model/Exercise.model';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-exercise',
  templateUrl: './exercise.component.html',
  styleUrls: ['./exercise.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ExerciseComponent implements OnInit {
  private _destroyRef = inject(DestroyRef);
  private _question: string;
  private _answers: string[];
  private _checkedAnswer!: string;

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

  constructor(
    private readonly _appService: AppService,
    private readonly _changeDetector: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.nextApi();
  }

  private nextApi(): void {
    this._appService
      .startGame()
      .pipe(
        switchMap(() => this._appService.nextQuestion()),
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
          console.log(response);
          this._question = response.content;
          this._answers = response.answers;
        },
      });
  }
}

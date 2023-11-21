import {
  ChangeDetectorRef,
  Component,
  DestroyRef,
  OnInit,
  inject,
} from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { AddTranslationRequest } from '../model/add-translation-request.model';
import { TranslationService } from '../services/translation.service';
import { catchError, finalize, of, switchMap } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-translation',
  templateUrl: './translation.component.html',
  styleUrls: ['./translation.component.scss'],
})
export class TranslationComponent implements OnInit {
  private readonly _destroyRef = inject(DestroyRef);
  private _translationGroup!: FormGroup;
  private _chooseLanguage!: FormControl;
  private _error = '';

  get translationGroup(): FormGroup {
    return this._translationGroup;
  }

  get error(): string {
    return this._error;
  }

  constructor(
    private readonly _translationService: TranslationService,
    private readonly _changeDetector: ChangeDetectorRef
  ) {}

  private initializeGame(): void {
    this._translationService
      .startGame()
      .pipe(
        catchError((error) => {
          console.error(error);
          return of([]);
        }),
        takeUntilDestroyed(this._destroyRef),
        finalize(() => {
          this._changeDetector.detectChanges();
        })
      )
      .subscribe({});
  }

  ngOnInit(): void {
    this.initializeGame();

    this._chooseLanguage = new FormControl({
      nickname: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
      ]),
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
      ]),
    });

    this._translationGroup = new FormGroup({
      translationLanguage: new FormControl('', Validators.required),
      translationWord: new FormControl('', Validators.required),
      firstLanguage: new FormControl('', Validators.required),
      secondLanguage: new FormControl('', Validators.required),
    });
  }

  private setError(errorMessage: string): void {
    this._error = errorMessage;
  }

  private clearError(): void {
    this._error = '';
  }

  OnSave(): void {
    if (this._translationGroup.invalid) {
      this.setError('Wypełnij wszystkie pola');
      return;
    } else {
      let translationRequest: AddTranslationRequest;
      translationRequest = new AddTranslationRequest(
        this._translationGroup.get('translationLanguage').value,
        this._translationGroup.get('translationWord').value
      );

      console.log(translationRequest);
      this.clearError();
    }
  }
}

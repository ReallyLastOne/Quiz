import {
  ChangeDetectionStrategy,
  Component,
  DestroyRef,
  OnInit,
  inject,
} from '@angular/core';
import { Router } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { AppService } from '../services/app.service';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomeComponent implements OnInit {
  private _destroyRef = inject(DestroyRef);
  constructor(
    private readonly _router: Router,
    private readonly _appService: AppService
  ) {}

  ngOnInit(): void {
    this._appService
      .stopGame()
      .pipe(takeUntilDestroyed(this._destroyRef))
      .subscribe();
  }

  startLearning(): void {
    this._router.navigate([`/exercise`]);
  }
}

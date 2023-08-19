import { Component, DestroyRef, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { AppService } from '../services/app.service';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  private _destroyRef = inject(DestroyRef);
  constructor(private router: Router, private _appService: AppService) {}

  ngOnInit(): void {
    this._appService
      .stopGame()
      .pipe(takeUntilDestroyed(this._destroyRef))
      .subscribe();
  }

  startLearning() {
    this.router.navigate([`/exercise`]);
  }
}

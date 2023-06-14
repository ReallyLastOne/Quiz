import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
} from '@angular/common/http';
import {
  BehaviorSubject,
  Observable,
  catchError,
  filter,
  switchMap,
  take,
  throwError,
} from 'rxjs';
import { UserAuthenticationService } from '../services/user-authentication.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private isRefreshing = false;
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(
    null
  );

  constructor(private _userAuthenticationService: UserAuthenticationService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const idToken = this._userAuthenticationService.getAccessToken();

    if (idToken != null) {
      const cloned = this.buildRequest(request, idToken);

      return next.handle(cloned).pipe(
        catchError((error) => {
          if (error instanceof HttpErrorResponse && error.status === 401) {
            return this.handle401Error(cloned, next);
          }
          return throwError(() => new Error(error));
        })
      );
    } else {
      return next.handle(request);
    }
  }

  private handle401Error(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.refreshTokenSubject.next(null);

      const token = this._userAuthenticationService.getRefreshToken();

      return this._userAuthenticationService.refreshToken(token).pipe(
        switchMap((token: any) => {
          this.isRefreshing = false;
          this.refreshTokenSubject.next(token.accessToken);
          return next.handle(this.buildRequest(request, token.accessToken));
        })
      );
    } else {
      return this.refreshTokenSubject.pipe(
        filter((token) => token != null),
        take(1),
        switchMap((jwt) => {
          return next.handle(this.buildRequest(request, jwt));
        })
      );
    }
  }

  private buildRequest(request: HttpRequest<any>, token: string) {
    return request.clone({
      headers: request.headers.set('Authorization', 'Bearer ' + token),
    });
  }
}

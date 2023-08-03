import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserAuthenticationService } from '../services/user-authentication.service';

@Injectable()
export class CsrfTokenInterceptor implements HttpInterceptor {
  constructor(
    private readonly _userAuthenticationService: UserAuthenticationService
  ) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    const csrfToken = this._userAuthenticationService.getCsrfToken();
    if ((request.method as string).toLowerCase() !== 'get' && csrfToken) {
      request = request.clone({
        withCredentials: true,
        setHeaders: {
          'X-Requested-With': 'XHLHttpRequest',
          'X-XSRF-TOKEN': csrfToken,
        },
      });
    }
    return next.handle(request);
  }
}

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AppService {
  constructor(private _http: HttpClient) {}

  startGame() {
    const params = new HttpParams().set('questions', 3);
    return this._http.post(environment.apiUrl + `/game/quiz/start`, null, {
      params,
    });
  }

  stopGame() {
    return this._http.post(environment.apiUrl + `/game/quiz/stop`, null);
  }

  getPhrases(locales: string[]) {
    if (locales != null) {
      var body = {};
      return this._http.request(
        'GET',
        environment.apiUrl + `/exercises/phrases?languages=` + locales
      );
    }
    return this._http.get(environment.apiUrl + `/exercises/phrases`);
  }
}

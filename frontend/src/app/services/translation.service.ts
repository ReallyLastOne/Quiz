import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root' })
export class TranslationService {
  constructor(private readonly _http: HttpClient) {}

  startGame() {
    const params = new HttpParams()
      .set('destinationLanguage', 'pl')
      .set('sourceLanguage', 'en');
    return this._http.post(
      environment.apiUrl + `/game/translation/start`,
      null,
      {
        params,
      }
    );
  }

  stopGame() {
    return this._http.post(environment.apiUrl + `/game/translation/stop`, null);
  }

  nextQuestion() {
    return this._http.post(environment.apiUrl + '/game/translation/next', null);
  }
}

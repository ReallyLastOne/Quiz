import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Exercise } from '../model/Exercise.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AppService {
  constructor(private _http: HttpClient) {}

  startGame() {
    const params = new HttpParams().set('questions', 6);
    return this._http.post(environment.apiUrl + `/game/quiz/start`, null, {
      params,
    });
  }

  stopGame() {
    return this._http.post(environment.apiUrl + `/game/quiz/stop`, null);
  }

  nextQuestion(): Observable<Exercise> {
    return this._http.post<Exercise>(
      environment.apiUrl + `/game/quiz/next`,
      null
    );
  }

  answerQuestion(answer: string): Observable<void> {
    return this._http.post<void>(environment.apiUrl + `/game/quiz/answer`, {
      answer: answer,
    });
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

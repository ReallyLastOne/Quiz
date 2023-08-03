import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class AppService {
  constructor(private http: HttpClient) { }

  getAnswer() {
    return this.http.post(environment.apiUrl + `/game/quiz/start`, null);
  }

  getPhrases(locales: string[]) {
    if (locales != null) {
      var body = {};
      return this.http.request("GET", environment.apiUrl + `/exercises/phrases?languages=` + locales);
    }
    return this.http.get(environment.apiUrl + `/exercises/phrases`);
  }
}

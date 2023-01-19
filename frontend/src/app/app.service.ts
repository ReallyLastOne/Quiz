import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class AppService {
  constructor(private http: HttpClient) {}

  rootUrl = '/api';

  getAnswer() {
    return this.http.get(this.rootUrl + '/v1/exercise/question/1');
  }
}

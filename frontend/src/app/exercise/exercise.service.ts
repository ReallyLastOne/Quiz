import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Exercise } from '../model/exercise.model';
import { environment } from 'src/environments/environment';
import { AnswerResponse } from '../model/answer-response.model';

@Injectable()
export class ExerciseService {
  constructor(private _http: HttpClient) {}

  nextQuestion(): Observable<Exercise> {
    return this._http.post<Exercise>(
      environment.apiUrl + `/game/quiz/next`,
      null
    );
  }

  answerQuestion(answer: string): Observable<AnswerResponse> {
    return this._http.post<AnswerResponse>(
      environment.apiUrl + `/game/quiz/answer`,
      {
        answer: answer,
      }
    );
  }
}

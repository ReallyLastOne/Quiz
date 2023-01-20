import { Component, OnInit} from '@angular/core';
import { AppService } from '../app.service';
import { Exercise } from '../model/Exercise.model';

@Component({
  selector: 'app-exercise',
  templateUrl: './exercise.component.html',
  styleUrls: ['./exercise.component.css']
})
export class ExerciseComponent implements OnInit {

  private newData: any;
  private newParsedData: Exercise;
  answers: string[] = ["first","second very long answer","third not that long but still average","forth"];
  question: string;
  constructor(private _appService:AppService) { }

  ngOnInit(): void {
    this._appService.getAnswer().subscribe(res=>{
         this.newParsedData = JSON.parse(JSON.stringify(res));
         this.initializeQuestion(this.newParsedData.question);
       });
  }

  private initializeQuestion(question: string): void {
  this.question = question;
  }

}

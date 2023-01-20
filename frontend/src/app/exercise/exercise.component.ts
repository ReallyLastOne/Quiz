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
  answers: string[] = [];
  correctAnswer: string;
  question: string;
  buttonStyle: string ="";
  clicked = false;
  constructor(private _appService:AppService) { }

  ngOnInit(): void {
    this._appService.getAnswer().subscribe(res=>{
         this.newParsedData = JSON.parse(JSON.stringify(res));
         this.initializeExercise(this.newParsedData);
       });
  }

  private initializeExercise(exercise: Exercise): void {
  this.question = exercise.question;
  this.correctAnswer = exercise.correctAnswer;
  this.answers.push(...exercise.wrongAnswers);
  this.answers.push(exercise.correctAnswer);
  this.answers.sort((a, b) => 0.5 - Math.random());
  }

  getAnswer(event:any, answer: string) {
  if(answer == this.correctAnswer)
    event.target.classList.add('button-correct');
    else
    event.target.classList.add('button-wrong');
    this.clicked = true;
  }

}

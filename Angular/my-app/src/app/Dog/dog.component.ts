import { Component } from '@angular/core';
import { DogService } from './dog.service';
import { Observable } from 'rxjs';
import { DogApiResponse } from './dog.service';

@Component({
  selector: 'app-dog',
  templateUrl: './dog.component.html',
  styleUrls: ['./dog.component.css'],
})
export class DogsComponent {
  dogs$: Observable<DogApiResponse>; 

  constructor(private dogService: DogService) {
    this.dogs$ = this.dogService.getRandomDog(); 
  }
}

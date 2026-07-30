import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';

export interface DogApiResponse {
  message: string;
  status: string;
}

@Injectable()
export class DogService {
  private apiUrl = 'https://dog.ceo/api/breeds/image/random';
  constructor(private http: HttpClient) {}

  getRandomDog(): Observable<DogApiResponse> {
    return this.http.get<DogApiResponse>(this.apiUrl);
  }
}
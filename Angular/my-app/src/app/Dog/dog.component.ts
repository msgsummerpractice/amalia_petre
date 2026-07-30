import { Component, signal } from '@angular/core';
import { DogService, DogApiResponse } from './dog.service';
import { CommonModule } from '@angular/common';
import {MatIconModule} from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButton } from '@angular/material/button';
import { AuthStatusDirective } from '../AuthStatusDirective';
import { RouterLink } from '@angular/router';
import { FormatPipe } from '../TitleFormatPipe';

@Component({
  selector: 'app-dog',
  templateUrl: './dog.component.html',
  standalone: true,
  imports: [RouterLink,CommonModule, MatIconModule, MatToolbarModule, MatSidenavModule, MatButton, AuthStatusDirective, FormatPipe],
  providers: [DogService]
})

export class DogsComponent {
  dog = signal<DogApiResponse | null>(null);
  loading = signal(false);
  error = signal<string | null>(null);

  constructor(private dogService: DogService) {}

  fetchRandomDog(): void {
    this.loading.set(true);
    this.error.set(null);
    this.dogService.getRandomDog().subscribe({
      next: (response) => {
        this.dog.set(response);
        this.loading.set(false);
      },
      error: (err) => {
        this.error.set('Failed to fetch dog');
        this.loading.set(false);
      },
    });
  }
}

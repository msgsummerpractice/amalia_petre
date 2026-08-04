import { Component, inject, signal } from '@angular/core';
import { DogService, DogApiResponse } from '../Dog/dog.service';
import { CommonModule } from '@angular/common';
import {MatIconModule} from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButton } from '@angular/material/button';
import { AuthStatusDirective } from '../AuthStatusDirective';
import { Router, RouterLink } from '@angular/router';
import { FormatPipe } from '../TitleFormatPipe';
import { ReactiveFormsModule } from '@angular/forms';
import {DogsComponent} from '../Dog/dog.component';
import { AuthService } from '../AuthService';

@Component({
  selector: 'app-home',
  imports: [DogsComponent,RouterLink,CommonModule, MatIconModule, MatToolbarModule, MatSidenavModule, MatButton, AuthStatusDirective, FormatPipe, ReactiveFormsModule],
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
})
export class Home {
  private authService = inject(AuthService);
  private router = inject(Router);

  onLogout(): void {
    // Clear the token and roles from the AuthService
    this.authService.logout();
    // Navigate to the login page
    this.router.navigate(['/login']);
  }
}

import { Component, signal } from '@angular/core';
import { DogService, DogApiResponse } from '../Dog/dog.service';
import { CommonModule } from '@angular/common';
import {MatIconModule} from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatButton } from '@angular/material/button';
import { AuthStatusDirective } from '../AuthStatusDirective';
import { RouterLink } from '@angular/router';
import { FormatPipe } from '../TitleFormatPipe';
import { ReactiveFormsModule } from '@angular/forms';
import {DogsComponent} from '../Dog/dog.component';

@Component({
  selector: 'app-home',
  imports: [DogsComponent,RouterLink,CommonModule, MatIconModule, MatToolbarModule, MatSidenavModule, MatButton, AuthStatusDirective, FormatPipe, ReactiveFormsModule],
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
})
export class Home {}

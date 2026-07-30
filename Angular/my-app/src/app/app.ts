import { Component, signal } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { RouterOutlet } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import { DogsComponent } from './Dog/dog.component';
import { CommonModule } from '@angular/common';
import { AuthStatusDirective } from './AuthStatusDirective';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterLink,RouterOutlet, MatButton, MatToolbarModule, MatSidenavModule, MatIconModule, DogsComponent, CommonModule, AuthStatusDirective],
  templateUrl: './app.html',
  styleUrls: ['./app.css'],
})
export class App {
  protected readonly title = signal('my-app');

}

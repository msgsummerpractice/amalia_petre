import { Routes } from '@angular/router';
import { NotFoundComponent } from './not-found/NotFoundComponent';
import { DogsComponent } from './Dog/dog.component';
import { Login } from './login/login';

export const routes: Routes = [
   {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
  {
    path: 'login',
    loadComponent: () => import('./login/login').then(m => m.Login),
  },
  {
    path: 'home',
    component: DogsComponent,
  },
  {
    path: '**',
    component: NotFoundComponent,
  },
];

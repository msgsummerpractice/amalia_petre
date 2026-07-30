import { Routes } from '@angular/router';
import { NotFoundComponent } from './not-found/NotFoundComponent';
import { DogsComponent } from './Dog/dog.component';
import { authGuard } from './AuthGuard';

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
    canActivate: [authGuard],
  },
  {
    path: '**',
    component: NotFoundComponent,
  },
];

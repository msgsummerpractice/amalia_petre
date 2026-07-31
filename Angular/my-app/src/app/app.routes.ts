import { Routes } from '@angular/router';
import { NotFoundComponent } from './not-found/NotFoundComponent';
import { DogsComponent } from './Dog/dog.component';
import { authGuard } from './AuthGuard';
import { Home } from './home/home';

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
    component: Home,
    canActivate: [authGuard],
  },
  {
    path: '**',
    component: NotFoundComponent,
  },
];

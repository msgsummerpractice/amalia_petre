import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from './AuthService';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const authService = inject(AuthService);
  const token = authService.getToken();

  if(token){
    const cloned = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      }
    });
    console.log('AuthInterceptor: Adding Authorization header');
    return next(cloned);
  }
  return next(req);
};
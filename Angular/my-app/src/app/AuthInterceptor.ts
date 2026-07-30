import { HttpInterceptorFn } from '@angular/common/http';
// token from https://www.jwt.io/
const token = 'Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
    console.log('AuthInterceptor: Adding Authorization header');
  const cloned = req.clone({
    setHeaders: {
      Authorization: `${token}`,
    },
  });
  return next(cloned);
};
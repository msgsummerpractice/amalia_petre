import { inject } from "@angular/core";
import { AuthService } from "./AuthService";
import { CanActivateFn, Router } from "@angular/router";

export const authGuard: CanActivateFn = () => {
    const authService = inject(AuthService);
    const router = inject(Router);
    
    if (!authService.isAuthenticated()) {
        router.navigate(['/login']);
        return false;
    }
    return true;
};
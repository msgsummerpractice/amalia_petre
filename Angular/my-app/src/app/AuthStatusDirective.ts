import { Directive, effect, inject, Input, input, TemplateRef, ViewContainerRef } from "@angular/core";
import { AuthService } from "./AuthService";

@Directive({
  selector: "[authStatus]",
  standalone: true,
})
export class AuthStatusDirective {

  private readonly _viewContainer = inject(ViewContainerRef);
  private readonly _template = inject(TemplateRef);
  private readonly _authService = inject(AuthService);

  authStatus = input<string>('');

  constructor() {
    effect(() => {
      const requiredRole = this.authStatus();
      const isVisible =requiredRole ? this._authService.isAuthenticated() && this._authService.hasRole(requiredRole) : this._authService.isAuthenticated();

      if(isVisible){
        this._viewContainer.createEmbeddedView(this._template);
      }
      else{
        this._viewContainer.clear();
      }
    });
  }
}
import { Directive, effect, inject, Input, input, TemplateRef, ViewContainerRef } from "@angular/core";

@Directive({
  selector: "[authStatus]",
  standalone: true,
})
export class AuthStatusDirective {

  private readonly _viewContainer = inject(ViewContainerRef);
  private readonly _template = inject(TemplateRef);

  authStatus = input<boolean>(true);

  constructor() {
    effect(() => {
      if (this.authStatus()) {
        this._viewContainer.createEmbeddedView(this._template);
      } else {
        this._viewContainer.clear();
      }
    });
  }
}
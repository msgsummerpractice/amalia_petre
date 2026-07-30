import { Component, inject } from '@angular/core';
import {
  FormControl,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import {NgIf} from '@angular/common';

type LoginForm = {
  email: FormControl<string>;
  password: FormControl<string>;
};

@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
  standalone: true,
  imports: [ReactiveFormsModule, NgIf],
})
export class Login {
  private readonly _formBuilder = inject(NonNullableFormBuilder);
  protected readonly loginFormGroup = this._formBuilder.group<LoginForm>({
    email: this._formBuilder.control('', { validators: [Validators.required, Validators.email] }),
    password: this._formBuilder.control('', Validators.required),
  });

  onFormSubmit(): void {
    if (this.loginFormGroup.valid) {
      const email = this.loginFormGroup.get('email')?.value;
      const password = this.loginFormGroup.get('password')?.value;
      console.log('Form submitted with values:', { email, password });
    } else {
      console.log('Form is invalid');
    }
  }
}

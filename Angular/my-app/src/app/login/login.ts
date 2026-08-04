import { Component, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import {
  FormControl,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import {NgIf} from '@angular/common';
import { AuthService } from '../AuthService';

type LoginForm = {
  username: FormControl<string>;
  password: FormControl<string>;
};

type OttForm = {
  pin: FormControl<string>;
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
  private readonly _authService = inject(AuthService);
  private readonly _router = inject(Router);

  protected readonly loginFormGroup = this._formBuilder.group<LoginForm>({
    username: this._formBuilder.control('', Validators.required),
    password: this._formBuilder.control('', Validators.required),
  });

  protected readonly ottFormGroup = this._formBuilder.group<OttForm>({
    pin: this._formBuilder.control('', Validators.required),
  });

  protected mfaRequired = signal(false);
  errorMessage = signal<string | null>(null);
  private pendingUsername='';


  onFormSubmit(): void {
    if (this.loginFormGroup.invalid) return;
    this.errorMessage.set(null); 
      const { username, password } = this.loginFormGroup.getRawValue();
      if (!username || !password) {
        this.errorMessage.set('Username and password are required.');
        return;
      }
      
      this._authService.login(username, password).subscribe({
        next: (response) => {
          if (response.mfaRequired) {
            this.pendingUsername = username;
            console.log('Username: ', this.pendingUsername);
            this.mfaRequired.set(true);
          }
        },
        error: () => {
          this.errorMessage.set('Login failed. Please check your credentials.');
        },
      });
  }

  onOttSubmit(): void {
    if (this.ottFormGroup.invalid) return;
    this.errorMessage.set(null); 
      
      this._authService.verifyOtt(this.pendingUsername, this.ottFormGroup.getRawValue().pin).subscribe({
        next: (response) => {
          this._router.navigate(['/home']);
        },
        error: () => {
          this.errorMessage.set('Verification failed. Please check your one-time token.');
        },
      });
  }
}

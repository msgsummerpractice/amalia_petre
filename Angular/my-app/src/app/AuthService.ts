import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

export interface SignInResponse {
  mfaRequired: boolean;
  accessToken: string | null;
  tokenType: string;
  expiresInSeconds: number;
  roles: string[] | null;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/api/auth';

  private _token=signal<string | null>(null);
  private _roles=signal<string[]>([]);

  isAuthenticated = computed(() => this._token() !== null);
  roles= computed(() => this._roles());

  constructor(private http: HttpClient) {
    const savedToken=sessionStorage.getItem('accessToken');
    const savedRoles=sessionStorage.getItem('roles');
    if (savedToken) {
      this._token.set(savedToken);
    }
    if (savedRoles) {
      this._roles.set(JSON.parse(savedRoles));
    }
  }

  login(username: string, password: string) : Observable<SignInResponse> {
    return this.http.post<SignInResponse>(`${this.apiUrl}/login`, { username, password });
  }

  verifyOtt(username: string, token: string) : Observable<SignInResponse> {
    return this.http.post<SignInResponse>(`${this.apiUrl}/verify-ott`, { username, token }).pipe(
      tap((response: SignInResponse) => {
        if (response.accessToken) {
          this._token.set(response.accessToken);
          this._roles.set(response.roles ?? []);
          sessionStorage.setItem('accessToken', response.accessToken);
          sessionStorage.setItem('roles', JSON.stringify(response.roles ?? []));
        }
      })
    );
  }

  getToken(): string | null {
    return this._token();
  }

  hasRole(role: string): boolean {
    return this._roles().includes(role);
  }

  logout() : void {
    this._token.set(null);
    this._roles.set([]);
    sessionStorage.removeItem('accessToken');
    sessionStorage.removeItem('roles');
  }
}

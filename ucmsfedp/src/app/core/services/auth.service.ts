import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, tap } from 'rxjs';
import {environment} from '../../environments/environment';
import {MatSnackBar} from '@angular/material/snack-bar';


export interface AuthState {
  token: string | null;
  username: string | null;
  fullName: string | null;
  roles: string[];
  approved?: boolean;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private base = environment.apiBaseUrl || '';
  private key = 'ucms.auth';
  private _state$ = new BehaviorSubject<AuthState>({
    token: null, username: null, fullName: null, roles: [], approved: false
  });
  state$ = this._state$.asObservable();

  constructor(private http: HttpClient,
              private snack: MatSnackBar,
              ) {
    const saved = localStorage.getItem(this.key);
    if (saved) this._state$.next(JSON.parse(saved));
  }

  get snapshot() { return this._state$.value; }
  getToken() { return this.snapshot.token; }

  hasRole(role: 'ADMIN'|'LECTURER'|'STUDENT') {
    return this.snapshot.roles.includes(role);
  }

  login(username: string, password: string) {
    return this.http.post<any>(`${this.base}/api/auth/login`, { username, password }).pipe(
      tap(res => {
        const token =
          res?.token ||
          res?.accessToken ||
          res?.jwt ||
          null;

        const user = res?.user || res;
        const roles = Array.isArray(user?.roles) ? user.roles : [];

        const next: AuthState = {
          token,
          username: user?.username ?? null,
          fullName: user?.fullName ?? null,
          roles,
          approved: user?.approved ?? res?.approved ?? true
        };

        if (!token) {
          throw new Error('Login succeeded but no token was returned by the server.');
        }

        this.persist(next);
      })
    );
  }


  register(dto: any) {
    return this.http.post<any>(`${this.base}/api/auth/register`, dto).pipe(tap(res =>{
      this.snack.open(res, 'Close', { duration: 2500 })
    }));

  }

  logout() {
    this.persist({ token: null, username: null, fullName: null, roles: [], approved: false });
  }

  private persist(s: AuthState) {
    this._state$.next(s);
    localStorage.setItem(this.key, JSON.stringify(s));
  }
}

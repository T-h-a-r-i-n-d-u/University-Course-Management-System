import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

import { AuthService } from '../services/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private auth: AuthService) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.auth.getToken();
    const isAuth = req.url.includes('/api/auth/');
    if (!token || isAuth) return next.handle(req);

    const clone = req.clone({ setHeaders: { Authorization: `Bearer ${token}` } });
    return next.handle(clone);
  }
}

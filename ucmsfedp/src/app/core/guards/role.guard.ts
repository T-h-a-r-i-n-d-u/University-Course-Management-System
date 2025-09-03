import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({ providedIn: 'root' })
export class RoleGuard implements CanActivate {
  constructor(private auth: AuthService, private router: Router) {}
  canActivate(route: ActivatedRouteSnapshot): boolean {
    const roles: string[] = route.data['roles'] || [];
    if (!this.auth.getToken()) { this.router.navigateByUrl('/auth/login'); return false; }
    if (roles.length === 0) return true;
    for (const r of roles) if (this.auth.hasRole(r as any)) return true;
    this.router.navigateByUrl('/dashboard');
    return false;
  }
}

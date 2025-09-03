import { Component } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-shell',
  templateUrl: './shell.component.html',
  styleUrls: ['./shell.component.scss'],
  standalone:false
})
export class ShellComponent {
  constructor(public auth: AuthService, private router: Router) {}
  isAdmin() { return this.auth.hasRole('ADMIN'); }
  isLecturer() { return this.auth.hasRole('LECTURER'); }
  isStudent() { return this.auth.hasRole('STUDENT'); }
  logout() { this.auth.logout(); this.router.navigateByUrl('/auth/login'); }
}

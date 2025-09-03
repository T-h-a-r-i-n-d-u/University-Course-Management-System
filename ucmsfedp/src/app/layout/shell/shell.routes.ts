import { Routes } from '@angular/router';
import { ShellComponent } from './shell.component';
import { RoleGuard } from '../../core/guards/role.guard';

export const SHELL_ROUTES: Routes = [
  {
    path: '',
    component: ShellComponent,
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', loadComponent: () => import('../../features/dashboard/dashboard.component').then(m => m.DashboardComponent) },
      // add other features later (courses, notes, etc.)
      { path: 'admin/report', canActivate: [RoleGuard], data: { roles: ['ADMIN'] },
        loadComponent: () => import('../../features/admin-report/admin-report.component').then(m => m.AdminReportComponent) }
    ]
  }
];

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShellComponent } from './layout/shell/shell.component';
import { AuthGuard } from './core/guards/auth.guard';

const routes: Routes = [
  { path: 'auth', loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule) },

  {
    path: '',
    component: ShellComponent,
    canActivate: [AuthGuard],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', loadChildren: () => import('./features/dashboard/dashboard.module').then(m => m.DashboardModule) },
      { path: 'courses', loadChildren: () => import('./features/courses/courses.module').then(m => m.CoursesModule) },

      // { path: 'assignments', loadChildren: () => import('./features/assigments/assignments.module').then(m => m.AssignmentsModule) },
      { path: 'notes', loadChildren: () => import('./features/notes/notes.module').then(m => m.NotesModule) },
      { path: 'enrollments', loadChildren: () => import('./features/enrollments/enrollments.module').then(m => m.EnrollmentsModule) },
      { path: 'results', loadChildren: () => import('./features/results/results.module').then(m => m.ResultsModule) },
      { path: 'admin', loadChildren: () => import('./features/admin/admin.module').then(m => m.AdminModule) },
    ]
  },

  { path: '**', redirectTo: 'dashboard' }
];

@NgModule({ imports: [RouterModule.forRoot(routes)], exports: [RouterModule] })
export class AppRoutingModule {}

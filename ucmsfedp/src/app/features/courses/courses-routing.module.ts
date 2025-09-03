import { NgModule } from '@angular/core';


import { RoleGuard } from '../../core/guards/role.guard';
import { AuthGuard } from '../../core/guards/auth.guard';
import {CoursesListComponent} from './pages/list/courses-list.component';
import {CourseFormComponent} from './pages/form/course-form.component';
import {CourseDetailComponent} from './pages/detail/course-detail.component';
import {RouterModule, Routes} from '@angular/router';


const routes: Routes = [
  { path: '', component: CoursesListComponent, canActivate:[AuthGuard] },
  { path: 'new', component: CourseFormComponent, canActivate:[RoleGuard], data:{roles:['ADMIN']} },

  // child feature routes BEFORE ':id'
  { path: ':id/assignments', loadChildren: () => import('../assigments/assignments.module').then(m=>m.AssignmentsModule) },
  { path: ':id/notes', loadChildren: () => import('../notes/notes.module').then(m=>m.NotesModule) },

  { path: ':id', component: CourseDetailComponent, canActivate:[AuthGuard] },
  { path: ':id/edit', component: CourseFormComponent, canActivate:[RoleGuard], data:{roles:['ADMIN']} },
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CoursesRoutingModule {}

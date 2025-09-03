// src/app/features/assigments/assignments.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AssignmentsListComponent } from './pages/list/assignments-list.component';
import { AssignmentDetailComponent } from './pages/details/assignment-detail.component';
import { AssignmentFormComponent } from './pages/form/assignment-form.component';
import { AuthGuard } from '../../core/guards/auth.guard';
import { RoleGuard } from '../../core/guards/role.guard';

const routes: Routes = [
  { path: '', component: AssignmentsListComponent, canActivate: [AuthGuard] },
  { path: 'new', component: AssignmentFormComponent, canActivate: [RoleGuard], data: { roles:['LECTURER'] } },
  { path: ':aid', component: AssignmentDetailComponent, canActivate: [AuthGuard] },
  { path: ':aid/edit', component: AssignmentFormComponent, canActivate: [RoleGuard], data: { roles:['LECTURER'] } },
];

@NgModule({
  imports: [SharedModule, FormsModule, ReactiveFormsModule, RouterModule.forChild(routes)],
  declarations: [AssignmentsListComponent, AssignmentDetailComponent, AssignmentFormComponent]
})
export class AssignmentsModule {}

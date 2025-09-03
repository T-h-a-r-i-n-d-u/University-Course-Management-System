// src/app/features/admin/admin.module.ts
import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';
import { RouterModule, Routes } from '@angular/router';
import { RoleGuard } from '../../core/guards/role.guard';

import { AdminReportComponent } from './pages/report/admin-report.component';
import {PendingUsersComponent} from './pages/users/pending-users.component';
import {LecturersListComponent} from './pages/lecturers/lecturers-list.component';
import {LecturerDialogComponent} from './pages/lecturers/lecturer-dialog.component';
import {AssignLecturerDialog} from './pages/detail/assign-lecturer.dialog';
import {ReactiveFormsModule} from '@angular/forms';


const routes: Routes = [
  { path: 'report', component: AdminReportComponent, canActivate:[RoleGuard], data:{ roles:['ADMIN'] } },
  { path: 'users/pending', component: PendingUsersComponent, canActivate:[RoleGuard], data:{ roles:['ADMIN'] } },
  { path: 'lecturers', component: LecturersListComponent, canActivate:[RoleGuard], data:{ roles:['ADMIN'] } },
];

@NgModule({
  imports: [SharedModule, RouterModule.forChild(routes), ReactiveFormsModule],
  declarations: [AdminReportComponent, PendingUsersComponent, LecturersListComponent, LecturerDialogComponent,AssignLecturerDialog]
})
export class AdminModule {}

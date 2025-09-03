import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AuthGuard } from '../../core/guards/auth.guard';
import {NotesListComponent} from './pages/list/notes-list.component';

const routes: Routes = [
  { path: 'course/:courseId', component: NotesListComponent, canActivate: [AuthGuard] }
];

@NgModule({ imports: [RouterModule.forChild(routes)], exports: [RouterModule] })
export class NotesRoutingModule {}

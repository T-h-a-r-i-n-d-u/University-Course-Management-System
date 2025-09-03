import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { FormsModule } from '@angular/forms';
import { NotesListComponent } from './pages/list/notes-list.component';
import { AuthGuard } from '../../core/guards/auth.guard';


const routes: Routes = [
  { path: '', component: NotesListComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [SharedModule, FormsModule, RouterModule.forChild(routes)],
  declarations: [NotesListComponent]
})
export class NotesModule {}

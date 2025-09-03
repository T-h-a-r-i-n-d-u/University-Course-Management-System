import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';
import { RouterModule, Routes } from '@angular/router';
import {EnrollmentsMyComponent} from './pages/my/enrollments-my.component';

const routes: Routes = [{ path: '', component: EnrollmentsMyComponent }];

@NgModule({
  imports: [SharedModule, RouterModule.forChild(routes)],
  declarations: [EnrollmentsMyComponent]
})
export class EnrollmentsModule {}

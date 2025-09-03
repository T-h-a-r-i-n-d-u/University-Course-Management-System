import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CoursesRoutingModule } from './courses-routing.module';
import {CoursesListComponent} from './pages/list/courses-list.component';
import {CourseDetailComponent} from './pages/detail/course-detail.component';
import {CourseFormComponent} from './pages/form/course-form.component';


@NgModule({
  imports: [SharedModule, FormsModule, ReactiveFormsModule, CoursesRoutingModule],
  declarations: [CoursesListComponent, CourseDetailComponent, CourseFormComponent]
})
export class CoursesModule {}

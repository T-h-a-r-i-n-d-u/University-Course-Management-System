import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CoursesService, Course } from '../../courses.service';
import {EnrollmentsService} from '../../../enrollments/enrollments.service';
import {AuthService} from '../../../../core/services/auth.service';
import {AssignLecturerDialog} from '../../../admin/pages/detail/assign-lecturer.dialog';
import {MatDialog} from '@angular/material/dialog';


@Component({
  selector: 'app-course-detail',
  templateUrl: './course-detail.component.html',
  styleUrls: ['./course-detail.component.scss'],
  standalone:false
})
export class CourseDetailComponent implements OnInit {
  id!: number;
  loading = false;
  c?: Course;

  constructor(
    private route: ActivatedRoute,
    public router: Router,
    private api: CoursesService,
    private enrollApi: EnrollmentsService,
    private snack: MatSnackBar,
    public auth: AuthService,
    private dialog:MatDialog
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.fetch();
  }

  isAdmin() { return this.auth.hasRole('ADMIN'); }
  isStudent() { return this.auth.hasRole('STUDENT'); }

  fetch() {
    this.loading = true;
    this.api.get(this.id).subscribe({
      next: (r) => { this.c = r; this.loading = false; },
      error: () => { this.loading = false; this.snack.open('Course not found', 'Close', { duration: 2500 }); this.router.navigateByUrl('/courses'); }
    });
  }

  edit() { this.router.navigate(['/courses', this.id, 'edit']); }

  remove() {
    if (!confirm('Delete this course?')) return;
    this.api.remove(this.id).subscribe({
      next: () => { this.snack.open('Course deleted', 'Close', { duration: 2500 }); this.router.navigateByUrl('/courses'); },
      error: (e) => this.snack.open(e?.error?.message || 'Delete failed', 'Close', { duration: 3000 })
    });
  }

  enroll() {
    if (!this.c) return;
    this.enrollApi.request(this.c.id).subscribe({
      next: () => this.snack.open('Enrollment request sent.', 'Close', { duration: 2500 }),
      error: (e) => this.snack.open(e?.error?.message || 'Failed to enroll', 'Close', { duration: 3000 })
    });
  }

  assignLecturer(){
    const ref = this.dialog.open(AssignLecturerDialog);
    ref.afterClosed().subscribe(userId=>{
      if(!userId || !this.id) return;
      this.api.assignLecturer(this.id, userId).subscribe({
        next: _ => this.snack.open('Assigned','Close',{duration:2000}),
        error: e => this.snack.open(e?.error?.message || 'Failed','Close',{duration:3000})
      });
    });
  }
}

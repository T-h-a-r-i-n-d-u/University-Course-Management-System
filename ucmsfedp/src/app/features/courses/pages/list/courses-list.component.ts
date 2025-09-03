import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CoursesService, Course } from '../../courses.service';
import {EnrollmentsService} from '../../../enrollments/enrollments.service';
import {AuthService} from '../../../../core/services/auth.service';


@Component({
  selector: 'app-courses-list',
  templateUrl: './courses-list.component.html',
  styleUrls: ['./courses-list.component.scss'],
  standalone:false
})
export class CoursesListComponent implements OnInit {
  loading = false;
  q = '';
  data: Course[] = [];
  filtered: Course[] = [];

  constructor(
    private api: CoursesService,
    private enrollApi: EnrollmentsService,
    private auth: AuthService,
    private snack: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetch();
  }

  isAdmin() { return this.auth.hasRole('ADMIN'); }
  isStudent() { return this.auth.hasRole('STUDENT'); }

  fetch() {
    this.loading = true;
    this.api.list().subscribe({
      next: (r) => { this.data = r; this.applyFilter(); this.loading = false; },
      error: () => { this.data = []; this.applyFilter(); this.loading = false; }
    });
  }

  applyFilter() {
    const q = this.q.trim().toLowerCase();
    this.filtered = !q ? this.data : this.data.filter(c =>
      c.title?.toLowerCase().includes(q) || c.code?.toLowerCase().includes(q)
    );
  }

  view(c: Course) { this.router.navigate(['/courses', c.id]); }

  enroll(c: Course) {
    if (!this.isStudent()) return;
    this.enrollApi.request(c.id).subscribe({
      next: () => this.snack.open('Enrollment request sent.', 'Close', { duration: 2500 }),
      error: (e) => this.snack.open(e?.error?.message || 'Failed to enroll', 'Close', { duration: 3000 })
    });
  }

  create() { this.router.navigateByUrl('/courses/new'); }
}


import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import {Course, CoursesService} from '../courses/courses.service';

import {Enrollment, EnrollmentsService} from '../enrollments/enrollments.service';
import {Result, ResultsService} from '../results/results.service';
import {AdminReport, AdminReportService} from '../admin/admin-report.service';



@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  standalone: false
})
export class DashboardComponent implements OnInit {


  fullName = '';
  roles: string[] = [];
  now = new Date();


  adminLoading = false;
  admin?: AdminReport;


  studentLoading = false;
  myEnrollments: Enrollment[] = [];
  myResults: Result[] = [];


  coursesLoading = false;
  courses: Course[] = [];

  constructor(
    public auth: AuthService,
    private router: Router,
    private adminApi: AdminReportService,
    private coursesApi: CoursesService,
    private enrollmentsApi: EnrollmentsService,
    private resultsApi: ResultsService
  ) {}

  ngOnInit(): void {
    // Read current auth state safely
    const state = this.auth.snapshot;
    this.fullName = state?.fullName || 'User';
    this.roles = state?.roles || [];

    // always show a few courses to make the page feel “alive”
    this.coursesLoading = true;
    this.coursesApi.list().subscribe({
      next: r => { this.courses = r.slice(0, 6); this.coursesLoading = false; },
      error: () => { this.courses = []; this.coursesLoading = false; }
    });

    if (this.isAdmin()) {
      this.adminLoading = true;
      this.adminApi.get().subscribe({
        next: r => { this.admin = r; this.adminLoading = false; },
        error: () => { this.adminLoading = false; }
      });
    }

    if (this.isStudent()) {
      this.studentLoading = true;
      this.enrollmentsApi.my().subscribe({
        next: e => { this.myEnrollments = e; this.studentLoading = false; },
        error: () => { this.myEnrollments = []; this.studentLoading = false; }
      });
      this.resultsApi.mine().subscribe({
        next: r => { this.myResults = r; },
        error: () => { this.myResults = []; }
      });
    }
  }


  // role helpers
  isAdmin()    { return this.auth.hasRole('ADMIN'); }
  isStudent()  { return this.auth.hasRole('STUDENT'); }
  isLecturer() { return this.auth.hasRole('LECTURER'); }

  // navigation shortcuts
  go(path: string) { this.router.navigateByUrl(path); }
}

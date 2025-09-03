import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from '../../environments/environment';


export interface AdminReport {
  // high-level totals
  totalUsers: number;
  totalStudents: number;
  totalLecturers: number;
  totalCourses: number;

  // add these missing fields (your template uses them)
  totalAssignments: number;
  totalSubmissions: number;
  pendingUserApprovals: number;

  // per-course breakdown
  courses: Array<{
    id: number;
    code: string;
    title: string;
    assignments: number;
    notes: number;
    approvedEnrolls: number;
    submissions: number;
    results: number;
  }>;
}


@Injectable({ providedIn: 'root' })
export class AdminReportService {
  private base = environment.apiBaseUrl || '';
  constructor(private http: HttpClient) {}

  get(): Observable<AdminReport> {
    // adjust if your backend path differs
    return this.http.get<AdminReport>(`${this.base}/api/admin/report`);
  }
}

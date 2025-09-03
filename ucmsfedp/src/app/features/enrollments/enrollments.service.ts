import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import {environment} from '../../environments/environment';

export type EnrollmentStatus = 'PENDING'|'APPROVED'|'REJECTED';
export interface Enrollment { id:number; courseId:number; studentId:number; status:EnrollmentStatus; createdAt:string; decidedAt?:string; }

@Injectable({ providedIn: 'root' })
export class EnrollmentsService {
  private base = environment.apiBaseUrl || '';
  constructor(private http: HttpClient) {}
  my(): Observable<Enrollment[]> { return this.http.get<Enrollment[]>(`${this.base}/api/enrollments/my`); }
  request(courseId:number) { return this.http.post<Enrollment>(`${this.base}/api/enrollments`, { courseId }); }

  // admin
  pending(): Observable<Enrollment[]> { return this.http.get<Enrollment[]>(`${this.base}/api/enrollments/pending`); }
  decide(id:number, approve:boolean) { return this.http.post<void>(`${this.base}/api/enrollments/${id}/decision`, { approve }); }
}


import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import {environment} from '../../environments/environment';

export interface Assignment {
  id: number;
  courseId: number;
  title: string;
  description?: string;
  deadline: string;
  createdAt?: string;
}

export interface Submission {
  id: number;
  assignmentId: number;
  studentId: number;
  studentName: string;
  originalFilename: string;
  size: number;
  contentType: string;
  submittedAt: string;
  marks?: number;
  feedback?: string;
}

@Injectable({ providedIn: 'root' })
export class AssignmentsService {
  private base = environment.apiBaseUrl || '';
  constructor(private http: HttpClient) {}

  listByCourse(courseId: number): Observable<Assignment[]> {
    return this.http.get<Assignment[]>(`${this.base}/api/assignments/course/${courseId}`);
  }
  getOne(id: number): Observable<Assignment> {
    return this.http.get<Assignment>(`${this.base}/api/assignments/${id}`);
  }
  create(dto: {courseId:number; title:string; description?:string; deadline:string;}): Observable<Assignment> {
    return this.http.post<Assignment>(`${this.base}/api/assignments`, dto);
  }
  update(id:number, dto: Partial<Assignment>): Observable<Assignment> {
    return this.http.put<Assignment>(`${this.base}/api/assignments/${id}`, dto);
  }
  delete(id:number){ return this.http.delete<void>(`${this.base}/api/assignments/${id}`); }

  // submissions
  submit(assignmentId:number, file:File): Observable<Submission> {
    const fd = new FormData(); fd.append('file', file);
    return this.http.post<Submission>(`${this.base}/api/submissions/${assignmentId}`, fd);
  }
  mine(assignmentId:number): Observable<Submission> {
    return this.http.get<Submission>(`${this.base}/api/submissions/mine/${assignmentId}`);
  }
  listSubmissions(assignmentId:number): Observable<Submission[]> {
    return this.http.get<Submission[]>(`${this.base}/api/submissions/assignment/${assignmentId}`);
  }
  download(submissionId:number) {
    window.location.href = `${this.base}/api/submissions/${submissionId}/file`;
  }
  grade(submissionId:number, marks:number, feedback:string){
    return this.http.post<Submission>(`${this.base}/api/submissions/${submissionId}/grade`, { marks, feedback });
  }
}

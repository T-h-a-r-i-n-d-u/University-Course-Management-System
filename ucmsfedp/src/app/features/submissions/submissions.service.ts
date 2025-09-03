import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import {environment} from '../../environments/environment';

export interface Submission {
  id: number;
  assignmentId: number;
  studentId: number;
  fileName: string;
  submittedAt: string;
}

@Injectable({ providedIn: 'root' })
export class SubmissionsService {
  private base = environment.apiBaseUrl || '';
  constructor(private http: HttpClient) {}

  upload(assignmentId:number, file:File): Observable<Submission> {
    const form = new FormData(); form.append('file', file);
    return this.http.post<Submission>(`${this.base}/api/submissions/${assignmentId}`, form);
  }
  byAssignment(assignmentId:number): Observable<Submission[]> {
    return this.http.get<Submission[]>(`${this.base}/api/submissions/assignment/${assignmentId}`);
  }
  download(id:number) { return this.http.get(`${this.base}/api/submissions/${id}/download`, { responseType: 'blob' }); }
}
